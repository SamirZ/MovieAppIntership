package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.UserListAdapter;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;


public class ListTVSeriesFragment extends Fragment {

    private List<TVShow> TVShowList = new ArrayList<>();
    private UserListAdapter mTvSeriesAdapter;
    private Account user = MovieAppApplication.getUser();

    public ListTVSeriesFragment() {
        mTvSeriesAdapter = new UserListAdapter(TVShowList);
    }

    public static ListTVSeriesFragment newInstance() {

        return new ListTVSeriesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_list_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(TVShowList.size()==0){
            loadTVSeries();
        }else{
            TVShowList.clear();
            loadTVSeries();
            mTvSeriesAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    private void loadTVSeries() {
        String type = getActivity().getIntent().getStringExtra("TYPE");

        Log.d("TYPE",type);
        if(type.equalsIgnoreCase("FAVORITES"))
            searchForFavoriteTVSeries();

        if(type.equalsIgnoreCase("WATCHLIST"))
            searchForWatchlistTVSeries();

        if(type.equalsIgnoreCase("RATINGS"))
            searchForRatedTVSeries();
    }

    private void searchForFavoriteTVSeries() {
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                for (TVShow t: response.getTVShow()) {
                    TVShowList.add(t);
                }
                //TVSeriesList.addAll(response.getTVSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteTVSeries(user.getId(), user.getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                                for (TVShow t: response.getTVShow()) {
                                    TVShowList.add(t);
                                }
                                //TVSeriesList.addAll(response.getTVSeries());
                                mTvSeriesAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void searchForWatchlistTVSeries() {
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                for (TVShow t: response.getTVShow()) {
                    TVShowList.add(t);
                }
                //TVSeriesList.addAll(response.getTVSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                                for (TVShow t: response.getTVShow()) {
                                    TVShowList.add(t);
                                }
                                //TVSeriesList.addAll(response.getTVSeries());
                                mTvSeriesAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void searchForRatedTVSeries(){
        ApiHandler.getInstance().requestAccountRatedTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                for (TVShow t: response.getTVShow()) {
                    TVShowList.add(t);
                }
                //TVSeriesList.addAll(response.getTVSeries());
                mTvSeriesAdapter.notifyDataSetChanged();
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountRatedTVSeries(user.getId(), user.getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                                for (TVShow t: response.getTVShow()) {
                                    TVShowList.add(t);
                                }
                                //TVSeriesList.addAll(response.getTVSeries());
                                mTvSeriesAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

    }
}
