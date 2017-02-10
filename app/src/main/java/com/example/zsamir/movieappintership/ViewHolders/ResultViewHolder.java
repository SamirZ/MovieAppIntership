package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;

import java.util.Locale;

/**
 * Created by zsami on 26-Jan-17.
 */
public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView date;
    private TextView rating;
    private ImageView image;
    private Result media;

    public ResultViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.result_name);
        date = (TextView) itemView.findViewById(R.id.result_date);
        rating = (TextView) itemView.findViewById(R.id.result_rating);
        image = (ImageView) itemView.findViewById(R.id.result_image);

        itemView.setOnClickListener(this);

    }

    public void bindResult(Result result) {

        media = result;
        if(result.mediaType.equalsIgnoreCase("movie")){
            name.setText(result.toMovie().getTitle());
            date.setText("("+result.toMovie().getReleaseYear()+")");
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",result.toMovie().getVoteAverage())+" /10");
            if(result.toMovie().getPosterPath()==null){
                Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
            }else{
                Glide.with(image.getContext()).load(result.toMovie().getPosterUrl()).into(image);
            }
        }
        if(result.mediaType.equalsIgnoreCase("tv")){
            name.setText(result.toTvSeries().getName());
            date.setText("("+itemView.getContext().getString(R.string.tv_series_name)+" "+result.toTvSeries().getReleaseYear()+")");
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",result.toTvSeries().getVoteAverage())+" /10");
            if(result.toTvSeries().getPosterPath()==null){
                Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
            }else{
                Glide.with(image.getContext()).load(result.toTvSeries().getPosterUrl()).into(image);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(media.mediaType.equalsIgnoreCase("movie")){
            Intent i = new Intent(v.getContext(), MovieDetailsActivity.class);
            i.putExtra("Movie", media.toMovie());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(i);
        }
        if(media.mediaType.equalsIgnoreCase("tv")){
            Intent i = new Intent(v.getContext(), TVSeriesDetailsActivity.class);
            i.putExtra("TVSeries", media.toTvSeries());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(i);
        }

    }
}
