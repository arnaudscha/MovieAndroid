package com.example.aschaal.movieandroid.Models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aschaal on 31/10/2016.
 */

public class MovieResult {

    @SerializedName("adult")
    public Boolean adult;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("belongs_to_collection")
    public String belongs_to_collection;

    @SerializedName("budget")
    public int budget;

    @SerializedName("hompage")
    public String hompage;

    @SerializedName("id")
    public String id;

    @SerializedName("imdb_id")
    public String imdb_id;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("overview")
    public String overview;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("vote_average")
    public double vote_average;

    @SerializedName("vote_count")
    public double vote_count;

    public static MovieResult CreateInstance(String JSON){
        String mJsonString = "...";
        JsonParser parser = new JsonParser();
        JsonElement mJson =  parser.parse(mJsonString);

        return new Gson().fromJson(mJson, MovieResult.class);
    }
}
