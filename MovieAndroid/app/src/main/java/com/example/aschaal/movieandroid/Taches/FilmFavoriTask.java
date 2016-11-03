package com.example.aschaal.movieandroid.Taches;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.aschaal.movieandroid.Datas.Contrat;
import com.example.aschaal.movieandroid.MainActivityFragment;
import com.example.aschaal.movieandroid.Models.Film;
import com.example.aschaal.movieandroid.Outils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aschaal on 02/11/2016.
 */

public class FilmFavoriTask extends AsyncTask<Void, Void, List<Film>> {

    private MainActivityFragment fragment;

    public FilmFavoriTask(MainActivityFragment fragment) {
        this.fragment = fragment;
    }

    private List<Film> getFavoriteMoviesDataFromCursor(Cursor cursor) {
        List<Film> results = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Film movie = new Film(cursor);
                results.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return results;
    }

    @Override
    protected List<Film> doInBackground(Void... params) {
        Cursor cursor = fragment.getContext().getContentResolver().query(
                Contrat.MovieEntry.CONTENT_URI,
                Outils.MOVIE_COLUMNS,
                null,
                null,
                null
        );
        return getFavoriteMoviesDataFromCursor(cursor);
    }

    @Override
    protected void onPostExecute(List<Film> movies) {
        if (movies != null) {
            if (fragment.getFilmAdapter() != null) {
                fragment.getFilmAdapter().setData(movies);
            }
            fragment.films = new ArrayList<>();
            fragment.films.addAll(movies);
        }
    }
}