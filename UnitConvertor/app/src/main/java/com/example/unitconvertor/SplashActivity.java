package com.example.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Thread th = new Thread(){
            public void  run(){
                try {
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.d("Error", "Error: "+e.toString());
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        th.start();
    }
}