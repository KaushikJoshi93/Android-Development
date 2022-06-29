package com.example.animationmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String nwname , nwmobile_no ;
    ArrayList<StudentModel> myarrlist;
    StudentModelAdaptor studentModelAdaptor;
    RecyclerView recyclerView;
    public static final String CHANNEL_ID = "my channel";
    public static final int NOTIFICATION_ID = 100;
    public static final int REQUEST_CODE = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Oncreate method is called", Toast.LENGTH_SHORT).show();
//        TextView textView = findViewById(R.id.textView);
//        Button blink_btn = findViewById(R.id.Blink);
//        Button fade_btn = findViewById(R.id.fade);
//        Button rotate_btn = findViewById(R.id.rotate);
//        Button scale_btn = findViewById(R.id.scale);
//        blink_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.blink_animation);
//                textView.startAnimation(animation);
//            }
//        });
//        fade_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.fade_animation);
//                textView.startAnimation(animation);
//            }
//        });
//        rotate_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.rotate_animation);
//                textView.startAnimation(animation);
//            }
//        });
//        scale_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.scale_animation);
//                textView.startAnimation(animation);
//            }
//        });
//        Spinner spinner = findViewById(R.id.spinner);
//        ArrayList<String> place_name = new ArrayList<>();
//        place_name.add("Haldwani");
//        place_name.add("Rudrapur");
//        place_name.add("Kichha");
//        place_name.add("Shantipuri");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_spinner_dropdown_item , place_name);
//        spinner.setAdapter(adapter);

//        Setting up the recyclerview
        FloatingActionButton add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update_dialog);
                dialog.setCancelable(false);
                TextView name = dialog.findViewById(R.id.name);
                TextView mobile_no = dialog.findViewById(R.id.mobile_no);
                Button add_btn = dialog.findViewById(R.id.add_btn);
                ImageView close_btn = dialog.findViewById(R.id.close_btn);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!name.getText().toString().equals("") && !mobile_no.getText().toString().equals("")){
                            nwname = name.getText().toString();
                            nwmobile_no = mobile_no.getText().toString();
                            myarrlist.add(new StudentModel(nwname , nwmobile_no , R.drawable.internet));
                            studentModelAdaptor.notifyItemInserted(myarrlist.size() - 1);
                            recyclerView.scrollToPosition(myarrlist.size() - 1);
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Name or mobile number cannot be empty", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

//                convert drwable to bitmap
                Drawable drawable = ResourcesCompat.getDrawable(getResources() , R.drawable.internet , null);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap largeicon = bitmapDrawable.getBitmap();

//                Notification example
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification;
                Intent inotify = new Intent(getApplicationContext() , MainActivity.class);
                inotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pt = PendingIntent.getActivities(MainActivity.this , REQUEST_CODE , new Intent[]{inotify}, PendingIntent.FLAG_UPDATE_CURRENT);

//                Bigpicture style
                Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                        .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources() , R.drawable.calculator , null))).getBitmap())
                        .bigLargeIcon(largeicon)
                        .setBigContentTitle("Hello this is a title of a new notification")
                        .setSummaryText("Yes this is the test description of the notification generated by kaushik joshi");
//                Notification.InboxStyle

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification = new NotificationCompat.Builder(MainActivity.this , CHANNEL_ID)
                            .setLargeIcon(largeicon)
                            .setSmallIcon(R.drawable.ic_baseline_delete_24)
                            .setContentTitle("This is a title of the notification")
                            .setContentText("Hello this is a notification generated by me as a app developer")
                            .setSubText("Hello  notification kaushik joshi")
                            .setContentIntent(pt)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources() , R.drawable.calculator , null))).getBitmap())
                                    .bigLargeIcon(largeicon)
                                    .setBigContentTitle("Hello this is a title of a new notification")
                                    .setSummaryText("Yes this is the test description of the notification generated by kaushik joshi"))
                            .setAutoCancel(true)
                            .build();
                    notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID , "New channel" , NotificationManager.IMPORTANCE_HIGH));
                }
                else{
                    notification = new Notification.Builder(MainActivity.this)
                            .setLargeIcon(largeicon)
                            .setSmallIcon(R.drawable.ic_baseline_delete_24)
                            .setContentTitle("This is a title of the notification")
                            .setContentText("Hello this is a notification generated by me as a app developer")
                            .setContentIntent(pt)
                            .setAutoCancel(true)
                            .setStyle(bigPictureStyle)
                            .setSubText("Hello  notification kaushik joshi")
                            .build();
                }
                notificationManager.notify(NOTIFICATION_ID , notification);

            }
        });
//toast.setview()
//        toast.setgravity();

        myarrlist = new ArrayList<>();
        myarrlist.add(new StudentModel("Amit" , "9219486893" , R.drawable.hero));
        myarrlist.add(new StudentModel("Rahul" , "8824763219" , R.drawable.internet));
        myarrlist.add(new StudentModel("Jignesh" , "6543217899" , R.drawable.calculator));
        myarrlist.add(new StudentModel("Karan" , "9342796431" , R.drawable.suitcase));
        myarrlist.add(new StudentModel("Amit" , "9219486893" , R.drawable.hero));
        myarrlist.add(new StudentModel("Rahul" , "8824763219" , R.drawable.internet));
        myarrlist.add(new StudentModel("Jignesh" , "6543217899" , R.drawable.calculator));
        myarrlist.add(new StudentModel("Karan" , "9342796431" , R.drawable.suitcase));
        myarrlist.add(new StudentModel("Amit" , "9219486893" , R.drawable.hero));
        myarrlist.add(new StudentModel("Rahul" , "8824763219" , R.drawable.internet));
        myarrlist.add(new StudentModel("Jignesh" , "6543217899" , R.drawable.calculator));
        myarrlist.add(new StudentModel("Karan" , "9342796431" , R.drawable.suitcase));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentModelAdaptor = new StudentModelAdaptor(this , myarrlist);
        recyclerView.setAdapter(studentModelAdaptor);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sm != null){
            Sensor accelorosensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(accelorosensor != null){
                sm.registerListener((new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//                            Toast.makeText(MainActivity.this, "Value of x: "+sensorEvent.values[0] + " value of y: "+sensorEvent.values[1]  + " value of z: "+sensorEvent.values[2], Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }

                }), accelorosensor , SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else{
            Toast.makeText(this, "Sensor is not available", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop method is called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy method is called", Toast.LENGTH_SHORT).show();
    }
}