package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.UserListViewHolder;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder>{
    private List<Movie> mMovies;
    private List<TVShow> mTVSeries;

    public UserListAdapter(List<Movie> mMovies, int i) {
        this.mMovies = mMovies;
    }

    public UserListAdapter(List<TVShow> mTVSeries){
        this.mTVSeries = mTVSeries;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserListViewHolder(inflatedView,this);
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        if(mMovies!=null){
            Movie movie = mMovies.get(position);
            holder.bindMovie(movie);
        }else if(mTVSeries !=null){
            TVShow TVShow = mTVSeries.get(position);
            holder.bindTVSeries(TVShow);
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies!=null)
            return mMovies.size();
        else if(mTVSeries !=null)
            return mTVSeries.size();
        return 0;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public List<TVShow> getTVSeries() {
        return mTVSeries;
    }
}