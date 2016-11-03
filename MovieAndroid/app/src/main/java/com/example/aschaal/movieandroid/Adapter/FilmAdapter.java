package com.example.aschaal.movieandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aschaal.movieandroid.Models.Film;
import com.example.aschaal.movieandroid.R;

import java.util.List;

/**
 * Created by aschaal on 02/11/2016.
 */

public class FilmAdapter extends BaseAdapter {

    private final Context c;
    private final LayoutInflater i;

    private final Film f = new Film();

    private List<Film> films;

    public FilmAdapter(Context context, List<Film> objects) {
        c = context;
        i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        films = objects;
    }

    public Context getContext() {
        return c;
    }

    public void add(Film object) {
        synchronized (f) {
            films.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (f) {
            films.clear();
        }
        notifyDataSetChanged();
    }

    public void setData(List<Film> data) {
        clear();
        for (Film film : data) {
            add(film);
        }
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Film getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = i.inflate(R.layout.grid_item_movie, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final Film movie = getItem(position);

        String image_url = "http://image.tmdb.org/t/p/w185" + movie.getImage();

        viewHolder = (ViewHolder) view.getTag();

        Glide.with(getContext()).load(image_url).into(viewHolder.imageView);

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_image);
        }
    }
}
