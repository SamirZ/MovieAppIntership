package com.example.zsamir.movieappintership;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TvSeries;

import java.util.ArrayList;

public class ImageDetailsActivity extends AppCompatActivity {

    Movie mMovie;
    TvSeries mTVSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
            TextView movieName = (TextView) findViewById(R.id.image_details_movie_name);
            if(mMovie.getTitle()!=null && mMovie.getReleaseYear()!=null)
            movieName.setText(mMovie.getTitle()+" ("+mMovie.getReleaseYear()+")");

            TextView movieImageNumber = (TextView) findViewById(R.id.image_details_num_of_images);
            movieImageNumber.setText((mMovie.lastLoadedBackdrop+1)+" of "+mMovie.numOfBackdrops);

            ImageView movieImage = (ImageView) findViewById(R.id.image_details_image);
            if(mMovie.getBackdropUrl()!=null)
            Glide.with(this).load(mMovie.getBackdropSizeOriginalUrl()).into(movieImage);
        }

        if (getIntent().hasExtra("TVSeries")) {
            mTVSeries = getIntent().getParcelableExtra("TVSeries");
            TextView movieName = (TextView) findViewById(R.id.image_details_movie_name);
            if(mTVSeries.getName()!=null && mTVSeries.getFirstAirDate()!=null)
            movieName.setText(mTVSeries.getName()+" ("+mTVSeries.getFirstAirDate()+")");

            TextView movieImageNumber = (TextView) findViewById(R.id.image_details_num_of_images);
            movieImageNumber.setText((mTVSeries.lastLoadedBackdrop+1)+" of "+mTVSeries.numOfBackdrops);

            ImageView movieImage = (ImageView) findViewById(R.id.image_details_image);
            if(mTVSeries.getBackdropUrl()!=null)
            Glide.with(this).load(mTVSeries.getBackdropSizeOriginalUrl()).into(movieImage);
        }

    }

}
