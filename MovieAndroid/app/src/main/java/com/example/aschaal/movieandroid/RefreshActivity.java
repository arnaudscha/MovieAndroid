package com.example.aschaal.movieandroid;

import android.graphics.drawable.Drawable;

/**
 * Created by aschaal on 06/10/2016.
 */

public interface RefreshActivity {
    public void startRefreshing();
    public void stopRefreshing(String JSON);
    public void onConnectionTaskEnd();
    public void onConnectionTaskEnd(Drawable d);
}
