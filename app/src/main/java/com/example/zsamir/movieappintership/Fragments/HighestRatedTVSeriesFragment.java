package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;

import com.example.zsamir.movieappintership.Adapters.TvSeriesAdapter;
import com.example.zsamir.movieappintership.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class HighestRatedTVSeriesFragment extends Fragment {

    private static HighestRatedTVSeriesFragment instance = null;
    ArrayList<TvSeries> tvSeriesList = new ArrayList<>();
    ApiHandler movieDbApi = ApiHandler.getInstance();
    TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(tvSeriesList);

    public HighestRatedTVSeriesFragment() {
    }

    public static HighestRatedTVSeriesFragment getInstance() {
        if(instance == null) {
            instance = new HighestRatedTVSeriesFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highest_rated_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.highest_rated_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);

        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        loadInitialTopRatedTvSeries();
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextTopRatedTvSeries(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        return rootView;
    }

    private void loadInitialTopRatedTvSeries() {
        movieDbApi.requestHighestRatedTvSeries(new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TvSeriesList response) {
                tvSeriesList.clear();
                tvSeriesList.addAll(response.getTvSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadNextTopRatedTvSeries(int page) {
        movieDbApi.requestHighestRatedTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TvSeriesList response) {
                tvSeriesList.addAll(response.getTvSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }
}