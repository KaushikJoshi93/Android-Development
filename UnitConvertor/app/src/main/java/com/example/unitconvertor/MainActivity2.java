package com.example.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    TextView textViewOfSecond;
    private Button play;
    private Button pause;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        textViewOfSecond = findViewById(R.id.textView3);
//        Intent intent = getIntent();
//        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
//        textViewOfSecond.setText(name);

//        Implementing Listview used with the ArrayAdapter
//        String []names_arr = {"karan","amit","rahul","abhinav","karan","amit","rahul","abhinav","karan","amit","rahul","abhinav","karan","amit","rahul","abhinav","karan","amit","rahul","abhinav"};
//        ListView listView = findViewById(R.id.listview);
//        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names_arr);
//        listView.setAdapter(ad);
//        Implementing Custom ArrayAdapter
//        KaranAdapter ad = new KaranAdapter(this , R.layout.my_karan_layout , names_arr);
//        listView.setAdapter(ad);

//        Listener on ArrayAdapter
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity2.this, "You Clicked on item: "+ i, Toast.LENGTH_SHORT).show();
//            }
//        });
        play = findViewById(R.id.play_btn);
        pause = findViewById(R.id.pause_btn);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(this , R.raw.music1);
        seekBar.setMax(mediaPlayer.getDuration());

        Handler updateHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            public void run() {
                // Get mediaplayer time and set the value
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                // This will trigger itself every one second.
                updateHandler.postDelayed(this, 1000);
            }
        };

        updateHandler.postDelayed(timerRunnable, 1000);


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
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation , R.anim.slide_down);
    }

    protected void  onStop(){
        super.onStop();
        mediaPlayer.release();
    }
//    Function for button to send the message
//    public void send_msg(View view){
//        EditText msg_box;
//        msg_box = findViewById(R.id.message);
//        String msg = msg_box.getText().toString();
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
//        sendIntent.setType("text/plain");
//        if (sendIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(sendIntent);
//        }
//
//    }
}