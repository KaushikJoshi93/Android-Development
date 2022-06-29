package com.example.musicly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicly.services.CreateNotification;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {
    ViewPagerAdaptor viewPagerAdaptor;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ArrayList<String> arrlist = new ArrayList<>();
    ArrayList<File> Filelist = new ArrayList<>();
    ArrayList<File> musicList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.appToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        toolbar.showOverflowMenu();
        initViewPager();
        new TabLayoutMediator(tabLayout , viewPager ,this).attach();
       if(ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE} , 102);


       }
//       else{
           String[] proj = { MediaStore.Audio.Media._ID,
                   MediaStore.Audio.Media.ARTIST,
                   MediaStore.Audio.Media.TITLE,
                   MediaStore.Audio.Media.DATA,
                   MediaStore.Audio.Media.DISPLAY_NAME,
                   MediaStore.Audio.Media.DURATION };
           Cursor audioCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
           if(audioCursor != null){
               if(audioCursor.moveToFirst()){
                   do{
//                       int audioIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                       if(audioCursor.getString(4).endsWith(".mp3")){
                           Log.d("musicName" , String.valueOf(audioCursor.getString(4) + " "+audioCursor.getString(3) +" "+ audioCursor.getString(1)));

                           File fileName = new File(audioCursor.getString(3));
                           Log.d("filename" , String.valueOf(fileName));
                           if(fileName.exists()) {
                               musicList.add(fileName);
                           }
                       }
                   }while(audioCursor.moveToNext());
               }
           }
           audioCursor.close();
           Filelist.addAll(musicList);

//       }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            MainActivity.this.finishAffinity();
        }
    }




    private void initViewPager() {
        arrlist.add("Music");
        arrlist.add("Albums");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        MusicListFragment musicListFragment = MusicListFragment.newInstance(Filelist);
        viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager() , getLifecycle());
        viewPagerAdaptor.addFragment(MusicListFragment.newInstance(Filelist));
        viewPagerAdaptor.addFragment(AlbumFragment.newInstance(Filelist));
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(viewPagerAdaptor);


    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(arrlist.get(position));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.cancel(CreateNotification.NOTIFICATION_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search for music..");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent switch_intent = new Intent(MainActivity.this , SearchActivity.class);
                switch_intent.putExtra("SearchQuery" , searchView.getQuery().toString());
                switch_intent.putExtra("SongList" , Filelist);
                startActivity(switch_intent);
                searchView.setQuery("" , false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_more:
                Toast.makeText(this, "This is a more button....", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //    public boolean onPrepareOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

}