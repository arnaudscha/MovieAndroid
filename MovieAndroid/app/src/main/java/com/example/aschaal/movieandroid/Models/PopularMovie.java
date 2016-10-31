package com.example.aschaal.movieandroid.Models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aschaal on 07/10/2016.
 */

public class PopularMovie {
    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("overview")
    public String overView;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("genre_ids")
    public ArrayList<Integer> genresIds;

    @SerializedName("id")
    public int id;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("title")
    public String title;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("vote_count")
    public String voteCount;

    @SerializedName("video")
    public boolean video;

    @SerializedName("vote_average")
    public String voteAverage;

    public static PopularMovie CreateInstance(String JSON){
        String mJsonString = "...";
        JsonParser parser = new JsonParser();
        JsonElement mJson =  parser.parse(mJsonString);

        return new Gson().fromJson(mJson, PopularMovie.class);
    }
}
