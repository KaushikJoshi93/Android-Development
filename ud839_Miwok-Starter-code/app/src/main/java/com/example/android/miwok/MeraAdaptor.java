package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MeraAdaptor extends ArrayAdapter<Word> {

//    make the variable for storing color resource id
    private int mColorsResourceId;



    public MeraAdaptor(Activity context , ArrayList<Word> myArr , int colorResourceId) {
        super(context,0, myArr);
        this.mColorsResourceId = colorResourceId;
    }

//    Override the getView method of ArrayAdaptor class

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.items , parent , false);
        }
//        Get the object located at the given position
        Word currentWord = getItem(position);

//        Find all the textViews to insert the data
        TextView textView1 = listItemView.findViewById(R.id.textView1);
        TextView textView2 = listItemView.findViewById(R.id.textView2);

//        Set the theme color for the list items
        View txtContainer = listItemView.findViewById(R.id.text_container);
//        Find the color id that the resource id map to
        int color = ContextCompat.getColor(getContext() , this.mColorsResourceId);
//        set the color to text container
        txtContainer.setBackgroundColor(color);

//        Now set the text to all the textviews
        textView1.setText(currentWord.getmMiwokTranslation());
        textView2.setText(currentWord.getmEnglishTranslation());

//        Now set the image in our imageview
        ImageView myimage = listItemView.findViewById(R.id.myimage);
        if(currentWord.hasImage()) {
//            set the resourceid of the imageview
            myimage.setImageResource(currentWord.getmImageResourceId());
//            Make sure the view is visible
            myimage.setVisibility(View.VISIBLE);
        }
        else{
//            Hide the image by passing View.GONE which hides the image but don't take the space of the image
            myimage.setVisibility(View.GONE);
        }


//        Now return the prepared view
        return  listItemView;
    }
}
