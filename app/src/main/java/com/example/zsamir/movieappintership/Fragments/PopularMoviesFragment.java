package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Adapters.MovieAdapter;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class PopularMoviesFragment extends Fragment{

    private static PopularMoviesFragment instance = null;
    ArrayList<Movie> moviesList = new ArrayList<>();
    ApiHandler apiHandler = ApiHandler.getInstance();
    MovieAdapter mMovieAdapter = new MovieAdapter(moviesList);

    public PopularMoviesFragment() {
    }

    public static PopularMoviesFragment getInstance() {
        if(instance == null) {
            instance = new PopularMoviesFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.popular_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        loadPopularMovies(1);

        mRecyclerView.setLayoutManager(gridLayoutManager );
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPopularMovies(page+1);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setAdapter(mMovieAdapter);

        return rootView;
    }


    private void loadPopularMovies(int page){
        apiHandler.requestMostPopularMovies(page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                moviesList.addAll(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }
}