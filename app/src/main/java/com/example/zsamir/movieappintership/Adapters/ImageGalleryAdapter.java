package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.ImageGalleryViewHolder;

import java.util.List;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryViewHolder> {

    private List<Backdrop> backdrops;
    private Movie movie;
    private TvSeries tvSeries;

    public ImageGalleryAdapter(List<Backdrop> backdrops, Movie movie) {
        this.backdrops = backdrops;
        this.movie = movie;
    }
    public ImageGalleryAdapter(List<Backdrop> backdrops, TvSeries tvSeries) {
        this.backdrops = backdrops;
        this.tvSeries = tvSeries;
    }

    @Override
    public ImageGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_gallery_item, parent, false);
        return new ImageGalleryViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ImageGalleryViewHolder holder, int position) {
        Backdrop backdrop = backdrops.get(position);
        if(movie!=null)
            holder.bindImage(backdrop, movie,backdrops);
        if(tvSeries!=null)
            holder.bindImage(backdrop, tvSeries,backdrops);
    }

    @Override
    public int getItemCount() {
        return backdrops.size();
    }

}