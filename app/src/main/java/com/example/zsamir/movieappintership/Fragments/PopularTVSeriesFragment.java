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
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(TVShowList.size()==0){
                loadPopularTvSeries(1);
            }else{
                TVShowList.clear();
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
            TvSeriesAdapter mmTvSeriesAdapter = new TvSeriesAdapter(RealmUtils.getInstance().readPopularTVShowsFromRealm());
            mRecyclerView.setAdapter(mmTvSeriesAdapter);
            mmTvSeriesAdapter.notifyDataSetChanged();
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
                    if(!TVShowList.contains(t)){
                        t.type = "POPULAR";
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
                if(page==1){
                    RealmUtils.getInstance().deleteAllPopularTVShows();
                }
                RealmUtils.getInstance().addTVShowsToRealm(TVShowList);
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }


}