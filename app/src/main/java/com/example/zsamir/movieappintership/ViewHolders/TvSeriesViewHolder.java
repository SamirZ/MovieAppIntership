package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.R;

import java.util.List;

/**
 * Created by zsami on 09-Jan-17.
 */

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
        int[] genreIds = tvSeries.getGenreIds();
        mTvSeriesName.setText(tvSeries.getName());
        List<String> s = tvSeries.getTvSeriesGenres();
        // Genre ids sometimes empty
        if(s.size()>0)
            mTvSeriesGenre.setText(s.get(0));
        else
            mTvSeriesGenre.setText("Not Sorted"); ///!!!!!!
        mTvSeriesReleaseDate.setText("(TV Series"+" "+tvSeries.getFirstAirDate()+"-)");
        mTvSeriesRating.setText(Float.toString(tvSeries.getVoteAverage()));
        Glide.with(mTvSeriesImage.getContext()).load(tvSeries.getPosterUrl()).into(mTvSeriesImage);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), tvSeries.getName(), Toast.LENGTH_SHORT).show();

    }
}
