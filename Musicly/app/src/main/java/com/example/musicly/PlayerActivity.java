package com.example.musicly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicly.services.CreateNotification;

import java.io.File;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;


public class PlayerActivity extends AppCompatActivity {
    Handler updateHandler;
    Runnable timerRunnable;
   AppCompatImageButton play_btn , previous_btn , next_btn ;
   Bitmap mybitmap;
    File songName;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    ImageView imageView , back_btn , more_btn;
    TextView title , left_duration , right_duration;
    ArrayList<File> musicArrList = new ArrayList<>();
    File song;
    int buttonBackground = 1 , songIndex , val=0;
    BroadcastReceiver broadcastReceiver;
    ScrollView rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        rootView = findViewById(R.id.rootView);
        imageView = findViewById(R.id.playerImage);
        left_duration = findViewById(R.id.left_duration);
        right_duration = findViewById(R.id.right_duration);
        back_btn = findViewById(R.id.player_back);
        more_btn = findViewById(R.id.player_more);
        // Make a broadcast receiver for the notification
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getExtras().getString("actionname");
                switch (action){
                    case CreateNotification.ACTION_PLAY:
                        playSong();
                        break;
                    case CreateNotification.ACTION_NEXT:
                        nextSong();
                        break;
                    case CreateNotification.ACTION_PREVIOUS:
                        previousSong();
                        break;
                    default:
                        Log.d("tag" , "this is a default thing...."+action);
                }
            }
        };

        registerReceiver(broadcastReceiver , new IntentFilter("TRACKS_TRACKS"));

        title = findViewById(R.id.musicTitle);
        title.setSelected(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            songName = (File) extras.getSerializable("songName");
            musicArrList = (ArrayList<File>) extras.getSerializable("musicArr");
        }
        title.setText(songName.getName().substring(0 , songName.getName().lastIndexOf(".")));
        play_btn = (AppCompatImageButton) findViewById(R.id.playsong);
        previous_btn = findViewById(R.id.previousSong);
        next_btn = findViewById(R.id.nextSong);
        initMediaPlayer(songName);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());

         updateHandler = new Handler();
        timerRunnable = new Runnable() {

            public void run() {
                // Get mediaplayer time and set the value
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                left_duration.setText(mediaPlayer.getCurrentPosition()/1000/60 + ":"+String.format("%02d",mediaPlayer.getCurrentPosition()/1000%60));
                // This will trigger itself every one second.
                updateHandler.postDelayed(this, 1000);
            }
        };

        updateHandler.postDelayed(timerRunnable, 1000);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHandler.removeCallbacks(timerRunnable);
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        putCompletionListener();

        mybitmap = createAlbumArt(song.toString());
        if(mybitmap != null) {
            Blurry.with(this).radius(25).sampling(4).from(mybitmap).into(imageView);
            rootView.setBackground(imageView.getDrawable());
            imageView.setImageBitmap(mybitmap);
        }
        else{
            Blurry.with(this).radius(25).sampling(4).from(BitmapFactory.decodeResource(getResources() , R.drawable.img)).into(imageView);
            rootView.setBackground(imageView.getDrawable());
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.img));
        }

//        Bitmap bitmap = BlurImage.with(getApplicationContext()).load(imageView.getDrawable()).intensity(20).Async(true).getImageBlur();

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(PlayerActivity.this , R.anim.rotate_animation);
                mybitmap = (mybitmap ==null)? BitmapFactory.decodeResource(getResources() , R.drawable.img):mybitmap;
                try{
                    if(buttonBackground == 1) {
                        play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                        play_btn.startAnimation(animation);
                        mediaPlayer.start();
                        CreateNotification.createNotification(PlayerActivity.this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_pause_24);
                        buttonBackground = 2;
                    }
                    else{
                        play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                        play_btn.startAnimation(animation);
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            CreateNotification.createNotification(PlayerActivity.this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_play_arrow_24);
                        }
                        buttonBackground = 1;
                    }
                }
                catch (Exception e){

                }

            }
        });


        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMusic("previous");
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMusic("next");
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        val = 1;
        updateHandler.removeCallbacks(timerRunnable);
        mediaPlayer.release();
    }

    //    function to change music
    public void changeMusic(String flag){
        songIndex = musicArrList.indexOf(songName);
        if(flag.toLowerCase() == "previous"){
            songIndex -= 1;
            if(songIndex < 0){
                songIndex = musicArrList.size() -1;
            }
        }
        else {
            songIndex += 1;
            if(songIndex == musicArrList.size()){
                songIndex = 0;
            }
        }
        songName = musicArrList.get(songIndex);
        mediaPlayer.release();
        initMediaPlayer(songName);

        mybitmap = createAlbumArt(song.toString());
        Animation animation = AnimationUtils.loadAnimation(PlayerActivity.this , R.anim.slide_animation_left_to_right);
        imageView.startAnimation(animation);
        if(mybitmap != null) {
            Blurry.with(this).radius(25).sampling(4).from(mybitmap).into(imageView);
            rootView.setBackground(imageView.getDrawable());
            imageView.setImageBitmap(mybitmap);
        }
        else{
            Blurry.with(this).radius(25).sampling(4).from(BitmapFactory.decodeResource(getResources() , R.drawable.img)).into(imageView);
            rootView.setBackground(imageView.getDrawable());
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.img));
        }
        title.setText(songName.getName().substring(0 , songName.getName().lastIndexOf(".")));
        if(flag != "next" && flag != "previous"){
            mediaPlayer.start();
            if(buttonBackground == 1) {
                play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                buttonBackground = 2;
            }
            seekBar.setMax(mediaPlayer.getDuration());

        }
        else{
            if(buttonBackground == 2 ){
                play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                buttonBackground = 1;
            }
        }
        putCompletionListener();
        updateHandler.postDelayed(timerRunnable , 1000);
        mybitmap = (mybitmap ==null)? BitmapFactory.decodeResource(getResources() , R.drawable.img):mybitmap;
        if(mediaPlayer.isPlaying()) {
                CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList, R.drawable.ic_baseline_pause_24);
        }
        else{
                CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_play_arrow_24);

        }
    }

//    function to put completion listener
    public void putCompletionListener(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                updateHandler.removeCallbacks(timerRunnable);
                changeMusic("nextsong");

            }
        });
    }

    //    function to initialize mediaplayer
    public void initMediaPlayer(File songName){
        song = songName;
        if(song.exists()){
            mediaPlayer = MediaPlayer.create(this , Uri.parse(song.toString()));
            right_duration.setText(mediaPlayer.getDuration()/1000/60 + ":"+String.format("%02d",mediaPlayer.getDuration()/1000%60));
        }
    }

    public static Bitmap createAlbumArt(final String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            byte[] embedPic = retriever.getEmbeddedPicture();
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(val != 1) {
            mybitmap = (mybitmap == null) ? BitmapFactory.decodeResource(getResources(), R.drawable.img) : mybitmap;
            if (mediaPlayer.isPlaying()) {
                CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList, R.drawable.ic_baseline_pause_24);
            } else {
                CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList, R.drawable.ic_baseline_play_arrow_24);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
        mediaPlayer = null;
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.cancel(CreateNotification.NOTIFICATION_ID);
    }

    private void playSong() {
        Log.d("tag" ,"in play song");
        mybitmap = (mybitmap ==null)? BitmapFactory.decodeResource(getResources() , R.drawable.img):mybitmap;
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_play_arrow_24);
            if(buttonBackground ==2) {
                play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                buttonBackground = 1;
            }

        }
        else{
            mediaPlayer.start();
            CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_pause_24);
            if(buttonBackground ==1) {
                play_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                buttonBackground = 2;
            }
        }
    }

    private void nextSong(){
        Log.d("tag" ,"in next song");
        changeMusic("next");
        seekBar.setMax(mediaPlayer.getDuration());
        if(mybitmap != null) {
            CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_play_arrow_24);
        }
        else{
            CreateNotification.createNotification(this, songName.getName(), BitmapFactory.decodeResource(getResources() , R.drawable.img), musicArrList , R.drawable.ic_baseline_play_arrow_24);
        }
    }

    private void previousSong(){
        Log.d("tag" ,"in previous song");
        changeMusic("previous");
        seekBar.setMax(mediaPlayer.getDuration());
        if(mybitmap != null) {
            CreateNotification.createNotification(this, songName.getName(), mybitmap, musicArrList , R.drawable.ic_baseline_play_arrow_24);
        }
        else{
            CreateNotification.createNotification(this, songName.getName(), BitmapFactory.decodeResource(getResources() , R.drawable.img), musicArrList , R.drawable.ic_baseline_play_arrow_24);
        }
    }
}



