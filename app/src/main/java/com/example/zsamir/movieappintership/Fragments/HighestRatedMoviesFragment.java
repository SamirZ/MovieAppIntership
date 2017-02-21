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

public class HighestRatedMoviesFragment extends Fragment{

    private static HighestRatedMoviesFragment instance = null;
    ArrayList<Movie> moviesList = new ArrayList<>();
    ApiHandler apiHandler = ApiHandler.getInstance();
    MovieAdapter mMovieAdapter = new MovieAdapter(moviesList);
    int numberOfPages;

    public HighestRatedMoviesFragment() {
    }

    public static HighestRatedMoviesFragment getInstance() {
        if(instance == null) {
            instance = new HighestRatedMoviesFragment();
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highest_rated_movies, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.highest_rated_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        if(moviesList.size()==0)
            loadHighestRatedMovies(1);
        else{
            moviesList.clear();
            loadHighestRatedMovies(1);
            mMovieAdapter.notifyDataSetChanged();
        }

        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setLayoutManager(gridLayoutManager );
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                loadHighestRatedMovies(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setAdapter(mMovieAdapter);

        return rootView;
    }

    private void loadHighestRatedMovies(int page){
        apiHandler.requestHighestRatedMovies(page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                numberOfPages = response.getTotalPages();
                // addition
                for (Movie m: response.getMovies()) {
                    if(!moviesList.contains(m))
                        moviesList.add(m);
                }
                //moviesList.addAll(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }
}