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
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class LatestTVSeriesFragment extends Fragment {

    private static LatestTVSeriesFragment instance = null;
    private ArrayList<TvSeries> tvSeriesList = new ArrayList<>();
    private ApiHandler movieDbApi = ApiHandler.getInstance();
    private TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(tvSeriesList);
    int numberOfPages;

    public LatestTVSeriesFragment() {
    }

    public static LatestTVSeriesFragment getInstance() {
        if(instance == null) {
            instance = new LatestTVSeriesFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_latest_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.latest_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(tvSeriesList.size()==0)
            loadLatestTvSeries(1);
        else{
            tvSeriesList.clear();
            loadLatestTvSeries(1);
            mTvSeriesAdapter.notifyDataSetChanged();
        }

        mRecyclerView.setLayoutManager(gridLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                    loadLatestTvSeries(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }

    private void loadLatestTvSeries(int page) {
        movieDbApi.requestLatestTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TvSeriesList response) {
                numberOfPages = response.getTotalPages();
                //
                for (TvSeries t: response.getTvSeries()) {
                    if(!tvSeriesList.contains(t))
                        tvSeriesList.add(t);
                }
                //tvSeriesList.addAll(response.getTvSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }


}
