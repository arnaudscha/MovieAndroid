package com.example.aschaal.movieandroid.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;

import java.util.ArrayList;

/**
 * Created by aschaal on 31/10/2016.
 */

public class MovieResult {

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("belongs_to_collection")
    public Collection belongsCollection;

    @SerializedName("budget")
    public int budget;

    @SerializedName("genres")
    public ArrayList<genre> genres;

    @SerializedName("id")
    public int id;

    @SerializedName("imdb_id")
    public String imdbId;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("overview")
    public String overview;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("vote_count")
    public double voteCount;

    public static MovieResult CreateInstance(String JSON){
        MovieResult result = null;

        Gson gson = new GsonBuilder().create();
        result = gson.fromJson(JSON, MovieResult.class);
        return result;
    }
}
