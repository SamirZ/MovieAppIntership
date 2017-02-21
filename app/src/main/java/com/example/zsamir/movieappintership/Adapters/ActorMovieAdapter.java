package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.ActorMovieViewHolder;

import java.util.ArrayList;

public class ActorMovieAdapter extends RecyclerView.Adapter<ActorMovieViewHolder>{
    private ArrayList<Movie> mMovies;
    private Cast mActor;

    public ActorMovieAdapter(ArrayList<Movie> mMovies, Cast mActor) {
        this.mMovies = mMovies;
        this.mActor = mActor;
    }

    @Override
    public ActorMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_movie_item, parent, false);
        return new ActorMovieViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ActorMovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.bindMovie(movie, mActor);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}