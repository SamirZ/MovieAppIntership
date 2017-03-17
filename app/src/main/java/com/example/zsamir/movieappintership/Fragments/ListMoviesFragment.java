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
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListMoviesFragment extends Fragment {

    private List<Movie> moviesList = new ArrayList<>();
    private UserListAdapter mMovieAdapter;
    private Account user;

    public ListMoviesFragment() {
        mMovieAdapter = new UserListAdapter(moviesList, 1);
    }

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
        mRecyclerView.setAdapter(mMovieAdapter);

        if(moviesList.size()==0){
            loadMovies(1);
        }else{
            moviesList.clear();
            loadMovies(1);
        }

        mRecyclerView.setLayoutManager(layoutManager);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                loadMovies(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        return rootView;
    }

    private void loadMovies(int page) {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            Log.d("TYPE", type);
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
                mMovieAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForWatchlistMovies(int page) {
        ApiHandler.getInstance().requestAccountWatchlistMovies(user.getId(), user.getSessionId(), page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForFavoriteMovies(int page) {
        ApiHandler.getInstance().requestAccountFavoriteMovies(user.getId(), user.getSessionId(), page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }
}