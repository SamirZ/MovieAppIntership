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

public class HighestRatedTVSeriesFragment extends Fragment {

    private static HighestRatedTVSeriesFragment instance = null;
    ArrayList<TVShow> TVShowList = new ArrayList<>();
    ApiHandler movieDbApi = ApiHandler.getInstance();
    TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(TVShowList);
    int numberOfPages;

    public HighestRatedTVSeriesFragment() {
    }

    public static HighestRatedTVSeriesFragment getInstance() {
        if(instance == null) {
            instance = new HighestRatedTVSeriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_highest_rated_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.highest_rated_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);

        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        mTvSeriesAdapter.notifyDataSetChanged();

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(TVShowList.size()==0)
                loadTopRatedTvSeries(1);
            else{
                TVShowList.clear();
                loadTopRatedTvSeries(1);
                mTvSeriesAdapter.notifyDataSetChanged();
            }

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //if(page+1<=numberOfPages)
                    loadTopRatedTvSeries(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            TvSeriesAdapter mmTvSeriesAdapter = new TvSeriesAdapter(RealmUtils.getInstance().readHighestRatedTVShowsFromRealm());
            mRecyclerView.setAdapter(mmTvSeriesAdapter);
            mmTvSeriesAdapter.notifyDataSetChanged();
        }

        return rootView;
    }


    private void loadTopRatedTvSeries(final int page) {
        movieDbApi.requestHighestRatedTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                if(response!=null){
                    numberOfPages = response.getTotalPages();
                    //addition
                    for (TVShow t: response.getTVShow()) {
                        if(!TVShowList.contains(t)){
                            TVShow tv = RealmUtils.getInstance().readTVShowFromRealm(t.getId());
                            if(tv!=null) {
                                t.popular = tv.popular;
                                t.latest = tv.latest;
                                t.airing = tv.airing;
                            }
                            t.highestrated = true;
                            if(t.getGenres().length>0){
                                t.allGenres = "";
                                for (int i = 0; i < t.getGenres().length; i++) {
                                    t.allGenres+=t.getGenres()[i]+",";
                                }
                                t.allGenres = t.allGenres.substring(0, t.allGenres.length()-1);
                            }
                            TVShowList.add(t);
                        }
                    }
                    RealmUtils.getInstance().addTVShowsToRealm(TVShowList);
                    mTvSeriesAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}