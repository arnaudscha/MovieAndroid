package com.example.aschaal.movieandroid;

/**
 * Created by aschaal on 02/11/2016.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.aschaal.movieandroid.Adapter.BandeAnnonceAdapter;
import com.example.aschaal.movieandroid.Adapter.CritiqueAdapter;
import com.example.aschaal.movieandroid.Datas.Contrat;
import com.example.aschaal.movieandroid.Models.BandeAnnonce;
import com.example.aschaal.movieandroid.Models.Critique;
import com.example.aschaal.movieandroid.Models.Film;
import com.example.aschaal.movieandroid.Taches.BandeAnnonceTask;
import com.example.aschaal.movieandroid.Taches.CritiqueTask;
import com.linearlistview.LinearListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailActivityFragment extends Fragment {

    public static final String TAG = DetailActivityFragment.class.getSimpleName();

    static final String DETAIL_MOVIE = "DETAIL_MOVIE";

    private Film film;
    public Film getFilm() { return film; }

    private TextView resume;
    private TextView dateView;
    private TextView votes;

    private LinearListView bandeAnnonces;
    private LinearListView critiques;

    private CardView critiquesCardview;
    public CardView getCritiquesCardview() { return critiquesCardview; }
    private CardView bandeAnnoncesCardview;
    public CardView getBandeAnnoncesCardview() { return bandeAnnoncesCardview; }

    private BandeAnnonceAdapter bandeAnnonceAdapter;
    public BandeAnnonceAdapter getBandeAnnonceAdapter() { return bandeAnnonceAdapter; }
    private CritiqueAdapter critiqueAdapter;
    public CritiqueAdapter getCritiqueAdapter() { return critiqueAdapter; }

    private ScrollView layout;

    private Button addFav;
    private Button share;

    private ShareActionProvider sap;
    public ShareActionProvider getSap() { return sap; }

    // the first trailer video to share
    public BandeAnnonce bandeAnnonce;
    public DetailActivity activity;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            film = arguments.getParcelable(DetailActivityFragment.DETAIL_MOVIE);
        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        layout = (ScrollView) rootView.findViewById(R.id.detail_layout);

        if (film != null) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.INVISIBLE);
        }

        addFav = (Button) rootView.findViewById(R.id.addFav);
        share = (Button) rootView.findViewById(R.id.share);

        resume = (TextView) rootView.findViewById(R.id.detail_overview);
        dateView = (TextView) rootView.findViewById(R.id.detail_date);
        votes = (TextView) rootView.findViewById(R.id.detail_vote_average);

        bandeAnnonces = (LinearListView) rootView.findViewById(R.id.detail_trailers);
        critiques = (LinearListView) rootView.findViewById(R.id.detail_reviews);

        critiquesCardview = (CardView) rootView.findViewById(R.id.detail_reviews_cardview);
        bandeAnnoncesCardview = (CardView) rootView.findViewById(R.id.detail_trailers_cardview);

        bandeAnnonceAdapter = new BandeAnnonceAdapter(getActivity(), new ArrayList<BandeAnnonce>());
        bandeAnnonces.setAdapter(bandeAnnonceAdapter);

        bandeAnnonces.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView linearListView, View view,
                                    int position, long id) {
                BandeAnnonce trailer = bandeAnnonceAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);
            }
        });

        final ImageView iv = (ImageView) rootView.findViewById(R.id.detail_image);

        critiqueAdapter = new CritiqueAdapter(getActivity(), new ArrayList<Critique>());
        critiques.setAdapter(critiqueAdapter);

        if (film != null) {
            String image_url = Outils.buildImageUrl(342, film.getImage2());
            Glide.with(this).load(image_url).into(iv);

            resume.setText(film.getOverview());

            String movie_date = film.getDate();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String date = DateUtils.formatDateTime(getActivity(),
                        formatter.parse(movie_date).getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
                dateView.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            votes.setText(Integer.toString(film.getRating()));
        }

        NestedScrollView sv = (NestedScrollView) activity.findViewById(R.id.scrollView_detail);
        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float diff = (float)scrollY - (float)oldScrollY;
                float h = (float) iv.getHeight();
                float a = iv.getAlpha();
                iv.setAlpha(
                    a - diff / h
                );
            }
        });
        if(activity != null){
            initActionBar(activity);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (film != null) {
            new BandeAnnonceTask(this).execute(Integer.toString(film.getId()));
            new CritiqueTask(this).execute(Integer.toString(film.getId()));
        }
    }

    private void initActionBar(DetailActivity da){
        Toolbar actionBar = (Toolbar) da.findViewById(R.id.toolbar);
        if(film != null){

            String image_url = Outils.buildImageUrl(342, film.getImage());
            Glide.with(this).load(image_url).asBitmap().into(target);
        }
        da.setSupportActionBar(actionBar);
        da.getSupportActionBar().setTitle(film.getTitle());
        da.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        da.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
            int c = Outils.getAverageColor(resource);

            ImageView iv = (ImageView) getActivity().findViewById(R.id.expandedImage);
            CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) getActivity()
                    .findViewById(R.id.collapsing_toolbar);

            ctl.setContentScrim(new ColorDrawable(c));
            iv.setImageBitmap(resource);

            if(share != null && addFav != null){
                share.setBackground(new ColorDrawable(c));
                addFav.setBackground(new ColorDrawable(c));
            }

        }
    };
}
