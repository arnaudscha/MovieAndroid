package com.example.aschaal.movieandroid.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aschaal.movieandroid.Models.Token;
import com.example.aschaal.movieandroid.RefreshActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aschaal on 07/10/2016.
 */

public class ConnectionTask extends AsyncTask<Void, Void, Void> {
    public RefreshActivity currentActivity;
    public String response;
    public static Token token;

    public ConnectionTask(RefreshActivity activity){
        currentActivity = activity;
    }
    @Override
    protected Void doInBackground(Void... params) {
        ((Activity)currentActivity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentActivity.startRefreshing();
            }
        });

        currentActivity.startRefreshing();
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        response = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("https://api.themoviedb.org/3/authentication/token/new?api_key=d36e0292d838026d4bbf02fae3dfad22");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                response = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                response = null;
            }
            response = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            response = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        ((Activity)currentActivity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentActivity.stopRefreshing("");
                token = Token.CreateInstance(response);
                currentActivity.onConnectionTaskEnd();
            }
        });
        return null;
    }
}
