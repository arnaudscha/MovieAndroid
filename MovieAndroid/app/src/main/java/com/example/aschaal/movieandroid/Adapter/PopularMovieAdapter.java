package com.example.aschaal.movieandroid.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.aschaal.movieandroid.Models.ListMovies;

/**
 * Created by aschaal on 08/10/2016.
 */

public class PopularMovieAdapter extends ArrayAdapter {
    private ListMovies datas;

    public PopularMovieAdapter(Context context, int resource, ListMovies datas) {
        super(context, resource);

        this.datas = datas;
    }
}
