package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.DetailsImagesViewHolder;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<DetailsImagesViewHolder> {

    private List<Backdrop> backdrops;
    private Movie movie;
    private TVSeries TVSeries;

    public ImageAdapter(List<Backdrop> backdrops,Movie movie) {
        this.movie = movie;
        this.backdrops = backdrops;
    }

    public ImageAdapter(List<Backdrop> backdrops, TVSeries TVSeries){
        this.backdrops = backdrops;
        this.TVSeries = TVSeries;
    }

    @Override
    public DetailsImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image_item, parent, false);
        return new DetailsImagesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DetailsImagesViewHolder holder, int position) {
        Backdrop backdrop = backdrops.get(position);
        if(movie!=null)
            holder.bindImage(backdrop, movie, backdrops);
        if(TVSeries !=null)
            holder.bindImage(backdrop, TVSeries, backdrops);
    }

    @Override
    public int getItemCount() {
        return backdrops.size();
    }
}
