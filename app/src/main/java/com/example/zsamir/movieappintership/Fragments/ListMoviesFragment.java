package com.example.zsamir.movieappintership.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.UserListAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListMoviesFragment extends Fragment {

    private List<Movie> moviesList = new ArrayList<>();
    private UserListAdapter mMovieAdapter = new UserListAdapter(moviesList, 1);
    private Account user;

    public static ListMoviesFragment newInstance() {
        return new ListMoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_list_recyclerView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            Gson gson = new Gson();
            user = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        if(((BaseActivity)getActivity()).isNetworkAvailable()) {
            
            loadMovies(1);

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if(page>1)
                        loadMovies(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            loadMoviesOffline();
        }
        return rootView;
    }

    private void loadMoviesOffline() {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            if (type.equalsIgnoreCase("FAVORITES"))
                searchForFavoriteMoviesOffline();
            else if (type.equalsIgnoreCase("WATCHLIST"))
                searchForWatchlistMoviesOffline();
            else if (type.equalsIgnoreCase("RATINGS"))
                searchForRatedMoviesOffline();
        }
    }

    private void loadMovies(int page) {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            if(page==1){
                if (type.equalsIgnoreCase("FAVORITES"))
                    RealmUtils.getInstance().removeRealmAccountFavMoviesData();
                else if (type.equalsIgnoreCase("WATCHLIST"))
                    RealmUtils.getInstance().removeRealmAccountWatchMoviesData();
                else if (type.equalsIgnoreCase("RATINGS"))
                    RealmUtils.getInstance().removeRealmAccountRatedMoviesData();
            }

            if (type.equalsIgnoreCase("FAVORITES"))
                searchForFavoriteMovies(page);
            else if (type.equalsIgnoreCase("WATCHLIST"))
                searchForWatchlistMovies(page);
            else if (type.equalsIgnoreCase("RATINGS"))
                searchForRatedMovies(page);
        }
    }

    private void searchForRatedMovies(int page) {
        ApiHandler.getInstance().requestAccountRatedMovies(user.getId(), user.getSessionId(), page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                RealmUtils.getInstance().addRealmAccountRatedMoviesData(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForWatchlistMovies(int page) {
        ApiHandler.getInstance().requestAccountWatchlistMovies(user.getId(), user.getSessionId(), page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                RealmUtils.getInstance().addRealmAccountWatchMoviesData(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForFavoriteMovies(int page) {
        ApiHandler.getInstance().requestAccountFavoriteMovies(user.getId(), user.getSessionId(), page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                RealmUtils.getInstance().addRealmAccountFavMoviesData(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }


    private void searchForRatedMoviesOffline() {
        moviesList.addAll(RealmUtils.getInstance().readRealmAccount().getRatedMovies());
        mMovieAdapter.notifyDataSetChanged();
    }

    private void searchForWatchlistMoviesOffline() {
        moviesList.addAll(RealmUtils.getInstance().readRealmAccount().getWatchMovies());
        mMovieAdapter.notifyDataSetChanged();
    }

    private void searchForFavoriteMoviesOffline() {
        moviesList.addAll(RealmUtils.getInstance().readRealmAccount().getFavMovies());
        mMovieAdapter.notifyDataSetChanged();
    }
}