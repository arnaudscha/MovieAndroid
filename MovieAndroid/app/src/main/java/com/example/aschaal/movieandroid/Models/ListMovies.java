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

    public static ListMovies CreateInstance(String JSON){
        ListMovies result = null;

        Gson gson = new GsonBuilder().create();
        result = gson.fromJson(JSON, ListMovies.class);
        return result;
    }

    public ArrayList<String> getTitle(){
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < movies.size(); i++){
            result.add(movies.get(i).title);
        }

        return result;
    }
}
