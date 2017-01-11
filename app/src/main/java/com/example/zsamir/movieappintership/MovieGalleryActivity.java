package com.example.zsamir.movieappintership;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.ImageGalleryAdapter;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.Movie;

import java.util.ArrayList;

public class MovieGalleryActivity extends AppCompatActivity {

    ArrayList<Backdrop> backdrops = new ArrayList<>();
    Movie movie;
    int mMovieId;
    ApiHandler apiHandler = ApiHandler.getInstance();
    ImageGalleryAdapter imageGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_gallery);

        if(getIntent().hasExtra("Movie")){
            movie = getIntent().getParcelableExtra("Movie");
            imageGalleryAdapter = new ImageGalleryAdapter(backdrops,movie);
            TextView movieName = (TextView)findViewById(R.id.image_gallery_movie_name);
            movieName.setText(getString(R.string.images)+" : "+movie.getTitle()+" ("+movie.getReleaseYear()+")");

        }
        if (getIntent().hasExtra("MovieId")) {
            mMovieId = Integer.parseInt(getIntent().getStringExtra("MovieId"));

            loadInitialImages();
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.movie_gallery_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(imageGalleryAdapter);

    }

    private void loadInitialImages() {
        apiHandler.requestMovieImages(mMovieId, new ApiHandler.MovieImagesListener() {
            @Override
            public void success(Images response) {
                backdrops.addAll(response.backdrops);
                TextView movieImageNumber = (TextView) findViewById(R.id.image_gallery_num_of_images);
                movieImageNumber.setText(backdrops.size()+" "+getString(R.string.images1));
                imageGalleryAdapter.notifyDataSetChanged();
            }
        });
    }


}
