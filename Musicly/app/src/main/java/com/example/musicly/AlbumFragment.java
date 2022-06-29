package com.example.musicly;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<File> albumList = new ArrayList<>() ;
    private  ArrayList<MusicListModel> albums = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlbumFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(ArrayList<File> fileList) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2 , fileList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albumList = (ArrayList<File>) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_album, container, false);
        GridView albumGrid = myview.findViewById(R.id.albumGrid);
        if (albumList.size() == 0) {
            TextView nomusic = myview.findViewById(R.id.albumText);
            albumGrid.setVisibility(View.GONE);
            nomusic.setVisibility(View.VISIBLE);
        }
        else {
        Log.d("albums" , String.valueOf(albumList));

            File song;
            Bitmap songBitmap;
            for (int i = 0; i< albumList.size() ; i++) {
                Log.d("inside" , "i am inside loop...");
                song = albumList.get(i);
                songBitmap = createAlbumArt(song.toString());
                if(songBitmap == null){
                    albums.add(new MusicListModel(song , BitmapFactory.decodeResource(getResources() , R.drawable.img)));
                }
                else {
                    albums.add(new MusicListModel(song, songBitmap));
                }
            }
            AlbumAdaptor albumadaptor = new AlbumAdaptor(getActivity() , albums);
            albumGrid.setAdapter(albumadaptor);
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