package com.example.aschaal.movieandroid.Taches;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.aschaal.movieandroid.DetailActivityFragment;
import com.example.aschaal.movieandroid.Models.BandeAnnonce;
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

public class BandeAnnonceTask extends AsyncTask<String, Void, List<BandeAnnonce>> {

    private final String LOG_TAG = BandeAnnonceTask.class.getSimpleName();

    private DetailActivityFragment fragment;

    public BandeAnnonceTask(DetailActivityFragment fragment) {
        this.fragment = fragment;
    }

    private List<BandeAnnonce> getTrailersDataFromJson(String jsonStr) throws JSONException {
        JSONObject trailerJson = new JSONObject(jsonStr);
        JSONArray trailerArray = trailerJson.getJSONArray("results");

        List<BandeAnnonce> results = new ArrayList<>();

        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            // Only show Trailers which are on Youtube
            if (trailer.getString("site").contentEquals("YouTube")) {
                BandeAnnonce trailerModel = new BandeAnnonce(trailer);
                results.add(trailerModel);
            }
        }

        return results;
    }

    @Override
    protected List<BandeAnnonce> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {
            final String BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] + "/videos";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, fragment.getString(R.string.tmdb_api_key))
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
            return getTrailersDataFromJson(jsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(List<BandeAnnonce> trailers) {
        if (trailers != null) {
            if (trailers.size() > 0) {
                fragment.getBandeAnnoncesCardview().setVisibility(View.VISIBLE);
                if (fragment.getBandeAnnonceAdapter() != null) {
                    fragment.getBandeAnnonceAdapter().clear();
                    for (BandeAnnonce trailer : trailers) {
                        fragment.getBandeAnnonceAdapter().add(trailer);
                    }
                }

                fragment.bandeAnnonce = trailers.get(0);
                if (fragment.getSap() != null) {
                    fragment.getSap().setShareIntent(createShareMovieIntent(
                            fragment.getFilm(),
                            fragment.bandeAnnonce
                    ));
                }
            }
        }
    }

    public static Intent createShareMovieIntent(Film f, BandeAnnonce b) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, f.getTitle() + " " +
                "http://www.youtube.com/watch?v=" + b.getKey());
        return shareIntent;
    }
}