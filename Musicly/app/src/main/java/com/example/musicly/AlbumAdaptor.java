package com.example.musicly;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class AlbumAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<MusicListModel> albumList;
    private ArrayList<File> musicArr = new ArrayList<>();

    public AlbumAdaptor(Context mycontext , ArrayList<MusicListModel> albumsList) {
        this.context = mycontext;
        this.albumList = albumsList;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int i) {
        return albumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myview = view;
        if(myview == null){
            myview = LayoutInflater.from(context).inflate( R.layout.albumlayout, viewGroup , false);
        }
        ImageView imageView = myview.findViewById(R.id.albumImage);
        if(albumList.get(i).getImg() == null){
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources() , R.drawable.img));
        }
        else {
            imageView.setImageBitmap(albumList.get(i).getImg());
        }
        TextView textView = myview.findViewById(R.id.albumSongName);
        String name = albumList.get(i).getSongName().getName().substring(0 , albumList.get(i).getSongName().getName().lastIndexOf("."));
        textView.setText(name);
        for (int j=0 ; j<albumList.size() ; j++){
            musicArr.add(albumList.get(j).getSongName());
        }
        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(context, PlayerActivity.class);
                myintent.putExtra("songName", albumList.get(i).getSongName());
                myintent.putExtra("musicArr" , musicArr);
                context.startActivity(myintent);
            }
        });
        return myview;
    }
}
