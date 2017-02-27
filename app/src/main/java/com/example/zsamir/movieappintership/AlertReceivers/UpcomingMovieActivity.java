package com.example.zsamir.movieappintership.AlertReceivers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.UserListAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Common.SplashActivity;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMovieActivity extends BaseActivity {

    private List<Movie> moviesList = new ArrayList<>();
    private UserListAdapter mMovieAdapter;
    private Account user;
    private String pass;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lists);

        mMovieAdapter = new UserListAdapter(moviesList, 1);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.user_list_recyclerView);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            Gson gson = new Gson();
            user = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
            pass = sharedPreferences.getString("PASSWORD","");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        searchForUpcomingMovies(1);
        setTitle("Upcoming movies");

        mRecyclerView.setLayoutManager(layoutManager);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                searchForUpcomingMovies(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    private void searchForUpcomingMovies(final int page) {
        Log.d("FIRST", String.valueOf(first));
        if(first) {
            ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
                @Override
                public void success(Token response) {
                    if (response != null) {
                        ApiHandler.getInstance().validateToken(user.getUsername(), pass, response.getRequestToken(), new ApiHandler.TokenListener() {
                            @Override
                            public void success(Token response) {
                                if (response != null) {
                                    ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                        @Override
                                        public void success(Session response) {
                                            if (response != null) {
                                                ApiHandler.getInstance().requestUpcomingMovies(page, new ApiHandler.MovieListListener() {
                                                    @Override
                                                    public void success(MovieList response) {
                                                        if (response != null) {
                                                            //moviesList.addAll(response.getMovies());
                                                            for (Movie m:response.getMovies()) {
                                                                moviesList.add(m);
                                                            }
                                                            mMovieAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
            first = false;
        }else{
            ApiHandler.getInstance().requestUpcomingMovies(page, new ApiHandler.MovieListListener() {
                @Override
                public void success(MovieList response) {
                    if (response != null) {
                        moviesList.addAll(response.getMovies());
                        mMovieAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, SplashActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

