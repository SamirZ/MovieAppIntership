package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Adapters.MovieAdapter;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;

public class PopularMoviesFragment extends Fragment{

    private static PopularMoviesFragment instance = null;
    ArrayList<Movie> moviesList = new ArrayList<>();
    ApiHandler apiHandler = ApiHandler.getInstance();
    MovieAdapter mMovieAdapter = new MovieAdapter(moviesList);
    int numberOfPages;

    public PopularMoviesFragment() {
    }

    public static PopularMoviesFragment getInstance() {
        if(instance == null) {
            instance = new PopularMoviesFragment();
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.popular_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieAdapter.notifyDataSetChanged();

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(moviesList.size()==0)
                loadPopularMovies(1);
            else{
                moviesList.clear();
                loadPopularMovies(1);
            }

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //if(page+1<=numberOfPages)
                    loadPopularMovies(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            MovieAdapter mmMovieAdapter = new MovieAdapter(RealmUtils.getInstance().readPopularMoviesFromRealm());
            mRecyclerView.setAdapter(mmMovieAdapter);
            mmMovieAdapter.notifyDataSetChanged();
        }


        return rootView;
    }


    private void loadPopularMovies(final int page){
        apiHandler.requestMostPopularMovies(page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                if(response!=null){
                    numberOfPages = response.getTotalPages();
                    // addition
                    for (Movie m: response.getMovies()) {
                        if(!moviesList.contains(m)){
                            Movie movie = RealmUtils.getInstance().readMovieFromRealm(m.getId());
                            if(movie!=null) {
                                m.latest = movie.latest;
                                m.highestrated = movie.highestrated;
                            }
                            m.popular = true;
                            m.allGenres = "";
                            for (int i = 0; i < m.getGenreIds().length; i++) {
                                m.allGenres+=m.getGenreIds()[i]+",";
                            }
                            m.allGenres = m.allGenres.substring(0, m.allGenres.length()-1);
                            moviesList.add(m);
                        }
                    }
                    Log.d("POPULAR M SIZE", String.valueOf(moviesList.size()));
                    RealmUtils.getInstance().addMoviesToRealm(moviesList,"POPULAR");
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}