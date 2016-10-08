package com.example.aschaal.movieandroid;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aschaal.movieandroid.Models.ListMovies;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RefreshActivity {

    public static ListMovies movies;
    public ArrayList<String> displayedArray;

    //Elements Graphiques.
    public ListView lv;
    public SwipeRefreshLayout swl;
    public Toolbar tb;

    //Task
    public GetMoviesTask taskGetMovies;
    public ConnectionTask taskConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskGetMovies = new GetMoviesTask(this);
        displayedArray = new ArrayList<String>();
        //On recupere les composants graphiques.
        lv = (ListView) findViewById(R.id.content_main_list_view);
        swl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        tb = (Toolbar) findViewById(R.id.toolbar);

        swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskGetMovies.execute();
            }
        });
        setSupportActionBar(tb);
        //On set un adapter qui permet de mettre en page touts les éléments de "movies".
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayedArray);
        //On peuple la listView avec l'adapter.
        lv.setAdapter(itemsAdapter);

        taskConnection = new ConnectionTask(this);
        taskConnection.execute();
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
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            //TODO ajouter l'asyncTask.
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
        taskGetMovies = new GetMoviesTask(this);
        taskConnection = new ConnectionTask(this);
        if(swl != null)
            if(swl.isRefreshing())
                swl.setRefreshing(false);
        if(JSON != ""){
            movies = ListMovies.CreateInstance(JSON);
            if(movies != null){
                if(movies.movies != null){
                    displayedArray.clear();
                    displayedArray.addAll(movies.getTitle());
                }
            }
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
}
