package com.example.fragmentmaker;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class BFragment extends Fragment {
    public ImageView imageView;
    public BFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_b, container, false);
        EditText editText = myview.findViewById(R.id.edittextB);
        TextView textView = myview.findViewById(R.id.frag2_textview);
        Button button = myview.findViewById(R.id.btnB);
        SharedPreferences preferences = getActivity().getSharedPreferences("NameOfStudent" , Context.MODE_PRIVATE);
        String name = preferences.getString("name"  , "karan");
        textView.setText(name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
                SharedPreferences preferences1 = getActivity().getSharedPreferences("NameOfStudent" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("name" , editText.getText().toString());
                editor.commit();
            }
        });

        imageView = myview.findViewById(R.id.imageview);
        Button openCamera = myview.findViewById(R.id.openCamera);
        Button openGallery = myview.findViewById(R.id.openGallery);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(icamera , 100);
            }
        });

        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery , 101);
            }
        });

        return myview;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 100){
//                For camera
                Bitmap img = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(img);
            }
           else if(requestCode == 101){
//                For Gallery
                imageView.setImageURI(data.getData());
            }
        }
    }
}