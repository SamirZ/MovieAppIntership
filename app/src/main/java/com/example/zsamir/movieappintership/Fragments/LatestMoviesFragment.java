package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.MovieAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;


public class LatestMoviesFragment extends Fragment {

    private static LatestMoviesFragment instance = null;
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private ApiHandler apiHandler = ApiHandler.getInstance();
    private MovieAdapter mMovieAdapter = new MovieAdapter(moviesList);
    int numberOfPages;

    public LatestMoviesFragment() {
    }

    public static LatestMoviesFragment getInstance() {
        if(instance == null) {
            instance = new LatestMoviesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_latest_movies, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.latest_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieAdapter.notifyDataSetChanged();

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(moviesList.size()==0)
                loadLatestMovies(1);
            else{
                moviesList.clear();
                loadLatestMovies(1);
                mMovieAdapter.notifyDataSetChanged();
            }


            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //if(page+1<=numberOfPages)
                    loadLatestMovies(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            MovieAdapter mmMovieAdapter = new MovieAdapter(RealmUtils.getInstance().readLatestMoviesFromRealm());
            mRecyclerView.setAdapter(mmMovieAdapter);
            mmMovieAdapter.notifyDataSetChanged();
        }


        return rootView;
    }


    private void loadLatestMovies(final int page){
        apiHandler.requestLatestMovies(page, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                if(response!=null){
                    numberOfPages = response.getTotalPages();
                    // addition
                    for (Movie m: response.getMovies()) {
                        if(!moviesList.contains(m)){
                            Movie movie = RealmUtils.getInstance().readMovieFromRealm(m.getId());
                            if(movie!=null) {
                                m.highestrated = movie.highestrated;
                                m.popular = movie.popular;
                            }
                            m.latest = true;
                            m.allGenres = "";
                            for (int i = 0; i < m.getGenreIds().length; i++) {
                                if(m.getGenreIds()[i]!=m.getGenreIds().length-1)
                                    m.allGenres+=m.getGenreIds()[i]+",";
                                else
                                    m.allGenres+=m.getGenreIds()[i];
                            }
                            moviesList.add(m);
                        }
                    }
                    if(moviesList!=null)
                        RealmUtils.getInstance().addMoviesToRealm(moviesList,"LATEST");
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });
    }



}
