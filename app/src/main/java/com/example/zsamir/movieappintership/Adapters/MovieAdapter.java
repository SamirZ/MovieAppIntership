package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.MovieViewHolder;

import java.util.ArrayList;

/**
 * Created by zsami on 31-Dec-16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{
    ArrayList<Movie> mMovies;

    public MovieAdapter(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bindMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void addItem(Movie movie) {
        mMovies.add(0, movie);
        notifyItemInserted(0);
    }

}