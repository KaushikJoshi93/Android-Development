package com.example.musicly;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MusicListAdaptor extends RecyclerView.Adapter<MusicListAdaptor.Viewholder> {
    private Context musicContext;
    private ArrayList<MusicListModel> musicArrList;
    private ArrayList<File> musicArr = new ArrayList<>();

    public MusicListAdaptor(Context musicContext, ArrayList<MusicListModel> musicArrList) {
        this.musicContext = musicContext;
        this.musicArrList = musicArrList;
    }

    @NonNull
    @Override
    public MusicListAdaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mylist = LayoutInflater.from(musicContext).inflate(R.layout.musiclistlayout , parent , false);
        RecyclerView.ViewHolder myviewholder = new Viewholder(mylist);
        return (Viewholder) myviewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdaptor.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        String name = musicArrList.get(position).getSongName().getName().substring(0 , musicArrList.get(position).getSongName().getName().lastIndexOf("."));
        holder.songname.setText(name);
        holder.img.setImageBitmap(musicArrList.get(position).getImg());
        for (int i=0 ; i<musicArrList.size() ; i++){
            musicArr.add(musicArrList.get(i).getSongName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(musicContext, PlayerActivity.class);
                myintent.putExtra("songName", musicArrList.get(position).getSongName());
                myintent.putExtra("musicArr" , musicArr);
                musicContext.startActivity(myintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicArrList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView songname;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.songImg);
            songname = itemView.findViewById(R.id.songName);

        }
    }


}
