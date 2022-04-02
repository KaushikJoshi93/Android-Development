package com.example.unitconvertor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity3 extends AppCompatActivity {
    private Button video_play;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        surfaceView = findViewById(R.id.surfaceView);
        video_play = findViewById(R.id.video_play);
        seekBar2 = findViewById(R.id.seekBar2);
        mediaPlayer = MediaPlayer.create(this  ,  R.raw.myvideo);
        seekBar2.setMax(mediaPlayer.getDuration());

        Handler updateHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            public void run() {
                // Get mediaplayer time and set the value
                seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                // This will trigger itself every one second.
                updateHandler.postDelayed(this, 1000);
            }
        };

        updateHandler.postDelayed(timerRunnable, 1000);


        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        surfaceView.setKeepScreenOn(true);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
        video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                     mediaPlayer.pause();
                     video_play.setText("Play");
                }
                else {
                    mediaPlayer.start();
                    video_play.setText("Pause");
                }

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                video_play.setText("Play");
            }
        });
    }
}