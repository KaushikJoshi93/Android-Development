package com.example.fragmentmaker;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


public class AFragment extends Fragment {

    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_a, container, false);
//        TextView mytextview = myview.findViewById(R.id.frag1_textview);
//        mytextview.setText("Hello Karan this is a fragment one...");
//        if(getArguments() != null){
//            String name = getArguments().getString("Argument1");
////            mytextview.setText(name);
//        }
        WebView webView = myview.findViewById(R.id.mywebview1);
        ProgressBar progressBar = myview.findViewById(R.id.myprogressbar1);
        webView.loadUrl("https://www.google.com");
        webView.setWebViewClient( new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        return myview;
    }


}