package com.example.jsonparser;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdaptor extends ArrayAdapter<Earthquake> {

//    Constructor method for EarthquakeAdaptor
    public EarthquakeAdaptor(Activity context, ArrayList<Earthquake> myArr) {
        super(context, 0 , myArr);
    }

    @Nullable
    @Override
    public Earthquake getItem(int position) {
        return super.getItem(position);
    }

    @SuppressWarnings("deprecation")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list , parent , false);
        }
        Earthquake currentObject =  getItem(position);
//        get all the textviews
        TextView magnitude = currentView.findViewById(R.id.magnitude);
        TextView location = currentView.findViewById(R.id.location);
        TextView time = currentView.findViewById(R.id.earthquake_time);
//        make the magnitude look better
        DecimalFormat decimal_f = new DecimalFormat("0.00");
        String pretty_magnitude = decimal_f.format(Float.parseFloat(currentObject.getMagnitude()));

//        Make a date object to beatify the date
        long timeInMilliseconds = Long.parseLong(currentObject.getTime());
        Date dt_obj = new Date(timeInMilliseconds);
//        Now format the date in pretty form
        SimpleDateFormat dt_format = new SimpleDateFormat("DD MMM, yyyy");
        String dateToDisplay = dt_format.format(dt_obj);
//

//        set all the values
        magnitude.setText(pretty_magnitude);
        location.setText(currentObject.getLocation());
        time.setText(dateToDisplay);

//        change the background of the magnitude
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

//        get the magnitude color value
        int magnitudeColor = getMagnitudeColor(Float.parseFloat(magnitude.getText().toString()));
        magnitudeCircle.setColor(magnitudeColor);

        return currentView;
    }

//    make the method
    private int getMagnitudeColor(Float val){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(val);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
