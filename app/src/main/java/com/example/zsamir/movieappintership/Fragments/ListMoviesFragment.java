package com.example.zsamir.movieappintership.Fragments;

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
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class ListMoviesFragment extends Fragment {

    private ArrayList<Movie> moviesList = new ArrayList<>();
    private UserListAdapter mMovieAdapter;
    private Account user = MovieAppApplication.getUser();

    public ListMoviesFragment() {
        mMovieAdapter = new UserListAdapter(moviesList);
    }

    public static ListMoviesFragment newInstance() {
        return new ListMoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_list_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setAdapter(mMovieAdapter);

        if(moviesList.size()==0){
            loadMovies();
        }else{
            moviesList.clear();
            loadMovies();
            mMovieAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    private void loadMovies() {
        String type = getActivity().getIntent().getStringExtra("TYPE");

        Log.d("TYPE",type);
        if(type.equalsIgnoreCase("FAVORITES"))
            searchForFavoriteMovies();

        if(type.equalsIgnoreCase("WATCHLIST"))
            searchForWatchlistMovies();

        if(type.equalsIgnoreCase("RATINGS"))
            searchForRatedMovies();

    }

    private void searchForRatedMovies() {
        ApiHandler.getInstance().requestAccountRatedMovies(user.getId(), user.getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    moviesList.add(t);
                }
                mMovieAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountRatedMovies(user.getId(), user.getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    moviesList.add(t);
                                }
                                mMovieAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void searchForWatchlistMovies() {
        ApiHandler.getInstance().requestAccountWatchlistMovies(user.getId(), user.getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    moviesList.add(t);
                }
                mMovieAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistMovies(user.getId(), user.getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    moviesList.add(t);
                                }
                                mMovieAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void searchForFavoriteMovies() {
        ApiHandler.getInstance().requestAccountFavoriteMovies(user.getId(), user.getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    moviesList.add(t);
                }
                mMovieAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteMovies(user.getId(), user.getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    moviesList.add(t);
                                }
                                mMovieAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }
}