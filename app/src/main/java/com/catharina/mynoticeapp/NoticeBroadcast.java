package com.catharina.mynoticeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by catharina on 18/09/16.
 */
public class NoticeBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Catharina", "Iniciando por Broadcast");
        intent = new Intent(context, NoticeService.class);
        context.startService(intent);
    }
}
