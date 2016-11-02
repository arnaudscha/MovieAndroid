package com.example.aschaal.movieandroid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.aschaal.movieandroid.Tasks.GetMoviesTask;

/**
 * Created by aschaal on 06/10/2016.
 */

public class Utility {
    public static String convertPref(String i){
        switch (i){
            case "1":
                return GetMoviesTask.UPCOMING_NAME;
            case "2":
                return GetMoviesTask.NOW_PLAYING_NAME;
            case "3":
                return GetMoviesTask.POPULAR_NAME;
            case "4":
                return GetMoviesTask.TOP_RATED_NAME;
            default:
                return GetMoviesTask.NOW_PLAYING_NAME;
        }
    }
}
