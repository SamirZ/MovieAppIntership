package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.UserListAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.List;


public class ListTVSeriesFragment extends Fragment {

    private List<TVShow> tvShowList = new ArrayList<>();
    private UserListAdapter mTvSeriesAdapter = new UserListAdapter(tvShowList);
    private Account user = MovieAppApplication.getUser();

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
        mRecyclerView.setLayoutManager(layoutManager);

        if(((BaseActivity)getActivity()).isNetworkAvailable()){

            RealmUtils.getInstance().removeRealmAccountFavTVShowsData();
            RealmUtils.getInstance().removeRealmAccountWatchTVShowsData();
            RealmUtils.getInstance().removeRealmAccountRatedTVShowsData();

            loadTVSeries(1);

            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if(page>1)
                        loadTVSeries(page);
                }
            };
            mRecyclerView.addOnScrollListener(scrollListener);
        }else{
            loadTVSeriesOffline();
        }

        return rootView;
    }

    private void loadTVSeries(int page) {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            if(page==1){
                if (type.equalsIgnoreCase("FAVORITES"))
                    RealmUtils.getInstance().removeRealmAccountFavTVShowsData();
                else if (type.equalsIgnoreCase("WATCHLIST"))
                    RealmUtils.getInstance().removeRealmAccountWatchTVShowsData();
                else if (type.equalsIgnoreCase("RATINGS"))
                    RealmUtils.getInstance().removeRealmAccountRatedTVShowsData();
            }

            if (type.equalsIgnoreCase("FAVORITES"))
                searchForFavoriteTVSeries(page);
            else if (type.equalsIgnoreCase("WATCHLIST"))
                searchForWatchlistTVSeries(page);
            else if (type.equalsIgnoreCase("RATINGS"))
                searchForRatedTVSeries(page);

        }
    }

    private void loadTVSeriesOffline() {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            if (type.equalsIgnoreCase("FAVORITES"))
                searchForFavoriteTVShowsOffline();
            else if (type.equalsIgnoreCase("WATCHLIST"))
                searchForWatchlistTVShowsOffline();
            else if (type.equalsIgnoreCase("RATINGS"))
                searchForRatedTVShowsOffline();

        }
    }


    private void searchForFavoriteTVSeries(int page) {
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                tvShowList.addAll(response.getTVShow());
                RealmUtils.getInstance().addRealmAccountFavTVShowsData(response.getTVShow());
                mTvSeriesAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForWatchlistTVSeries(final int page) {
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                tvShowList.addAll(response.getTVShow());
                RealmUtils.getInstance().addRealmAccountWatchTVShowsData(response.getTVShow());
                mTvSeriesAdapter.notifyDataSetChanged();

            }
        });
    }

    private void searchForRatedTVSeries(int page){
        ApiHandler.getInstance().requestAccountRatedTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(com.example.zsamir.movieappintership.Modules.TVShowList response) {
                tvShowList.addAll(response.getTVShow());
                RealmUtils.getInstance().addRealmAccountRatedTVShowsData(response.getTVShow());
                mTvSeriesAdapter.notifyDataSetChanged();
            }
        });

    }

    private void searchForRatedTVShowsOffline() {
        tvShowList.addAll(RealmUtils.getInstance().readRealmAccount().getRatedTVShow());
        mTvSeriesAdapter.notifyDataSetChanged();
    }

    private void searchForWatchlistTVShowsOffline() {
        tvShowList.addAll(RealmUtils.getInstance().readRealmAccount().getWatchTVShow());
        mTvSeriesAdapter.notifyDataSetChanged();
    }

    private void searchForFavoriteTVShowsOffline() {
        tvShowList.addAll(RealmUtils.getInstance().readRealmAccount().getFavTVShow());
        mTvSeriesAdapter.notifyDataSetChanged();
    }
}
