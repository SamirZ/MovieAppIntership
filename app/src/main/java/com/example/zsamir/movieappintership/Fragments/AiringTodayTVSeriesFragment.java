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

import com.example.zsamir.movieappintership.Adapters.TvSeriesAdapter;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class AiringTodayTVSeriesFragment extends Fragment {

    private static AiringTodayTVSeriesFragment instance = null;
    ArrayList<TVSeries> TVSeriesList = new ArrayList<>();
    ApiHandler movieDbApi = ApiHandler.getInstance();
    TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(TVSeriesList);
    int numberOfPages;

    public AiringTodayTVSeriesFragment() {
    }

    public static AiringTodayTVSeriesFragment getInstance() {
        if(instance == null) {
            instance = new AiringTodayTVSeriesFragment();
        }
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTvSeriesAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_airing_today_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.airing_today_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);

        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(TVSeriesList.size()==0)
            loadAiringTodayTvSeries(1);
        else{
            TVSeriesList.clear();
            loadAiringTodayTvSeries(1);
            mTvSeriesAdapter.notifyDataSetChanged();
        }

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page<=numberOfPages)
                    loadAiringTodayTvSeries(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        return rootView;
    }

    private void loadAiringTodayTvSeries(int page) {
        movieDbApi.requestAiringTodayTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVSeriesList response) {
                numberOfPages = response.getTotalPages();
                Log.d("Total pages", String.valueOf(numberOfPages));
                TVSeriesList.addAll(response.getTVSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }
}