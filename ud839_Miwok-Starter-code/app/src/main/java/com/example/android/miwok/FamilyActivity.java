package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_family);
//        Create and setup the AudioFocus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //        Words array
        ArrayList<Word> strArr2 = new ArrayList<Word>();
        strArr2.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        strArr2.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        strArr2.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        strArr2.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        strArr2.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        strArr2.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        strArr2.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        strArr2.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        strArr2.add(new Word("grandmother ", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        strArr2.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

//        Make and set the adapter
        ListView listView2 = findViewById(R.id.family_list);

//        Add the custom adaptor
        MeraAdaptor adaptor2 = new MeraAdaptor(this, strArr2, R.color.category_family);

//        Set the adaptor to listview
        listView2.setAdapter(adaptor2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = strArr2.get(position);

//                Request the audio focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                    We have the audio focus now

                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
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

//        Release the audio Focus
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();

    }
}