package com.catharina.mynoticeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.catharina.mynoticeapp.com.github.kevinsawicki.http.HttpRequest;
import com.catharina.mynoticeapp.modelo.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catharina on 18/09/16.
 */
public class NoticeService extends Service {

    List<Notice> noticeList = new ArrayList<>();
    public List<Worker> threads = new ArrayList<Worker>();


    /*Metodo criado somente quando ser criar um serviço de vinculação*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getBaseContext(), "Serviço iniciado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(threads.size() == 0) {
            Worker w = new Worker(startId);
            w.start();
            threads.add(w);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public class Worker extends Thread{

        public int startId = 0;
        public boolean ativo = true;

        public Worker (int startId){
            this.startId = startId;
        }

        @Override
        public void run() {
            while (ativo){
                try {
                    Thread.sleep(30000);
                    new NoticeTaskNoticiation().execute();
                    Log.i("Catharina", "Serviço "+startId+" iniciado.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            stopSelf(startId);
            Log.i("Catharina", "Serviço "+startId+" finalizado");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < threads.size(); i++){
            threads.get(i).ativo = false;
        }

        Toast.makeText(getBaseContext(), "Serviço finalizado", Toast.LENGTH_SHORT).show();
    }


    private class NoticeTaskNoticiation extends AsyncTask<String, Void, String[]>{

        @Override
        protected void onPostExecute(String[] result) {

            boolean houveAdicao = false;
            boolean primeiraVez = true;

            if(noticeList.size() != 0){
                primeiraVez = false;
            }

            for(int i = 0; i < result.length; i++){
                String aux = result[i];
                String[] split = aux.split(";");
                Notice n = new Notice(Long.parseLong(split[0]),split[1],split[2],split[3]);
                if(!noticeList.contains(n)){
                    houveAdicao = true;
                    noticeList.add(n);
                }
            }


           if(!primeiraVez && houveAdicao){

               NotificationCompat.Builder mBuilder =
                       new NotificationCompat.Builder(getBaseContext())
                               .setSmallIcon(R.drawable.ic_launcher)
                               .setContentTitle("Notice App")
                               .setContentText("Nova Notícia Adicionada");

               Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
               PendingIntent resultPendingIntent =
                       PendingIntent.getActivity( getBaseContext(), 0, resultIntent,
                               PendingIntent.FLAG_UPDATE_CURRENT
                       );
               mBuilder.setContentIntent(resultPendingIntent);

               Notification notification = mBuilder.build();

               notification.flags = Notification.FLAG_AUTO_CANCEL;
               notification.defaults |= Notification.DEFAULT_VIBRATE;
               notification.defaults |= Notification.DEFAULT_LIGHTS;
               notification.defaults |= Notification.DEFAULT_SOUND;

               int notificationId = 000001;
               NotificationManager mNotifyMgr =
                       (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
               mNotifyMgr.notify(notificationId, notification);
           }
            else{
               Toast.makeText(getBaseContext(), "Nenhuma notícia nova", Toast.LENGTH_SHORT).show();
           }
        }

        @Override
        protected String[] doInBackground(String... params) {

            String[] notices = null;

            Log.i("Catharina", "iniciou uma requisição");
            try {
                String path = "https://mynotice.herokuapp.com/notices.json";

                String conteudo = HttpRequest.get(path).body();
                JSONArray resultados = new JSONArray(conteudo);
                notices = new String[resultados.length()];

                for (int i = 0; i < resultados.length(); i++) {
                    JSONObject notice = resultados.getJSONObject(i);
                    String titulo = notice.getString("title");
                    Long id = notice.getLong("id");
                    String corpo = notice.getString("body");
                    String dataCriacao = notice.getString("criation");

                    notices[i] = id + ";" + titulo + ";" + corpo + ";" + dataCriacao;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return notices;
        }

    }
}
