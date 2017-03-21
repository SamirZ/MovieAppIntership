package com.example.zsamir.movieappintership.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmInteger;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);

        loadFromPrefference();

        splashStart();
    }

    private void splashStart() {
        Thread splashThread = new Thread(){

            @Override
            public void run(){
                try {
                    sleep(1500);
                    Intent i = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        splashThread.start();
    }

    private void loadFromPrefference() {
        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            final Gson gson = new Gson();
            Account u = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
            String pass = sharedPreferences.getString("PASSWORD","");

            requestToken(u.getUsername(),pass);
            MovieAppApplication.setUser(u);
            if(isNetworkAvailable()){
                AccountListsRequestHandler.getInstance().requestFavoriteTVSeries();
                AccountListsRequestHandler.getInstance().requestWatchlistTVSeries();
                AccountListsRequestHandler.getInstance().requestWatchlistMovies();
                AccountListsRequestHandler.getInstance().requestFavoriteMovies();
            }else{
                // LOAD OFFLINE LIKED,WATCHLIST AND RATED
                u.setFavMovieList(RealmUtils.getInstance().readRealmAccount().getFavMovieListInteger());
                u.setFavTVSeriesList(RealmUtils.getInstance().readRealmAccount().getFavTVShowListInteger());
                u.setWatchlistMovieList(RealmUtils.getInstance().readRealmAccount().getWatchMovieListInteger());
                u.setWatchlistTVSeriesList(RealmUtils.getInstance().readRealmAccount().getWatchTVShowListInteger());
                MovieAppApplication.setUser(u);
            }
        }
    }

    private void requestToken(final String username, final String password){
        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){
                    ApiHandler.getInstance().validateToken(username, password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {

                        }
                    });
                }
            }
        });

    }

    private void requestFavoriteMovies(){
        ApiHandler.getInstance().requestAccountFavoriteMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountFavMovies(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountFavMovies(l);

                            }
                        });
                    }
                }
            }
        });
    }

    private void requestFavoriteTVSeries(){
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVShowList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountFavTVShow(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountFavTVShow(l);
                            }
                        });
                    }
                }
            }
        });
    }

    private void requestWatchlistMovies(){
        ApiHandler.getInstance().requestAccountWatchlistMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountWatchlistMovies(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountWatchlistMovies(l);
                            }
                        });
                    }
                }
            }
        });
    }

    private void requestWatchlistTVSeries(){
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVShowList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);
                            }
                        });
                    }
                }

            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
