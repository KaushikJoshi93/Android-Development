package com.example.fragmentmaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPagerAdaptor extends FragmentPagerAdapter {

    public MyViewPagerAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new AFragment();
        }
        else{
            return new BFragment();
        }
    }

    @Override
    public int getCount() {
        return 2; //number of tabs
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "FragmentA";
        }
        else{
            return "FragmentB";
        }
    }
}
