package com.example.jsonparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    private static String SAMPLE_JSON_RESPONSE;

    /**
     * Sample JSON response for a USGS query
     */


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public QueryUtils() throws ExecutionException, InterruptedException {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public  ArrayList<Earthquake> extractEarthquakes() throws ExecutionException, InterruptedException {
        // Create an empty ArrayList that we can start adding earthquakes to
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        try {
            URL url = new URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&limit=10");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(9000);
            urlConnection.setReadTimeout(9000);
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream );
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            SAMPLE_JSON_RESPONSE = stringBuilder.toString();
        }
        catch(Exception e){
            Log.d("AppError" , "Some Error occured in async task..");
        }
        Log.d("responsejson","sample json response is: "+SAMPLE_JSON_RESPONSE);
        try {
//        create a json object class
            JSONObject root_json = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray earthquakeArray = root_json.getJSONArray("features");
//            We will loop through each values
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                String magnitude = properties.getString("mag");
                String location = properties.getString("place");
                String time = properties.getString("time");
                Earthquake earthquake = new Earthquake(magnitude, time, location);
                earthquakes.add(earthquake);
            }


            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ErrorInApp" , "There is a error in json....");
        }

        // Return the list of earthquakes
        return earthquakes;
    }


}





