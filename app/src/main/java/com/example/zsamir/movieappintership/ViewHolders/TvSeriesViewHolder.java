package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;

import java.util.Locale;

public class TvSeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mTvSeriesImage;
    private TextView mTvSeriesName;
    private TextView mTvSeriesGenre;
    private TextView mTvSeriesReleaseDate;
    private TextView mTvSeriesRating;

    private TVShow TVShow;

    private ImageView mTVSeriesImageFav;
    private ImageView mTVSeriesImageWatch;

    public TvSeriesViewHolder(View itemView) {
        super(itemView);

        mTvSeriesImage = (ImageView) itemView.findViewById(R.id.tv_series_image);
        mTvSeriesGenre = (TextView) itemView.findViewById(R.id.tv_series_genre);
        mTvSeriesReleaseDate = (TextView) itemView.findViewById(R.id.tv_series_release_date);
        mTvSeriesName = (TextView) itemView.findViewById(R.id.tv_series_name);
        mTvSeriesRating = (TextView) itemView.findViewById(R.id.tv_series_rating);
        mTVSeriesImageFav = (ImageView) itemView.findViewById(R.id.tv_series_favorites_image);
        mTVSeriesImageWatch = (ImageView) itemView.findViewById(R.id.tv_series_bookmark_image);
        mTvSeriesImage.setOnClickListener(this);
    }

    public void bindTvSeries(TVShow TVShow) {
        this.TVShow = TVShow;

        if(TVShow.getName()!=null)
        mTvSeriesName.setText(TVShow.getName());

        if(TVShow.getTvSeriesGenres().size()>0){
            mTvSeriesGenre.setText(TVShow.getTvSeriesGenres().get(0));
        }
        else
            mTvSeriesGenre.setText(" ");


        // no fin date!
        if(TVShow.getReleaseYear()!=null)
            mTvSeriesReleaseDate.setText("(TV Series"+" "+ TVShow.getReleaseYear()+""+")");
        if(String.format(Locale.getDefault(),"%1$.1f", TVShow.getVoteAverage())!=null)
            mTvSeriesRating.setText(String.format(Locale.getDefault(),"%1$.1f", TVShow.getVoteAverage()));

        if(MovieAppApplication.isUserLoggedIn()){


            mTVSeriesImageFav.setVisibility(View.VISIBLE);
            mTVSeriesImageWatch.setVisibility(View.VISIBLE);

            if(MovieAppApplication.getUser().getFavTVSeriesList()!=null){
                if(MovieAppApplication.getUser().getFavTVSeriesList().contains(TVShow.getId())){
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageFav.getContext(),R.drawable.like_filled_2));
                    mTVSeriesImageFav.setBackground(likeIcon);

                } else{
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageFav.getContext(),R.drawable.like_2));
                    mTVSeriesImageFav.setBackground(likeIcon);
                }
            }

            if(MovieAppApplication.getUser().getWatchlistTVSeriesList()!=null){
                if(MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(TVShow.getId())){
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageWatch.getContext(),R.drawable.bookmark_filled_2));
                    mTVSeriesImageWatch.setBackground(bookmarkIcon);
                }else{
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageWatch.getContext(),R.drawable.bookmark));
                    mTVSeriesImageWatch.setBackground(bookmarkIcon);
                }
            }
        }else{
            mTVSeriesImageFav.setVisibility(View.GONE);
            mTVSeriesImageWatch.setVisibility(View.GONE);
        }

        Glide.with(mTvSeriesImage.getContext()).load(TVShow.getPosterUrl()).into(mTvSeriesImage);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), TVSeries.getName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(view.getContext(), TVSeriesDetailsActivity.class);
        i.putExtra("TVSeries", TVShow);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(i);
    }
}
