package com.example.zsamir.movieappintership.Fragments;

import android.content.SharedPreferences;
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
import com.example.zsamir.movieappintership.Common.EndlessRecyclerViewScrollListener;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ListTVSeriesFragment extends Fragment {

    private List<TVShow> TVShowList = new ArrayList<>();
    private UserListAdapter mTvSeriesAdapter;
    private Account user = MovieAppApplication.getUser();
    private RecyclerView mRecyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;

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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_list_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setAdapter(mTvSeriesAdapter);

        if(TVShowList.size()==0){
            loadTVSeries(1);
        }else{
            TVShowList.clear();
            loadTVSeries(1);
        }
        mRecyclerView.setLayoutManager(layoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                loadTVSeries(page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        return rootView;
    }

    private void loadTVSeries(int page) {
        if(getActivity().getIntent().hasExtra("TYPE")) {
            String type = getActivity().getIntent().getStringExtra("TYPE");

            Log.d("TYPE", type);
            if (type.equalsIgnoreCase("FAVORITES"))
                searchForFavoriteTVSeries(page);
            else if (type.equalsIgnoreCase("WATCHLIST"))
                searchForWatchlistTVSeries(page);
            else if (type.equalsIgnoreCase("RATINGS"))
                searchForRatedTVSeries(page);

        }
    }


    private void searchForFavoriteTVSeries(int page) {
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
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

    private void searchForWatchlistTVSeries(int page) {
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
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

    private void searchForRatedTVSeries(int page){
        ApiHandler.getInstance().requestAccountRatedTVSeries(user.getId(), user.getSessionId(), page, new ApiHandler.TvSeriesListListener() {
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
