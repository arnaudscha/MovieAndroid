package com.example.aschaal.movieandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aschaal.movieandroid.Models.MovieResult;
import com.example.aschaal.movieandroid.Tasks.GetImageActivity;
import com.example.aschaal.movieandroid.Tasks.GetMovieTask;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity implements RefreshActivity {

    FloatingActionButton fab;
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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        gmt = new GetMovieTask(this, id);

        gmt.execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());

                b.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.circle)
                        .setTicker("Hearty365")
                        .setContentTitle("Default notification")
                        .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                        .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                        .setContentIntent(contentIntent)
                        .setContentInfo("Info");


                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, b.build());
            }
        });

        ScrollView sv = (ScrollView) findViewById(R.id.movie_activity_scrollView);

        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int diff = scrollY - oldScrollY;
                int h = imageView.getHeight();
                float supp = (float)((float)diff / (float)h);
                float alpha = imageView.getAlpha() - supp;
                imageView.setAlpha(alpha);
            }
        });
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
                overViewView.setText(mr.overview + mr.overview + mr.overview);
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
