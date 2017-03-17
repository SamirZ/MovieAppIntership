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

public class LatestTVSeriesFragment extends Fragment {

    private static LatestTVSeriesFragment instance = null;
    private ArrayList<TVShow> TVShowList = new ArrayList<>();
    private ApiHandler movieDbApi = ApiHandler.getInstance();
    private TvSeriesAdapter mTvSeriesAdapter = new TvSeriesAdapter(TVShowList);
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
    public void onStart() {
        super.onStart();
        mTvSeriesAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_latest_tvseries, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.latest_tv_series_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(((BaseActivity)getActivity()).isNetworkAvailable()){
            if(TVShowList.size()==0)
                loadLatestTvSeries(1);
            else{
                TVShowList.clear();
                loadLatestTvSeries(1);
                mTvSeriesAdapter.notifyDataSetChanged();
            }

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager ) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //if(page+1<=numberOfPages)
                    loadLatestTvSeries(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }
        else{
            TvSeriesAdapter mmTvSeriesAdapter = new TvSeriesAdapter(RealmUtils.getInstance().readLatestTVShowsFromRealm());
            mRecyclerView.setAdapter(mmTvSeriesAdapter);
            mmTvSeriesAdapter.notifyDataSetChanged();
        }
        return rootView;
    }

    private void loadLatestTvSeries(final int page) {
        movieDbApi.requestLatestTvSeries(page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                //addition
                for (TVShow t: response.getTVShow()) {
                    if(!TVShowList.contains(t)){
                        t.type = "LATEST";
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
                    RealmUtils.getInstance().deleteAllLatestTVShows();
                }
                RealmUtils.getInstance().addTVShowsToRealm(TVShowList);
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });
    }


}
