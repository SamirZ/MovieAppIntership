package com.example.zsamir.movieappintership.ViewHolders;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.ImageDetailsActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.R;

import java.util.List;

public class ImageGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private List<Backdrop> backdropList;
    private Backdrop backdrop;
    private Movie movie;
    private TVShow TVShow;
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
        if(backdrop.getBackdropSizeW300()!=null)
            Glide.with(mMovieImage.getContext()).load(backdrop.getBackdropSizeW300()).into(mMovieImage);
    }

    public void bindImage(Backdrop backdrop , TVShow TVShow, List<Backdrop> backdropList){
        this.backdrop = backdrop;
        this.TVShow = TVShow;
        this.backdropList = backdropList;
        if(backdrop.getBackdropSizeW300()!=null)
            Glide.with(mMovieImage.getContext()).load(backdrop.getBackdropSizeW300()).into(mMovieImage);
    }

    @Override
    public void onClick(View view) {
        if(movie!=null) {
            Movie sendMovie = movie;

            sendMovie.numOfBackdrops = backdropList.size();
            sendMovie.lastLoadedBackdrop = findBackdropInList();
            sendMovie.backdropList = backdropList;

            if(backdrop.getFilePath()!=null)
                sendMovie.setBackdropPath(backdrop.getFilePath());

            Intent i = new Intent(view.getContext(), ImageDetailsActivity.class);
            i.putExtra("Movie", sendMovie);

            view.getContext().startActivity(i);
        }
        if(TVShow !=null) {
            TVShow sendTVShow = TVShow;

            sendTVShow.numOfBackdrops = backdropList.size();
            sendTVShow.lastLoadedBackdrop = findBackdropInList();
            sendTVShow.backdropList = backdropList;

            if(backdrop.getFilePath()!=null)
                sendTVShow.setBackdropPath(backdrop.getFilePath());

            Intent j = new Intent(view.getContext(), ImageDetailsActivity.class);
            j.putExtra("TVSeries", sendTVShow);

            view.getContext().startActivity(j);
        }
    }

    private int findBackdropInList(){
        return backdropList.indexOf(backdrop);
    }

}
