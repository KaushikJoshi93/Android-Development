package com.example.practiceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyReciever extends BroadcastReceiver {
    Context mycontext;

    public MyReciever(Context context) {
        this.mycontext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = getConnectivityStatusString(mycontext);
        Toast.makeText(context, ""+status, Toast.LENGTH_SHORT).show();
    }


    public static String getConnectivityStatusString(@NonNull Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }
}
