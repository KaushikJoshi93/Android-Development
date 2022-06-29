package com.example.musicly.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicly.MainActivity;
import com.example.musicly.R;

import java.io.File;
import java.util.ArrayList;

public  class CreateNotification {
    public final static int NOTIFICATION_ID = 1221;
      static String SONG_CHANNEL_ID = "100";
      static int REQUEST_CODE = 1;
      public final static String PLAYER_KEY = "musicly.playerKey";
      public final static String ACTION_PLAY = "playSong";
      public final static String ACTION_PREVIOUS = "preSong";
      public final static String ACTION_NEXT = "nextSong";
//    Method to create notification in android
    public static void createNotification(Context context , String song , Bitmap img , ArrayList<File> songList , int playbtn){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
//        Intent for previous button
            Intent previous_intent = new Intent(context, MyReciever.class);
            previous_intent.setAction(ACTION_PREVIOUS);
            PendingIntent actionPrePendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, previous_intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        intent for play button
            Intent play_intent = new Intent(context, MyReciever.class);
            play_intent.setAction( ACTION_PLAY);
            PendingIntent actionPlayPendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, play_intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        intent for next button
            Intent next_intent = new Intent(context, MyReciever.class);
            next_intent.setAction( ACTION_NEXT);
            PendingIntent actionNextPendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, next_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SONG_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle(song)
                    .setContentText("This is a new message for you")
                    .setLargeIcon(img)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0 , 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .addAction(R.drawable.ic_baseline_skip_previous_24, "Pre", actionPrePendingIntent)
                    .addAction(playbtn, "Play", actionPlayPendingIntent)
                    .addAction(R.drawable.ic_baseline_skip_next_24, "Next", actionNextPendingIntent)
                    .setOngoing(true);
//        Create a notification channel for android oreo and higher android devices
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(SONG_CHANNEL_ID, "Song channel", NotificationManager.IMPORTANCE_HIGH);
                NotificationManager manager = context.getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
