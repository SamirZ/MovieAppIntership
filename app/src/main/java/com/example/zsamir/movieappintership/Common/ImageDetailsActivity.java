package com.example.zsamir.movieappintership.Common;

import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.R;

import java.util.List;

public class ImageDetailsActivity extends BaseActivity {

    private Movie mMovie;
    private TVSeries mTVSeries;
    private ImageView movieImage;
    private TextView movieImageNumber;
    private TextView movieName;
    private List<Backdrop> backdropList;
    private int imageNumber;
    private int imageCount;
    private GestureDetectorCompat gestureObject;
    private ImageDetailsActivity imageDetailsActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        setUpViews();

        setUpMovieImage();

        setUpTVShowImage();

        setUpSwipe();

    }

    private void setUpSwipe() {
        gestureObject = new GestureDetectorCompat(movieImage.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //right
                if(e1.getX() < e2.getX()){
                    imageNumber--;
                    if(imageNumber<=0){
                        imageNumber = 0;
                    }
                    if(imageNumber>=0){
                        movieImageNumber.setText((imageNumber+1)+" of "+imageCount);
                        if(mMovie!=null) {
                            Glide.with(imageDetailsActivity).load(mMovie.backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
                        }
                        if(mTVSeries!=null) {
                            Glide.with(imageDetailsActivity).load(mTVSeries.backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
                        }
                    }
                }

                //left
                if(e1.getX() > e2.getX() ){
                    imageNumber++;
                    if(imageNumber>backdropList.size()){
                        imageNumber = backdropList.size()-1;
                    }
                    if(imageNumber<backdropList.size()){
                        movieImageNumber.setText((imageNumber+1)+" of "+imageCount);
                        if(mMovie!=null)
                            Glide.with(movieImage.getContext()).load(mMovie.backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
                        if(mTVSeries!=null)
                            Glide.with(movieImage.getContext()).load(mTVSeries.backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
                    }
                }
                return true;
            }
        });
    }

    private void setUpTVShowImage() {
        if (getIntent().hasExtra("TVSeries")) {
            mTVSeries = getIntent().getParcelableExtra("TVSeries");
            setTitle(getString(R.string.tv_series_name));
            if(mTVSeries.getName()!=null && mTVSeries.getFirstAirDate()!=null)
                movieName.setText(mTVSeries.getName()+" ("+mTVSeries.getFirstAirDate()+")");

            movieImageNumber.setText((mTVSeries.lastLoadedBackdrop+1)+" of "+mTVSeries.numOfBackdrops);
            imageCount = mTVSeries.numOfBackdrops;
            imageNumber = mTVSeries.lastLoadedBackdrop;
            backdropList = mTVSeries.backdropList;

            if(mTVSeries.getBackdropUrl()!=null)
                Glide.with(this).load(backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
        }
    }

    private void setUpMovieImage() {
        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
            setTitle(getString(R.string.movies_label));

            if(mMovie.getTitle()!=null && mMovie.getReleaseYear()!=null)
                movieName.setText(mMovie.getTitle()+" ("+mMovie.getReleaseYear()+")");

            movieImageNumber.setText((mMovie.lastLoadedBackdrop+1)+" of "+mMovie.numOfBackdrops);
            imageCount = mMovie.numOfBackdrops;
            imageNumber = mMovie.lastLoadedBackdrop;
            backdropList = mMovie.backdropList;

            if(mMovie.getBackdropUrl()!=null){
                Glide.with(this).load(backdropList.get(imageNumber).getBackdropSizeW780()).into(movieImage);
            }
        }
    }

    private void setUpViews() {
        movieName = (TextView) findViewById(R.id.image_details_movie_name);
        movieImageNumber = (TextView) findViewById(R.id.image_details_num_of_images);
        movieImage = (ImageView) findViewById(R.id.image_details_image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureObject.onTouchEvent(event);
    }
}
