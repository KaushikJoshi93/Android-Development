package com.example.musicly;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MusicListModel {
    private Bitmap img;
    private File songName;

    public MusicListModel(File songName , Bitmap img) {
        this.songName = songName;
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public File getSongName() {
        return songName;
    }
}
