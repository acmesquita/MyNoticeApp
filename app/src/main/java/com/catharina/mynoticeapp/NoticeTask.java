package com.catharina.mynoticeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.catharina.mynoticeapp.adapter.NoticeAdapter;
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
public class NoticeTask extends AsyncTask<String, Void, String[]> {

    ProgressDialog dialog;
    RecyclerView recyclerView;
    NoticeAdapter adapter;
    Context context;

    public NoticeTask(RecyclerView recyclerView, NoticeAdapter adapter, Context context) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //exibir um Process Dialog
        dialog = new ProgressDialog(context);
        dialog.setTitle("Buscando noticias");
        dialog.setMessage("Buscando novas notícias.");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String[] result) {
        //TODO adicionar ao layout as informações
        if(result != null) {

            List<Notice> notices = new ArrayList<>();

            for(int i = 0; i < result.length; i++){
                String aux = result[i];
                String[] split = aux.split(";");
                Notice n = new Notice(Long.parseLong(split[0]),split[1],split[2],split[3]);
                notices.add(n);
            }
            if(adapter == null) {
                adapter = new NoticeAdapter(context, notices);
            }
            else{
                adapter.getNotices().addAll(notices);
            }

            recyclerView.setAdapter(adapter);
        }
        dialog.dismiss();
    }




    @Override
    protected String[] doInBackground(String... params) {

        String[] notices = null;
        List<Notice> ns = new ArrayList<>();

        try {
            String path = "https://mynotice.herokuapp.com/notices.json";

            String conteudo = HttpRequest.get(path).body();
            JSONArray resultados = new JSONArray(conteudo);

            notices = new String[resultados.length()];
            for (int i = 0; i < resultados.length(); i++) {
                JSONObject notice = resultados.getJSONObject(i);
                Long id = notice.getLong("id");
                String titulo = notice.getString("title");
                String corpo = notice.getString("body");
                String dataCriacao = notice.getString("criation");

                notices[i] = id+";"+titulo+ ";"+ corpo +";"+dataCriacao;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notices;
    }
}