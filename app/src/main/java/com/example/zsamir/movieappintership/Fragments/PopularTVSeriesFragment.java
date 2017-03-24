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
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;

public class PopularTVSeriesFragment extends Fragment {

    private static PopularTVSeriesFragment instance = null;
    ArrayList<TVShow> tvShowList = new ArrayList<>();
    ApiHandler movieDbApi = ApiHandler.getInstance();
    TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(tvShowList);
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
    public void onResume() {
        super.onResume();
        mTvSeriesAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.popular_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        mTvSeriesAdapter.notifyDataSetChanged();

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(tvShowList.size()==0){
                loadPopularTvSeries(1);
            }else{
                tvShowList.clear();
                loadPopularTvSeries(1);
                mTvSeriesAdapter.notifyDataSetChanged();
            }
            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //if(page+1<=numberOfPages)
                    loadPopularTvSeries(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            tvShowList.clear();
            tvShowList.addAll(RealmUtils.getInstance().readPopularTVShowsFromRealm());
            mTvSeriesAdapter.notifyDataSetChanged();
        }
        return rootView;
    }


    private void loadPopularTvSeries(final int page) {
        movieDbApi.requestMostPopularTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                numberOfPages = response.getTotalPages();
                //addition
                for (TVShow t: response.getTVShow()) {
                    if(!tvShowList.contains(t)){
                        TVShow tv = RealmUtils.getInstance().readTVShowFromRealm(t.getId());
                        if(tv!=null) {
                            t.airing = tv.airing;
                            t.latest = tv.latest;
                            t.highestrated = tv.highestrated;
                        }
                        t.popular = true;
                        if(t.getGenres().length>0){
                            t.allGenres = "";
                            for (int i = 0; i < t.getGenres().length; i++) {
                                t.allGenres+=t.getGenres()[i]+",";
                            }
                            t.allGenres = t.allGenres.substring(0, t.allGenres.length()-1);
                        }
                        tvShowList.add(t);
                    }
                }
                RealmUtils.getInstance().addTVShowsToRealm(tvShowList);
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }


}