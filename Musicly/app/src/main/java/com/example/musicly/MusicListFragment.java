package com.example.musicly;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import com.example.musicly.services.CreateNotification;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param1";
    private ArrayList<File> MusicList ;
    private  ArrayList<MusicListModel> music = new ArrayList<>();
    BroadcastReceiver broadcastReceiver;

    public MusicListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MusicListFragment newInstance(ArrayList<File> fileList) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM , fileList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MusicList = (ArrayList<File>) getArguments().getSerializable(ARG_PARAM);
            Log.d("musiclist" , ""+MusicList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_music_list, container, false);
        RecyclerView mylist = myview.findViewById(R.id.mylist);
        mylist.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (MusicList.isEmpty()) {
            TextView nomusic = myview.findViewById(R.id.nosong);
            mylist.setVisibility(View.GONE);
            nomusic.setVisibility(View.VISIBLE);
        } else {

            File song;
            Bitmap songBitmap;
            for (int i=0 ; i<MusicList.size() ; i++) {
               song = MusicList.get(i);
                songBitmap = createAlbumArt(song.toString());
                if(songBitmap == null){
                    music.add(new MusicListModel(song , BitmapFactory.decodeResource(getResources() , R.drawable.img)));
                }
                else {
                    music.add(new MusicListModel(song, songBitmap));
                }
            }
            MusicListAdaptor adaptor = new MusicListAdaptor(getActivity() , music);
            mylist.setAdapter(adaptor);


        }
        return myview;
    }



    public static Bitmap createAlbumArt(final String filePath) {
            Bitmap bitmap = null;
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(filePath);
                try {
                    byte[] embedPic = retriever.getEmbeddedPicture();
                bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length);
                }
                catch(Exception e){
                    return null;
                }
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
}