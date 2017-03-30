package com.example.zsamir.movieappintership.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
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

    private Fragment fragment;
    private ArrayList<CinemaMovie> mMovies;

    public CinemaMovieAdapter(Fragment fragment, ArrayList<CinemaMovie> mMovies) {
        this.mMovies = mMovies;
        this.fragment = fragment;
    }

    @Override
    public CinemaMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_movie_item, parent, false);
        return new CinemaMovieViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CinemaMovieViewHolder holder, int position) {
        CinemaMovie movie = mMovies.get(position);
        holder.bindMovie(fragment,movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}
