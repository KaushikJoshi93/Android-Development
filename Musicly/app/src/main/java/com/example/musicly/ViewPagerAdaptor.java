package com.example.musicly;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdaptor extends FragmentStateAdapter{
    ArrayList<Fragment> arrayList = new ArrayList<>();

    public ViewPagerAdaptor(FragmentManager fragmentManager , Lifecycle lifecycle){
        super(fragmentManager , lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arrayList.get(position);
    }

   public void addFragment(Fragment fragment){
        arrayList.add(fragment);
   }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
