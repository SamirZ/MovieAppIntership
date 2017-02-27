package com.example.zsamir.movieappintership.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.google.gson.Gson;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends BaseActivity {

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
            MovieAppApplication.setUser(u);
            requestToken(u.getUsername(),pass);
            requestFavoriteTVSeries();
            requestWatchlistTVSeries();
            requestWatchlistMovies();
            requestFavoriteMovies();
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
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                                }
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
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                                }
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
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                                }
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
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                                }
                            }
                        });
                    }
                }

            }
        });
    }

}
