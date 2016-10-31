package com.example.aschaal.movieandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aschaal.movieandroid.Models.MovieResult;
import com.example.aschaal.movieandroid.Tasks.GetImageActivity;
import com.example.aschaal.movieandroid.Tasks.GetMovieTask;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity implements RefreshActivity {

    RelativeLayout loadingView;
    TextView titleView;
    TextView overViewView;
    ImageView imageView;

    MovieResult mr;
    GetMovieTask gmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        int id = 0;
        id = i.getIntExtra("idmovie", id);

        this.setTitle(title);

        loadingView = (RelativeLayout) findViewById(R.id.movie_activity_progressbar);
        titleView = (TextView) findViewById(R.id.movie_activity_title);
        overViewView = (TextView) findViewById(R.id.movie_activity_overView);
        imageView = (ImageView) findViewById(R.id.movie_activity_imageView);

        gmt = new GetMovieTask(this, id);

        gmt.execute();
    }

    @Override
    public void startRefreshing() {
        if(loadingView != null){
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopRefreshing(String JSON) {
        if(loadingView != null){
            loadingView.setVisibility(View.INVISIBLE);
            if(!JSON.contains("status_message")){
                mr = MovieResult.CreateInstance(JSON);

                titleView.setText(mr.originalTitle);
                overViewView.setText(mr.overview);
            }
        }
    }

    @Override
    public void onConnectionTaskEnd() {
        if(mr != null){
            new GetImageActivity(this, mr.backdropPath).execute();
        }
    }

    @Override
    public void onConnectionTaskEnd(Drawable d) {
        if(mr != null && imageView != null){
            imageView.setImageDrawable(d);
        }
    }
}
