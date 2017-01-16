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
import com.example.zsamir.movieappintership.Modules.TvSeries;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<Backdrop> backdrops = new ArrayList<>();
    Movie movie;
    TvSeries tvSeries;
    ApiHandler apiHandler = ApiHandler.getInstance();
    ImageGalleryAdapter imageGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if(getIntent().hasExtra("Movie")){
            movie = getIntent().getParcelableExtra("Movie");
            imageGalleryAdapter = new ImageGalleryAdapter(backdrops,movie);
            TextView name = (TextView)findViewById(R.id.image_gallery_name);
            if(movie.getTitle()!=null && movie.getReleaseYear()!=null)
            name.setText(getString(R.string.images)+" : "+movie.getTitle()+" ("+movie.getReleaseYear()+")");
            loadInitialMovieImages();
        }
        if(getIntent().hasExtra("TVSeries")){
            tvSeries = getIntent().getParcelableExtra("TVSeries");
            imageGalleryAdapter = new ImageGalleryAdapter(backdrops,tvSeries);
            TextView name = (TextView)findViewById(R.id.image_gallery_name);
            if(tvSeries.getName()!=null && tvSeries.getReleaseYear()!=null)
            name.setText(getString(R.string.images)+" : "+tvSeries.getName()+" ("+tvSeries.getReleaseYear()+")");
            loadInitialTVSeriesImages();
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.movie_gallery_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager );
        mRecyclerView.setAdapter(imageGalleryAdapter);

    }

    private void loadInitialTVSeriesImages() {
        apiHandler.requestTVSeriesImages(tvSeries.getId(), new ApiHandler.ImagesListener() {
            @Override
            public void success(Images response) {
                backdrops.addAll(response.backdrops);
                TextView movieImageNumber = (TextView) findViewById(R.id.image_gallery_num_of_images);
                movieImageNumber.setText(backdrops.size()+" "+getString(R.string.images1));
                imageGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadInitialMovieImages() {
        apiHandler.requestMovieImages(movie.getId(), new ApiHandler.ImagesListener() {
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
