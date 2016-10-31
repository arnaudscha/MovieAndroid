package com.example.aschaal.movieandroid.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aschaal on 31/10/2016.
 */

public class Collection {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;
}
