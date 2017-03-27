package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.ActorMovieViewHolder;
import com.example.zsamir.movieappintership.ViewHolders.CinemaMovieViewHolder;

import java.util.ArrayList;

public class CinemaMovieAdapter extends RecyclerView.Adapter<CinemaMovieViewHolder>{

    private ArrayList<CinemaMovie> mMovies;

    public CinemaMovieAdapter(ArrayList<CinemaMovie> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public CinemaMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_movie_item, parent, false);
        return new CinemaMovieViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CinemaMovieViewHolder holder, int position) {
        CinemaMovie movie = mMovies.get(position);
        holder.bindMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}
