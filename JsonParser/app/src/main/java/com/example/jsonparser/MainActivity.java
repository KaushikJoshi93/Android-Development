package com.example.jsonparser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


class EarthquakeAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    public EarthquakeAsyncTaskLoader( Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.d("enter" , "Entered in doInBackground");
        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = null;
        try {
            Log.d("try" , "entered in try block");
            QueryUtils queryUtils = new QueryUtils();
            earthquakes = queryUtils.extractEarthquakes();

            Log.d("after try" , "In after earthquakes");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            super.onPostExecute(earthquakes);
//            Log.d("completed" , "completed data fetching....");
//            updateUI(earthquakes);
//            progressDialog.hide();
//        }
}

public class MainActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loaing Data..Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        android.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0 , null , this);

    }

    private void updateUI(ArrayList<Earthquake> earthquakesLst){
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdaptor newAdaptor = new EarthquakeAdaptor(this , earthquakesLst);
        earthquakeListView.setAdapter(newAdaptor);
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        EarthquakeAsyncTaskLoader earthquakeAsyncTaskLoader = new EarthquakeAsyncTaskLoader(this);
        return earthquakeAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {

    }


   
}