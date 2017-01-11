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

import java.util.ArrayList;

public class ImageDetailsActivity extends AppCompatActivity {

    Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
        }

        TextView movieName = (TextView) findViewById(R.id.image_details_movie_name);
        movieName.setText(mMovie.getTitle()+" ("+mMovie.getReleaseYear()+")");

        TextView movieImageNumber = (TextView) findViewById(R.id.image_details_num_of_images);
        movieImageNumber.setText((mMovie.lastLoadedBackdrop+1)+" of "+mMovie.numOfBackdrops);

        ImageView movieImage = (ImageView) findViewById(R.id.image_details_image);
        Glide.with(this).load(mMovie.getBackdropSizeOriginalUrl()).into(movieImage);
    }

}
