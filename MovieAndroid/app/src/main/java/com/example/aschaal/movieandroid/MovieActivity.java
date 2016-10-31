package com.example.aschaal.movieandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.aschaal.movieandroid.Tasks.GetMovieTask;

public class MovieActivity extends AppCompatActivity implements RefreshActivity {

    RelativeLayout loadingView;
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
        }
    }

    @Override
    public void onConnectionTaskEnd() {

    }
}
