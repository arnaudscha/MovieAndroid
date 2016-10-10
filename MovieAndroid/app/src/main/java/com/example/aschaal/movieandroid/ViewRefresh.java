package com.example.aschaal.movieandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aschaal.movieandroid.Tasks.RefreshingView;

/**
 * Created by aschaal on 09/10/2016.
 */

public class ViewRefresh extends View implements RefreshingView {
    public ImageView imageView;
    public TextView textView;
    public TextView textViewRounded;
    public ViewRefresh(Context context) {
        super(context);

    }

    @Override
    public void onTaskEnd(Drawable d) {
        if(imageView != null)
            imageView.setImageDrawable(d);
    }
}
