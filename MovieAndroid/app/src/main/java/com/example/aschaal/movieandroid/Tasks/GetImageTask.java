package com.example.aschaal.movieandroid.Tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.aschaal.movieandroid.RefreshActivity;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by aschaal on 08/10/2016.
 */

public class GetImageTask extends AsyncTask<Void, Void, Void> {
    private RefreshingView currentView;
    private String url;
    public GetImageTask(RefreshingView v, String url){
        this.url = url;
        this.currentView = v;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            InputStream is = (InputStream) new URL("https://image.tmdb.org/t/p/w500"+ url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            currentView.onTaskEnd(d);
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
