package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.ImageDetailsActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.R;

import java.util.List;

public class DetailsImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private List<Backdrop> backdropList;
    private ImageView mMovieImage;
    private Movie movie;
    private TvSeries tvSeries;
    private Backdrop backdrop;

    public DetailsImagesViewHolder(View itemView) {
        super(itemView);
        mMovieImage = (ImageView) itemView.findViewById(R.id.movie_image_holder);

        itemView.setOnClickListener(this);
    }

    public void bindImage(Backdrop backdrop , Movie movie, List<Backdrop> backdropList){
        this.backdrop = backdrop;
        this.movie = movie;
        this.backdropList = backdropList;
        if(backdrop.getBackdropSizeW300()!=null)
        Glide.with(mMovieImage.getContext()).load(backdrop.getBackdropSizeW300()).into(mMovieImage);
    }

    public void bindImage(Backdrop backdrop , TvSeries tvSeries, List<Backdrop> backdropList){
        this.backdrop = backdrop;
        this.tvSeries = tvSeries;
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

            if(backdrop.getFilePath()!=null)
                sendMovie.setBackdropPath(backdrop.getFilePath());

            Intent i = new Intent(view.getContext(), ImageDetailsActivity.class);
            i.putExtra("Movie", sendMovie);
            view.getContext().startActivity(i);
        }
        if(tvSeries!=null) {
            TvSeries sendTVSeries = tvSeries;

            sendTVSeries.numOfBackdrops = backdropList.size();
            sendTVSeries.lastLoadedBackdrop = findBackdropInList();

            if(backdrop.getFilePath()!=null)
                sendTVSeries.setBackdropPath(backdrop.getFilePath());

            Intent i = new Intent(view.getContext(), ImageDetailsActivity.class);
            i.putExtra("TVSeries", sendTVSeries);
            view.getContext().startActivity(i);
        }
    }

    public int findBackdropInList(){
        return backdropList.indexOf(backdrop);
    }

}
