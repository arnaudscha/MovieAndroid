package com.example.aschaal.movieandroid;

/**
 * Created by aschaal on 02/11/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;

import com.example.aschaal.movieandroid.Adapter.FilmAdapter;
import com.example.aschaal.movieandroid.Models.Film;
import com.example.aschaal.movieandroid.Taches.FilmFavoriTask;
import com.example.aschaal.movieandroid.Taches.FilmTache;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridView;

    public GridView getGridView() { return gridView; }

    private FilmAdapter filmAdapter;

    public FilmAdapter getFilmAdapter(){ return filmAdapter; }

    private String sortBy = Outils.POPULARITY_DESC;

    public ArrayList<Film> films = null;

    public MainActivityFragment() {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        void onItemSelected(Film movie, View transitedView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);
        MenuItem action_sort_by_favorite = menu.findItem(R.id.action_sort_by_favorite);

        if (sortBy.contentEquals(Outils.POPULARITY_DESC)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (sortBy.contentEquals(Outils.RATING_DESC)) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        } else if (sortBy.contentEquals(Outils.FAVORITE)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_favorite.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                sortBy = Outils.POPULARITY_DESC;
                updateMovies(sortBy);
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                sortBy = Outils.RATING_DESC;
                updateMovies(sortBy);
                return true;
            case R.id.action_sort_by_favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                sortBy = Outils.FAVORITE;
                updateMovies(sortBy);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) view.findViewById(R.id.gridview_movies);

        filmAdapter = new FilmAdapter(getActivity(), new ArrayList<Film>());

        gridView.setAdapter(filmAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film movie = filmAdapter.getItem(position);
                View tv = null;
                if(view != null){
                    tv = view.findViewById(R.id.grid_item_image);
                }
                ((Callback) getActivity()).onItemSelected(movie, tv);
            }
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Outils.SORT_SETTING_KEY)) {
                sortBy = savedInstanceState.getString(Outils.SORT_SETTING_KEY);
            }

            if (savedInstanceState.containsKey(Outils.MOVIES_KEY)) {
                films = savedInstanceState.getParcelableArrayList(Outils.MOVIES_KEY);
                filmAdapter.setData(films);
            } else {
                updateMovies(sortBy);
            }
        } else {
            updateMovies(sortBy);
        }

        return view;
    }

    private void updateMovies(String sort_by) {
        if (!sort_by.contentEquals(Outils.FAVORITE)) {
            new FilmTache(this).execute(sort_by);
        } else {
            new FilmFavoriTask(this).execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!sortBy.contentEquals(Outils.POPULARITY_DESC)) {
            outState.putString(Outils.SORT_SETTING_KEY, sortBy);
        }
        if (films != null) {
            outState.putParcelableArrayList(Outils.MOVIES_KEY, films);
        }
        super.onSaveInstanceState(outState);
    }
}
