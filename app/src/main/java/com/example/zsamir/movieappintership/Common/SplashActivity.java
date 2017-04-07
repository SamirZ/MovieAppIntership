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
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.gson.Gson;

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
                AccountListsRequestHandler.getInstance().requestRatedMovies();
                AccountListsRequestHandler.getInstance().requestRatedTVSeries();
            }else{
                // LOAD OFFLINE LIKED,WATCHLIST AND RATED
                u.setFavMovieList(RealmUtils.getInstance().readRealmAccount().getFavMovieListInteger());
                u.setFavTVSeriesList(RealmUtils.getInstance().readRealmAccount().getFavTVShowListInteger());
                u.setWatchlistMovieList(RealmUtils.getInstance().readRealmAccount().getWatchMovieListInteger());
                u.setWatchlistTVSeriesList(RealmUtils.getInstance().readRealmAccount().getWatchTVShowListInteger());
                u.setRatedMovieList(RealmUtils.getInstance().readRealmAccount().getRatedMovieListInteger());
                u.setRatedTVSeriesList(RealmUtils.getInstance().readRealmAccount().getRatedTVShowListInteger());
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}