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
import com.example.zsamir.movieappintership.Common.SplashActivity;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AiringEpisodesActivity extends BaseActivity {

    private List<TVShow> TVShowList = new ArrayList<>();
    private List<TVShow> watchlist = new ArrayList<>();
    private UserListAdapter mTvSeriesAdapter;
    private Account user;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lists);

        mTvSeriesAdapter = new UserListAdapter(TVShowList);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.user_list_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            Gson gson = new Gson();
            user = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
            password = sharedPreferences.getString("PASSWORD","");
        }

        searchForAiringTVSeries();
        setTitle("Episodes airing today");

        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void searchForAiringTVSeries() {
        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){
                    ApiHandler.getInstance().validateToken(user.getUsername(), password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {
                            if(response!=null){
                                ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                    @Override
                                    public void success(Session response) {
                                        if(response!=null){

                                            ApiHandler.getInstance().requestAiringTodayTvSeries(1, new ApiHandler.TvSeriesListListener() {
                                                @Override
                                                public void success(TVShowList response) {
                                                    Log.d("SUCCESS","ENTERED");
                                                    for (TVShow t: response.getTVShow()) {
                                                        watchlist.add(t);
                                                    }
                                                    if(response.getTotalPages()>1){
                                                        for(int i = response.getTotalPages(); i>= 2; i--){
                                                            ApiHandler.getInstance().requestAiringTodayTvSeries(i, new ApiHandler.TvSeriesListListener() {
                                                                @Override
                                                                public void success(TVShowList response) {
                                                                    for (TVShow t: response.getTVShow()) {
                                                                        watchlist.add(t);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
                                                            @Override
                                                            public void success(TVShowList response) {
                                                                if(response!=null){

                                                                    for (TVShow t:response.getTVShow()) {
                                                                        for (TVShow tv:watchlist) {
                                                                            if(t.getId()==tv.getId()) {
                                                                                if(!TVShowList.contains(t))
                                                                                    TVShowList.add(t);
                                                                            }
                                                                        }
                                                                    }
                                                                    mTvSeriesAdapter.notifyDataSetChanged();
                                                                    //common.retainAll(watchlist);
                                                                    }
                                                            }
                                                        });
                                                    }else{
                                                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
                                                            @Override
                                                            public void success(TVShowList response) {
                                                                if(response!=null){

                                                                    for (TVShow t:response.getTVShow()) {
                                                                        for (TVShow tv:watchlist) {
                                                                            if(t.getId()==tv.getId()) {
                                                                                if(!TVShowList.contains(t))
                                                                                    TVShowList.add(t);
                                                                            }
                                                                        }
                                                                    }
                                                                    mTvSeriesAdapter.notifyDataSetChanged();

                                                                    //common.retainAll(watchlist);
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
                }
            }
        });
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
