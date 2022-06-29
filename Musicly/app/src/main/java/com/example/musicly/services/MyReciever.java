package com.example.musicly.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getAction();
        Log.d("receiver" , "i am in receiver class "+value);
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname" , value));
    }
}
