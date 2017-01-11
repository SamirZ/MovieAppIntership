package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.MovieImagesViewHolder;
import java.util.List;

/**
 * Created by zsami on 08-Jan-17.
 */
public class ImageAdapter extends RecyclerView.Adapter<MovieImagesViewHolder> {
    List<Backdrop> backdrops;
    Movie movie;

    public ImageAdapter(List<Backdrop> backdrops,Movie movie) {
        this.movie = movie;
        this.backdrops = backdrops;
    }

    @Override
    public MovieImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image_item, parent, false);
        return new MovieImagesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MovieImagesViewHolder holder, int position) {
        Backdrop backdrop = backdrops.get(position);
        holder.bindImage(backdrop, movie, backdrops);
    }

    @Override
    public int getItemCount() {
        return backdrops.size();
    }
}
