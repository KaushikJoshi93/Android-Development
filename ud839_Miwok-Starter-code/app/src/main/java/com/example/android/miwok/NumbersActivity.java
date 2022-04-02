package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//        Words array
        ArrayList<Word> strArr = new ArrayList<Word>();
        strArr.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        strArr.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        strArr.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        strArr.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        strArr.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        strArr.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        strArr.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        strArr.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        strArr.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        strArr.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

//        Make and set the adapter
        ListView listView = findViewById(R.id.listView);

//        Add the custom adaptor
        MeraAdaptor adaptor = new MeraAdaptor(this,strArr , R.color.category_numbers);

//        Set the adaptor to listview
        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = strArr.get(position);

//                Request the audio focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
//                    We have the audio focus now

                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this , word.getmAudioResourceId());
                    mMediaPlayer.start();

//                Add the event to listen of completing the audio
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });

                }



            }
        });

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

//            abandon the Audio Focus
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
}