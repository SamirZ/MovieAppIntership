package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Modules.Genre;
import com.example.zsamir.movieappintership.Modules.TVSeriesGenres;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeriesDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TvSeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mTvSeriesImage;
    private TextView mTvSeriesName;
    private TextView mTvSeriesGenre;
    private TextView mTvSeriesReleaseDate;
    private TextView mTvSeriesRating;
    private TvSeries tvSeries;

    public TvSeriesViewHolder(View itemView) {
        super(itemView);

        mTvSeriesImage = (ImageView) itemView.findViewById(R.id.tv_series_image);
        mTvSeriesGenre = (TextView) itemView.findViewById(R.id.tv_series_genre);
        mTvSeriesReleaseDate = (TextView) itemView.findViewById(R.id.tv_series_release_date);
        mTvSeriesName = (TextView) itemView.findViewById(R.id.tv_series_name);
        mTvSeriesRating = (TextView) itemView.findViewById(R.id.tv_series_rating);
        mTvSeriesImage.setOnClickListener(this);
    }

    public void bindTvSeries(TvSeries tvSeries) {
        this.tvSeries = tvSeries;

        if(tvSeries.getName()!=null)
        mTvSeriesName.setText(tvSeries.getName());

        List<String> g = tvSeries.getTvSeriesGenres();

        if(tvSeries.getTvSeriesGenres().size()>0){
            mTvSeriesGenre.setText(tvSeries.getTvSeriesGenres().get(0));
        }
        else
            mTvSeriesGenre.setText(" ");


        // no fin date!
        if(tvSeries.getReleaseYear()!=null)
            mTvSeriesReleaseDate.setText("(TV Series"+" "+tvSeries.getReleaseYear()+""+")");
        if(String.format(Locale.getDefault(),"%1$.1f",tvSeries.getVoteAverage())!=null)
            mTvSeriesRating.setText(String.format(Locale.getDefault(),"%1$.1f",tvSeries.getVoteAverage()));
        if(tvSeries.getPosterPath()!=null)
            Glide.with(mTvSeriesImage.getContext()).load(tvSeries.getPosterUrl()).into(mTvSeriesImage);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), tvSeries.getName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(view.getContext(), TVSeriesDetailsActivity.class);
        i.putExtra("TVSeries", tvSeries);
        view.getContext().startActivity(i);
    }
}
