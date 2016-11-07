package com.example.aschaal.movieandroid.Taches;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aschaal.movieandroid.MainActivityFragment;
import com.example.aschaal.movieandroid.Models.Film;
import com.example.aschaal.movieandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aschaal on 02/11/2016.
 */

public class FilmTache extends AsyncTask<String, Void, List<Film>> {

    private final String LOG_TAG = FilmTache.class.getSimpleName();

    private MainActivityFragment fragment;

    public FilmTache(MainActivityFragment fragment) {
        this.fragment = fragment;
    }

    private List<Film> getMoviesDataFromJson(String jsonStr) throws JSONException {
        JSONObject movieJson = new JSONObject(jsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        List<Film> results = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            Film movieModel = new Film(movie);
            results.add(movieModel);
        }

        return results;
    }


    @Override
    protected List<Film> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {
            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";
            final String LANGUAGE_PARAM = "language";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(LANGUAGE_PARAM, "fr-FR")
                    .appendQueryParameter(API_KEY_PARAM, fragment.getString(R.string.tmdb_api_key))
                    .appendQueryParameter(SORT_BY_PARAM, params[0])
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
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
                return null;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMoviesDataFromJson(jsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(List<Film> movies) {
        if (movies != null) {
            if (fragment.getFilmAdapter()!= null) {
                fragment.getFilmAdapter().setData(movies);
            }
            fragment.films = new ArrayList<>();
            fragment.films.addAll(movies);
        }
    }
}