package com.example.fragmentmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    Button frag1_btn , frag2_btn;
//    BottomNavigationView bottomNavigationView;
//    TabLayout tab;
//    ViewPager viewPager;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDBhelper myDBhelper = new MyDBhelper(this);
//        myDBhelper.insert_db("Karan" , "21");
//        myDBhelper.insert_db("Rahul" , "34");
//        myDBhelper.insert_db("Amit" , "54");
//        myDBhelper.insert_db("Dhruv" , "21");

        ArrayList<StudentModel> arrStudent = myDBhelper.fetch_data();
        for (int i = 0; i < arrStudent.size(); i++) {
            Log.d("fetched_data" , "name: "+arrStudent.get(i).name +" Roll_no: "+arrStudent.get(i).roll_no);
        }

//        frag1_btn = findViewById(R.id.frag1_button);
//        frag2_btn = findViewById(R.id.frag2_button);

//        loadFrag(new AFragment() , 1);
//
//        frag1_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFrag(new AFragment() , 0);
//            }
//        });
//
//        frag2_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFrag(new BFragment() , 0);
//            }
//        });
//        tab = findViewById(R.id.mytablayout);
//        viewPager = findViewById(R.id.myviewpager);
//
//        MyViewPagerAdaptor adaptor = new MyViewPagerAdaptor(getSupportFragmentManager());
//        viewPager.setAdapter(adaptor);
//        tab.setupWithViewPager(viewPager);

//        bottomNavigationView = findViewById(R.id.bottomnavigation);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                if(id == R.id.home_item){
//                    ft.add(R.id.container , new AFragment());
//                    ft.commit();
//                }
//                else{
//                    ft.replace(R.id.container , new BFragment());
//                    ft.commit();
//                }
//
//                return true;
//            }
//        });
//        bottomNavigationView.setSelectedItemId(R.id.home_item);

    toolbar = findViewById(R.id.mytoolbar);
    navigationView = findViewById(R.id.navigation_drawer);
    drawerLayout = findViewById(R.id.mydrawerlayout);
    setSupportActionBar(toolbar);
//    to enable back button
//        if(getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        getSupportActionBar().setTitle("MyApp");
        toolbar.setSubtitle("This is a subtitle");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.OpenDrawer , R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        loadFrag(new AFragment() , 1);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home_item){
                    loadFrag(new AFragment() , 0);
                }
                else{
                    loadFrag(new BFragment() , 0);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });



        

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.nav_items , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home_item){
            Toast.makeText(this , "Home button" , Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Settings button", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

        public void loadFrag(Fragment fragment ,int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("Argument1","First Argument");
        bundle.putInt("Argument2",4);

        fragment.setArguments(bundle);
        if (flag == 1){
            ft.add(R.id.container , fragment);
//            fm.popBackStack("ROOT_FRAGMENT_TAG" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.popBackStack();
            ft.addToBackStack("ROOT_FRAGMENT_TAG");
        }
        else{
            ft.replace(R.id.container , fragment);
            ft.addToBackStack(null);
        }

        ft.commit();
    }
}