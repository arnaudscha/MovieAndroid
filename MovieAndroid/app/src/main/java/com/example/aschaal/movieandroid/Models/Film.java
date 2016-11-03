package com.example.aschaal.movieandroid.Models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.aschaal.movieandroid.Outils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aschaal on 02/11/2016.
 */

public class Film implements Parcelable {

    private int id;
    private String title; // original_title
    private String image; // poster_path
    private String image2; // backdrop_path
    private String overview;
    private int rating; // vote_average
    private String date; // release_date

    public Film() {

    }

    public Film(JSONObject movie) throws JSONException {
        this.id = movie.getInt("id");
        this.title = movie.getString("original_title");
        this.image = movie.getString("poster_path");
        this.image2 = movie.getString("backdrop_path");
        this.overview = movie.getString("overview");
        this.rating = movie.getInt("vote_average");
        this.date = movie.getString("release_date");
    }

    public Film(Cursor cursor) {
        this.id = cursor.getInt(Outils.COL_MOVIE_ID);
        this.title = cursor.getString(Outils.COL_TITLE);
        this.image = cursor.getString(Outils.COL_IMAGE);
        this.image2 = cursor.getString(Outils.COL_IMAGE2);
        this.overview = cursor.getString(Outils.COL_OVERVIEW);
        this.rating = cursor.getInt(Outils.COL_RATING);
        this.date = cursor.getString(Outils.COL_DATE);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImage2() {
        return image2;
    }

    public String getOverview() {
        return overview;
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(image2);
        dest.writeString(overview);
        dest.writeInt(rating);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Film> CREATOR
            = new Parcelable.Creator<Film>() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    private Film(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        image2 = in.readString();
        overview = in.readString();
        rating = in.readInt();
        date = in.readString();
    }
}
