package com.example.zsamir.movieappintership.ViewHolders;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.ImageDetailsActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsami on 08-Jan-17.
 */
public class ImageGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private List<Backdrop> backdropList;
    private Backdrop backdrop;
    Movie movie;
    private ImageView mMovieImage;

    public ImageGalleryViewHolder(View itemView) {
        super(itemView);
        mMovieImage = (ImageView) itemView.findViewById(R.id.image_gallery_holder);
        itemView.setOnClickListener(this);
    }

    public void bindImage(Backdrop backdrop, Movie movie, List<Backdrop> backdropList){
        this.backdrop = backdrop;
        this.movie = movie;
        this.backdropList = backdropList;
        Glide.with(mMovieImage.getContext()).load(backdrop.getBackdropSizeW300()).into(mMovieImage);
    }

    @Override
    public void onClick(View view) {
        Movie sendMovie = movie;
        sendMovie.numOfBackdrops = backdropList.size();
        sendMovie.lastLoadedBackdrop = findBackdropInList();
        sendMovie.setBackdropPath(backdrop.getFilePath());
        Intent i = new Intent(view.getContext(), ImageDetailsActivity.class);
        i.putExtra("Movie", sendMovie);
        view.getContext().startActivity(i);
    }

    public int findBackdropInList(){
        return backdropList.indexOf(backdrop);
    }

}
