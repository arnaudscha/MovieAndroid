package com.example.aschaal.movieandroid.Datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aschaal on 02/11/2016.
 */

public class SqlHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + Contrat.MovieEntry.TABLE_NAME + " (" +
                Contrat.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contrat.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                Contrat.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contrat.MovieEntry.COLUMN_IMAGE + " TEXT, " +
                Contrat.MovieEntry.COLUMN_IMAGE2 + " TEXT, " +
                Contrat.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                Contrat.MovieEntry.COLUMN_RATING + " INTEGER, " +
                Contrat.MovieEntry.COLUMN_DATE + " TEXT);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contrat.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
