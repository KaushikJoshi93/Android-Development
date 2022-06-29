package com.example.musicly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    String SongQuery;
    ArrayList<File> arrayList;
    ArrayList<String> musics = new ArrayList<>();
    String name;
    ListView listView;
    ArrayAdapter adapter;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.appToolbar);
        textView = findViewById(R.id.noMatch);
        setSupportActionBar(toolbar);
        arrayList = (ArrayList<File>) getIntent().getSerializableExtra("SongList");
        SongQuery = getIntent().getStringExtra("SearchQuery");
        listView = findViewById(R.id.searchListView);
        filterMusic();
        if (musics.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else {
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, musics);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    File songName = null;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if(arrayList.get(i).getName().substring(0 , arrayList.get(i).getName().lastIndexOf(".")).equals(musics.get(position))){
                            songName = arrayList.get(i);
                        }
                    }
                    Intent searchIntent = new Intent(SearchActivity.this , PlayerActivity.class);
                    searchIntent.putExtra("songName", songName);
                    searchIntent.putExtra("musicArr" , arrayList);
                    startActivity(searchIntent);
                    finish();
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search for music..");
        searchView.setQuery(SongQuery , false);
        searchView.setFocusable(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SongQuery = query;
                musics.removeAll(musics);
                filterMusic();
                if(musics.isEmpty()){
                    textView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                else {
                    adapter.notifyDataSetChanged();
                    textView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void filterMusic(){
        for (int i = 0; i < arrayList.size(); i++) {
            name = arrayList.get(i).getName().substring(0 , arrayList.get(i).getName().lastIndexOf("."));
            if(name.toLowerCase().contains(SongQuery.toLowerCase())){
                musics.add(name);
            }
        }
    }
}