package com.example.unitconvertor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class KaranAdapter extends ArrayAdapter<String> {
    private String[] arr;

    public KaranAdapter(@NonNull Context context, int resource, @NonNull String[] arr) {
        super(context, resource, arr);
        this.arr = arr;
    }

    @NonNull
    @Override
    public String getItem(int position){
        return  arr[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_karan_layout , parent , false);
        TextView t = convertView.findViewById(R.id.textView3);
        t.setText(getItem(position));
//        Before returning the view add the onclicklistener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked on item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
