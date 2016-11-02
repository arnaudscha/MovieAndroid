package com.example.aschaal.movieandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.aschaal.movieandroid.Adapter.PopularMovieAdapter;
import com.example.aschaal.movieandroid.Models.ListMovies;
import com.example.aschaal.movieandroid.Tasks.ConnectionTask;
import com.example.aschaal.movieandroid.Tasks.GetMoviesTask;

public class MainActivity extends AppCompatActivity implements RefreshActivity {

    public static ListMovies movies;
    public PopularMovieAdapter adapter;

    //Elements Graphiques.
    public ListView lv;
    public SwipeRefreshLayout swl;
    public Toolbar tb;
    public ImageView iv;

    //Task
    public GetMoviesTask taskGetMovies;
    public String currentName;
    public ConnectionTask taskConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentName = GetMoviesTask.POPULAR_NAME;
        movies = new ListMovies();

        //On recupere les composants graphiques.
        lv = (ListView) findViewById(R.id.content_main_list_view);
        swl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        tb = (Toolbar) findViewById(R.id.toolbar);

        swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(!swl.isRefreshing())
                getMovies();
            }
        });

        setSupportActionBar(tb);
        tb.setTitle(currentName);
        //On set un adapter qui permet de mettre en page touts les éléments de "movies".
        adapter = new PopularMovieAdapter(this, movies);
        //On peuple la listView avec l'adapter.

        if(!taskConnection.isConnected){
            taskConnection = new ConnectionTask(this);
            taskConnection.execute();
        }
        else{
            getMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startRefreshing() {
        if(swl != null)
            if(!swl.isRefreshing())
                swl.setRefreshing(true);
    }

    @Override
    public void stopRefreshing(String JSON) {
        taskGetMovies = new GetMoviesTask(this, currentName);
        taskConnection = new ConnectionTask(this);
        if(swl != null)
            if(swl.isRefreshing())
                swl.setRefreshing(false);
        if(JSON != ""){
            movies = ListMovies.CreateInstance(JSON);
            adapter = new PopularMovieAdapter(this, movies);
            lv.setAdapter(adapter);
            ((ArrayAdapter)lv.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void onConnectionTaskEnd() {
        if(!taskConnection.isCancelled()){
            taskConnection = new ConnectionTask(this);
        }
        if(ConnectionTask.token != null){
            if(ConnectionTask.token.token != null){
                //taskGetMovies.execute();
            }
        }
    }

    @Override
    public void onConnectionTaskEnd(Drawable d) {

    }

    public void getMovies(){
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        currentName = Utility.convertPref(preferences.getString("maintPageCategory", "1"));
        if(taskGetMovies != null){
            if(!taskGetMovies.isCancelled()){
                taskGetMovies.cancel(true);
            }
        }
        taskGetMovies = new GetMoviesTask(this, currentName);
        taskGetMovies.execute();
    }
}
