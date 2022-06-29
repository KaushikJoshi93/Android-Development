package com.example.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView2;
    private EditText inputText;
    double pound;
    public static final String EXTRA_NAME = "com.example.unitconvertor.extra.NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        inputText = findViewById(R.id.editTextNumber);
        textView2 = findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "This listener is working...", Toast.LENGTH_SHORT).show();
                textView2.setText("");
//                Log.d();
                String s = inputText.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(MainActivity.this, "The value cannot be empty...",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int kg = Integer.parseInt(s);
                         pound = 2.205 * kg;
                        textView2.setText("Corresponding value in pound is: " + pound);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Invalid value..",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public  void openActivity(View view){
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra(EXTRA_NAME , "This is a string of next window");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up , R.anim.no_animation);
    }
    public void openVideoPlayer(View view){
        Intent intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
    }


    //        Alternative way to do onclick --> we can set onClick attribute to below function in xml
//    public void calculate(View view){
//        textView2.setText("");
//        String s = inputText.getText().toString();
//        if (s.equals("")) {
//            Toast.makeText(MainActivity.this, "The value cannot be empty...", Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//                int kg = Integer.parseInt(s);
//                double pound = 2.205 * kg;
//                textView2.setText("Corresponding value in pound is: " + pound);
//            } catch (Exception e) {
//                Toast.makeText(MainActivity.this, "Invalid value..", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
}