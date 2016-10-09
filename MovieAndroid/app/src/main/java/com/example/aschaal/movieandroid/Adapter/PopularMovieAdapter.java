package com.example.aschaal.movieandroid.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aschaal.movieandroid.Models.ListMovies;
import com.example.aschaal.movieandroid.Models.PopularMovie;
import com.example.aschaal.movieandroid.R;
import com.example.aschaal.movieandroid.Tasks.GetImageTask;
import com.example.aschaal.movieandroid.Tasks.RefreshingView;
import com.example.aschaal.movieandroid.ViewRefresh;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by aschaal on 08/10/2016.
 */

public class PopularMovieAdapter extends ArrayAdapter {
    private ListMovies datas;
    private Context context;
    public PopularMovieAdapter(Context context, ListMovies datas) {
        super(context, R.layout.movie_layout);

        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return datas.getCount();
    }

    @Override
    public Object getItem(int position) {
        return datas.getItem(position);
    }

    @Override
    public int getPosition(Object item) {
        return datas.getIndex((PopularMovie)item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewRefresh holder;
        if(convertView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.movie_layout, null);
            holder = new ViewRefresh(context);
            holder.textView = (TextView) convertView.findViewById(R.id.movie_layout_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.movie_layout_img);
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.noposter));
            convertView.setTag(holder);
        }
        else{
            holder = (ViewRefresh) convertView.getTag();
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.noposter));
        }

        PopularMovie pm = (PopularMovie) getItem(position);
        if(pm != null){
            holder.textView.setText(pm.title);
            GetImageTask git = new GetImageTask((RefreshingView)holder, pm.posterPath);
            git.execute();
        }
        return convertView;
    }
}
