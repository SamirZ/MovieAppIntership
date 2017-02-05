package com.example.zsamir.movieappintership.Movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Adapters.ImageAdapter;
import com.example.zsamir.movieappintership.Adapters.ReviewAdapter;
import com.example.zsamir.movieappintership.Common.GalleryActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.Modules.MovieReview;
import com.example.zsamir.movieappintership.Modules.MovieReviews;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie mMovie;
    Credits mCredits;
    Images mMovieImages = new Images();
    MovieReviews mMovieReviews = new MovieReviews();

    Crew director;
    ArrayList<Crew> writers = new ArrayList<>();
    ArrayList<Cast> actors = new ArrayList<>();
    List<MovieReview> reviewList = new ArrayList<>();
    ArrayList<Backdrop> backdrops = new ArrayList<>();
    CastAdapter mCastAdapter = new CastAdapter(actors);
    ReviewAdapter mReviewAdapter = new ReviewAdapter(reviewList);;
    ImageAdapter mImageAdapter;
    // Genre ids sometimes empty take care of that

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Fabric.with(this, new Crashlytics());

        setFinishOnTouchOutside(true);

        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
            mImageAdapter = new ImageAdapter(backdrops,mMovie);
        }


        final TextView movieImagesLabel = (TextView) findViewById(R.id.movie_details_images_label);
        final TextView movieImagesSeeAll = (TextView) findViewById(R.id.see_all);
        final View movieImagesBreakline = findViewById(R.id.movie_details_images_breakline);
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.requestMovieImages(mMovie.getId(), new ApiHandler.ImagesListener() {
            @Override
            public void success(Images response) {
                if(response!=null){
                    mMovieImages = response;
                    if(mMovieImages.backdrops.size()>0){
                        backdrops.addAll(mMovieImages.backdrops);
                        mImageAdapter.notifyDataSetChanged();
                    }else{
                        movieImagesLabel.setVisibility(View.GONE);
                        movieImagesSeeAll.setVisibility(View.GONE);
                        movieImagesBreakline.setVisibility(View.GONE);
                    }
                }
            }
        });


        apiHandler.requestMovieCredits(mMovie.getId(), new ApiHandler.CreditsListener() {
            @Override
            public void success(Credits response) {
                mCredits = response;

                TextView mMovieDirectorLabel = (TextView) findViewById(R.id.movie_details_director_1);
                TextView mMovieDirector = (TextView) findViewById(R.id.movie_details_director_2);
                if(mCredits.crew!=null) {
                    for (Crew c : mCredits.crew) {
                        if (c.department.equals("Directing")) {
                            director = c;
                            mMovieDirector.setText(director.name);
                        }
                        if (c.department.equals("Writing")) {
                            writers.add(c);
                        }
                    }
                    TextView mMovieWritersLabel = (TextView) findViewById(R.id.movie_details_writers_1);
                    TextView mMovieWriters = (TextView) findViewById(R.id.movie_details_writers_2);
                    if (writers.size() > 2)
                        mMovieWriters.setText(writers.get(0).name + ", " + writers.get(1).name + ", " + writers.get(2).name);
                    else {
                        if (!writers.isEmpty())
                            mMovieWriters.setText(writers.get(0).name);
                        else{
                            mMovieWriters.setVisibility(View.GONE);
                            mMovieWritersLabel.setVisibility(View.GONE);

                        }
                    }
                }else{
                    mMovieDirector.setVisibility(View.GONE);
                    mMovieDirectorLabel.setVisibility(View.GONE);
                }
                StringBuilder sb = new StringBuilder();

                TextView movieDetailsCastLabel = (TextView) findViewById(R.id.movie_details_cast_label);
                View movieDetailsCastBreaklinne = findViewById(R.id.movie_details_cast_breakline);


                TextView mMovieStarsLabel = (TextView) findViewById(R.id.movie_details_stars_1);
                TextView mMovieStars = (TextView) findViewById(R.id.movie_details_stars_2);
                if(mCredits.cast!=null){
                    if(mCredits.cast.size()>0){
                        actors.addAll(mCredits.cast);
                        mCastAdapter.notifyDataSetChanged();
                    }else{
                        movieDetailsCastLabel.setVisibility(View.GONE);
                        movieDetailsCastBreaklinne.setVisibility(View.GONE);
                    }
                if(actors.size()>0) {
                    for(int i=0;i<actors.size() && i<3;i++){
                        sb.append(actors.get(i).name);
                        if(i!=2)
                        sb.append(", ");
                    }
                    mMovieStars.setText(sb.toString());
                }

            }else{
                    mMovieStars.setVisibility(View.GONE);
                    mMovieStarsLabel.setVisibility(View.GONE);
                }
            }
        });

        // department writing directing crew
        // cast actors

        apiHandler.requestMovie(mMovie.getId(), new ApiHandler.MovieDetailsListener() {
            @Override
            public void success(MovieDetails response) {
                TextView mMovieReleaseDate = (TextView) findViewById(R.id.movie_details_release_date);
                if(!response.productionCountries.isEmpty())
                    mMovieReleaseDate.setText(response.getReleaseDate()+" "+ "("+response.productionCountries.get(0).name+")");
                else
                    mMovieReleaseDate.setText(response.getReleaseDate());
            }
        });

        final TextView reviews = (TextView) findViewById(R.id.movie_reviews);
        final View breakline = (View) findViewById(R.id.review_breakline);
;
        apiHandler.requestMovieReviews(mMovie.getId(), new ApiHandler.MovieReviewsListener() {
            @Override
            public void success(MovieReviews response) {
                mMovieReviews = response;
                if(response!=null){
                    if(response.getResults().size()>0){
                        reviewList.addAll(response.getResults());
                    }else{
                        reviews.setVisibility(View.GONE);
                        breakline.setVisibility(View.GONE);
                    }
                }else{
                    reviews.setVisibility(View.GONE);
                    breakline.setVisibility(View.GONE);
                }
                mReviewAdapter.notifyDataSetChanged();
            }
        });


        ImageView mMovieImage = (ImageView) findViewById(R.id.movie_details_image);
        if(mMovie.getBackdropUrl()!=null){
            Glide.with(this).load(mMovie.getBackdropUrl()).into(mMovieImage);
            mMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent i = new Intent(MovieDetailsActivity.this, TrailerActivity.class);
                    //i.putExtra("MovieID",String.valueOf(mMovie.getId()));
                    //startActivity(i);
                }
            });
        }

        TextView mMovieName = (TextView) findViewById(R.id.movie_details_name);
        if(mMovie.getTitle()!=null && mMovie.getReleaseYear()!=null)
        mMovieName.setText(mMovie.getTitle());
        if(mMovie.getReleaseYear()!=null){
            mMovieName.append(" ("+mMovie.getReleaseYear()+")");
        }

        TextView mMovieGenre = (TextView) findViewById(R.id.movie_details_genre);
        if(mMovie.getMovieGenres().size()>0)
            mMovieGenre.setText(mMovie.getMovieGenres().get(0));
        else
            mMovieGenre.setText("Not Sorted");

        TextView seeMovieGallery = (TextView) findViewById(R.id.see_all);
        seeMovieGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GalleryActivity.class);
                i.putExtra("Movie",mMovie);
                view.getContext().startActivity(i);
            }
        });

        TextView mMovieOverview = (TextView) findViewById(R.id.movie_details_overview);
        if(mMovie.getOverview()!=null)
        mMovieOverview.setText(mMovie.getOverview());

        TextView mRating = (TextView) findViewById(R.id.movie_details_rating_1);
        TextView mRating2 = (TextView) findViewById(R.id.movie_details_rating_2);
        mRating.setText(String.format(Locale.getDefault(),"%1$.1f",mMovie.getVoteAverage()));
        mRating2.setText(" /10");

        RecyclerView mReviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recyclerView);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(layoutManagerReview);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        RecyclerView mImageRecyclerView = (RecyclerView) findViewById(R.id.images_recyclerView);
        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageRecyclerView.setLayoutManager(layoutManagerImage);
        mImageRecyclerView.setAdapter(mImageAdapter);


        RecyclerView mCastRecyclerView = (RecyclerView) findViewById(R.id.cast_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mCastRecyclerView.setLayoutManager(layoutManager);
        mCastRecyclerView.setAdapter(mCastAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
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
}
