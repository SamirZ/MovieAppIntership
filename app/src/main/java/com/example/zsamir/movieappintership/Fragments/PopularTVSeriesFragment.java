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
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;

public class PopularTVSeriesFragment extends Fragment {

    private static PopularTVSeriesFragment instance = null;
    ArrayList<TVShow> TVShowList = new ArrayList<>();
    ApiHandler movieDbApi = ApiHandler.getInstance();
    TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(TVShowList);
    int numberOfPages;

    public PopularTVSeriesFragment() {
    }

    public static PopularTVSeriesFragment getInstance() {
        if(instance == null) {
            instance = new PopularTVSeriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_popular_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.popular_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(TVShowList.size()==0){
            loadPopularTvSeries(1);
        }else{
            TVShowList.clear();
            loadPopularTvSeries(1);
            mTvSeriesAdapter.notifyDataSetChanged();
        }

        mRecyclerView.setLayoutManager(gridLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                loadPopularTvSeries(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }


    private void loadPopularTvSeries(int page) {
        movieDbApi.requestMostPopularTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                numberOfPages = response.getTotalPages();
                //addition
                for (TVShow t: response.getTVShow()) {
                    if(!TVShowList.contains(t))
                        TVShowList.add(t);
                }
                //TVSeriesList.addAll(response.getTVSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }


}