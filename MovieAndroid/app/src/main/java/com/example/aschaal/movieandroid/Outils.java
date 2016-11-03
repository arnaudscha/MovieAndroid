package com.example.aschaal.movieandroid;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.aschaal.movieandroid.Datas.Contrat;

/**
 * Created by aschaal on 02/11/2016.
 */

public class Outils {

    public static final String SORT_SETTING_KEY = "sort_setting";
    public static final String POPULARITY_DESC = "popularity.desc";
    public static final String RATING_DESC = "vote_average.desc";
    public static final String FAVORITE = "favorite";
    public static final String MOVIES_KEY = "movies";


    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_IMAGE = 3;
    public static final int COL_IMAGE2 = 4;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_RATING = 6;
    public static final int COL_DATE = 7;

    public static final String[] MOVIE_COLUMNS = {
            Contrat.MovieEntry._ID,
            Contrat.MovieEntry.COLUMN_MOVIE_ID,
            Contrat.MovieEntry.COLUMN_TITLE,
            Contrat.MovieEntry.COLUMN_IMAGE,
            Contrat.MovieEntry.COLUMN_IMAGE2,
            Contrat.MovieEntry.COLUMN_OVERVIEW,
            Contrat.MovieEntry.COLUMN_RATING,
            Contrat.MovieEntry.COLUMN_DATE
    };

    public static int isFavorited(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                Contrat.MovieEntry.CONTENT_URI,
                null,   // projection
                Contrat.MovieEntry.COLUMN_MOVIE_ID + " = ?", // selection
                new String[] { Integer.toString(id) },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }

    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }

    public static int getAverageColor(Bitmap b) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(b, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }
}
