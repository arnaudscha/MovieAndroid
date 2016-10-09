package com.example.aschaal.movieandroid.Models;

import android.graphics.Movie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aschaal on 08/10/2016.
 */

public class ListMovies {

    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public ArrayList<PopularMovie> movies;

    public ListMovies(){
        page = 0;
        movies = new ArrayList<PopularMovie>();
    }
    public static ListMovies CreateInstance(String JSON){
        ListMovies result = null;

        Gson gson = new GsonBuilder().create();
        result = gson.fromJson(JSON, ListMovies.class);
        return result;
    }

    public int getCount(){
        return movies.size();
    }

    public PopularMovie getItem(int i){
        return movies.get(i);
    }

    public int getIndex(PopularMovie pm){
        return movies.indexOf(pm);
    }
}
