package com.example.zsamir.movieappintership.Movies;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Adapters.ImageAdapter;
import com.example.zsamir.movieappintership.Adapters.ReviewAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.GalleryActivity;
import com.example.zsamir.movieappintership.Common.TrailerActivity;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Favorite;
import com.example.zsamir.movieappintership.LoginModules.PostResponse;
import com.example.zsamir.movieappintership.LoginModules.Watchlist;
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
import com.example.zsamir.movieappintership.Common.RatingActivity;
import com.example.zsamir.movieappintership.RealmUtils.RealmMovieDetails;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieDetailsActivity extends BaseActivity {

    private Movie mMovie;
    private Credits mCredits;
    private Images mMovieImages = new Images();
    private boolean liked = false;
    private boolean watchlist = false;

    private Crew director;
    private ArrayList<Crew> writers = new ArrayList<>();
    private ArrayList<Cast> actors = new ArrayList<>();
    private List<MovieReview> reviewList = new ArrayList<>();
    private ArrayList<Backdrop> backdrops = new ArrayList<>();
    private CastAdapter mCastAdapter = new CastAdapter(actors);
    private ReviewAdapter mReviewAdapter = new ReviewAdapter(reviewList);
    private ImageAdapter mImageAdapter;

    private TextView mMovieOverview;
    private TextView movieImagesLabel;
    private TextView movieImagesSeeAll;
    private TextView mMovieDirectorLabel;
    private TextView mMovieDirector;
    private TextView mMovieWritersLabel;
    private TextView mMovieWriters;
    private TextView mRating;
    private TextView mRating2;
    private TextView rateText;
    private TextView movieDetailsCastLabel;
    private TextView mMovieStarsLabel;
    private TextView mMovieStars;
    private TextView mMovieName;
    private TextView mMovieGenre;

    private RecyclerView mReviewRecyclerView;
    private RecyclerView mImageRecyclerView;
    private RecyclerView mCastRecyclerView;

    private ImageView mMovieImage;
    private ImageView rateImage;
    private ImageView playVideo;

    private View movieDetailsCastBreaklinne;
    private View movieImagesBreakline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setFinishOnTouchOutside(true);

        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
            mImageAdapter = new ImageAdapter(backdrops,mMovie);
        }

        setUpViews();

        setKnowData();

        setDetailedData();

        setReviews();

        setImages();

        setCast();

    }

    private void setCast() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mCastRecyclerView.setLayoutManager(layoutManager);
        mCastRecyclerView.setAdapter(mCastAdapter);
    }

    private void setImages() {
        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageRecyclerView.setLayoutManager(layoutManagerImage);
        mImageRecyclerView.setAdapter(mImageAdapter);
    }

    private void setReviews() {
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(layoutManagerReview);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
    }

    private void setUpViews() {
        mMovieOverview = (TextView) findViewById(R.id.movie_details_overview);
        mMovieImage = (ImageView) findViewById(R.id.movie_details_image);
        movieImagesLabel = (TextView) findViewById(R.id.movie_details_images_label);
        movieImagesSeeAll = (TextView) findViewById(R.id.see_all);
        movieImagesBreakline = findViewById(R.id.movie_details_images_breakline);
        mMovieDirectorLabel = (TextView) findViewById(R.id.movie_details_director_1);
        mMovieDirector = (TextView) findViewById(R.id.movie_details_director_2);
        mMovieWritersLabel = (TextView) findViewById(R.id.movie_details_writers_1);
        mMovieWriters = (TextView) findViewById(R.id.movie_details_writers_2);
        mRating = (TextView) findViewById(R.id.movie_details_rating_1);
        mRating2 = (TextView) findViewById(R.id.movie_details_rating_2);
        rateImage = (ImageView) findViewById(R.id.movie_details_star_image);
        rateText = (TextView) findViewById(R.id.movie_details_rating_3);
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recyclerView);
        mImageRecyclerView = (RecyclerView) findViewById(R.id.images_recyclerView);
        mCastRecyclerView = (RecyclerView) findViewById(R.id.cast_recyclerView);
        movieDetailsCastLabel = (TextView) findViewById(R.id.movie_details_cast_label);
        movieDetailsCastBreaklinne = findViewById(R.id.movie_details_cast_breakline);
        mMovieStarsLabel = (TextView) findViewById(R.id.movie_details_stars_1);
        mMovieStars = (TextView) findViewById(R.id.movie_details_stars_2);
        mMovieName = (TextView) findViewById(R.id.movie_details_name);
        mMovieGenre = (TextView) findViewById(R.id.movie_details_genre);
        playVideo = (ImageView) findViewById(R.id.movie_play_video);
    }

    private void setDetailedData() {

        if(isNetworkAvailable()){
            //
            RealmUtils.getInstance().createRealmMovieDetails(mMovie.getId());

            ApiHandler.getInstance().requestMovie(mMovie.getId(), new ApiHandler.MovieDetailsListener() {
                @Override
                public void success(MovieDetails response) {
                    TextView mMovieReleaseDate = (TextView) findViewById(R.id.movie_details_release_date);
                    if(!response.getProductionCountries().isEmpty()) {

                        // NOT WORKING ?
                        RealmUtils.getInstance().addRealmMovieDetailsProductionCountry(mMovie.getId(),response.getProductionCountries().get(0));


                        mMovieReleaseDate.setText(mMovie.getReleaseDate() + " " + "(" + response.getProductionCountries().get(0).getName() + ")");
                    }
                    else mMovieReleaseDate.setText(mMovie.getReleaseDate());
                }
            });

            ApiHandler.getInstance().requestMovieImages(mMovie.getId(), new ApiHandler.ImagesListener() {
                @Override
                public void success(Images response) {
                    if(response!=null){
                        mMovieImages = response;
                        if(mMovieImages.getBackdrops().size()>0){
                            backdrops.addAll(mMovieImages.getBackdrops());

                            // NOT WORKING ?
                            RealmUtils.getInstance().addRealmMovieDetailsBackrops(mMovie.getId(),backdrops);

                            mImageAdapter.notifyDataSetChanged();
                        }else{
                            movieImagesLabel.setVisibility(View.GONE);
                            movieImagesSeeAll.setVisibility(View.GONE);
                            movieImagesBreakline.setVisibility(View.GONE);
                        }
                    }
                }
            });


            ApiHandler.getInstance().requestMovieCredits(mMovie.getId(), new ApiHandler.CreditsListener() {
                @Override
                public void success(Credits response) {
                    mCredits = response;

                    if(mCredits.crew!=null) {
                        if(mCredits.crew.size()<1){
                            mMovieDirector.setVisibility(View.GONE);
                            mMovieDirectorLabel.setVisibility(View.GONE);
                            mMovieWriters.setVisibility(View.GONE);
                            mMovieWritersLabel.setVisibility(View.GONE);
                        }else{
                            for (Crew c : mCredits.crew) {
                                if (c.getDepartment().equals("Directing")) {
                                    director = c;
                                    // NOT WORKING
                                    RealmUtils.getInstance().addRealmMovieDetailsDirector(mMovie.getId(),director);
                                    mMovieDirector.setText(director.getName());
                                    break;
                                }
                                if (c.getDepartment().equals("Writing")) {
                                    writers.add(c);
                                }
                            }
                            if (writers.size() > 2) {
                                mMovieWriters.setText(writers.get(0).getName() + ", " + writers.get(1).getName() + ", " + writers.get(2).getName());
                            }else {
                                if (!writers.isEmpty()){
                                    mMovieWriters.setText(writers.get(0).getName());
                                }
                                else{
                                    mMovieWriters.setVisibility(View.GONE);
                                    mMovieWritersLabel.setVisibility(View.GONE);

                                }
                            }

                            // NOT WORKING ?
                            RealmUtils.getInstance().addRealmMovieDetailsWriters(mMovie.getId(),writers);
                        }
                    }else{
                        mMovieDirector.setVisibility(View.GONE);
                        mMovieDirectorLabel.setVisibility(View.GONE);
                    }
                    StringBuilder sb = new StringBuilder();


                    if(mCredits.cast!=null){
                        if(mCredits.cast.size()>0){
                            actors.addAll(mCredits.cast);

                            // NOT WORKING ?
                            RealmUtils.getInstance().addRealmMovieDetailsActors(mMovie.getId(),actors);

                            mCastAdapter.notifyDataSetChanged();
                        }else{
                            movieDetailsCastLabel.setVisibility(View.GONE);
                            movieDetailsCastBreaklinne.setVisibility(View.GONE);
                            mMovieStars.setVisibility(View.GONE);
                            mMovieStarsLabel.setVisibility(View.GONE);
                        }
                        if(actors.size()>0) {
                            for(int i=0;i<actors.size() && i<3;i++){
                                sb.append(actors.get(i).getName());
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

            final TextView reviews = (TextView) findViewById(R.id.movie_reviews);
            final View breakline = findViewById(R.id.review_breakline);
            ApiHandler.getInstance().requestMovieReviews(mMovie.getId(), new ApiHandler.MovieReviewsListener() {
                @Override
                public void success(MovieReviews response) {

                    if(response!=null){
                        if(response.getResults().size()>0){
                            reviewList.addAll(response.getResults());

                            // NOT WORKING ?
                            RealmUtils.getInstance().addRealmMovieDetailsReviews(mMovie.getId(),reviewList);

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


        }else{
            //Offline mode
            if(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId())!=null){

                TextView mMovieReleaseDate = (TextView) findViewById(R.id.movie_details_release_date);
                if(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getProductionCountry()!=null)
                    mMovieReleaseDate.setText(mMovie.getReleaseDate() + " " + "(" + RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getProductionCountry().getName() + ")");

                mMovieDirector.setText(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getDirector().getName());


                if (RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().size() > 2)
                    mMovieWriters.setText(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().get(0).getName() + ", " + RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().get(1).getName() + ", " + RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().get(2).getName());
                else {
                    if (!RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().isEmpty()){
                        mMovieWriters.setText(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getWriters().get(0).getName());
                    }
                    else{
                        mMovieWriters.setVisibility(View.GONE);
                        mMovieWritersLabel.setVisibility(View.GONE);
                    }
                }

                reviewList.addAll(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getMovieReviews());
                mReviewAdapter.notifyDataSetChanged();

                actors.addAll(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getActors());
                mCastAdapter.notifyDataSetChanged();

                backdrops.addAll(RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId()).getBackdrops());
                mImageAdapter.notifyDataSetChanged();


                // Actors label
                StringBuilder sb = new StringBuilder();
                if(actors.size()>0) {
                    for(int i=0;i<actors.size() && i<3;i++){
                        sb.append(actors.get(i).getName());
                        if(i!=2)
                            sb.append(", ");
                    }
                    mMovieStars.setText(sb.toString());
                }
            }
        }

    }

    private void setKnowData() {

        mRating.setText(String.format(Locale.getDefault(),"%1$.1f",mMovie.getVoteAverage()));
        mRating2.setText(getString(R.string.max_rating));

        movieImagesSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GalleryActivity.class);
                i.putExtra("Movie",mMovie);
                view.getContext().startActivity(i);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MovieAppApplication.isUserLoggedIn()){
                    Intent i = new Intent(MovieDetailsActivity.this, RatingActivity.class);
                    i.putExtra("MOVIE",mMovie);
                    startActivityForResult(i,1);
                }else{
                    showLoginDialog();
                }
            }
        };

        rateImage.setOnClickListener(onClickListener);
        rateText.setOnClickListener(onClickListener);


        if(mMovie.getOverview()!=null)
            mMovieOverview.setText(mMovie.getOverview());


        // load image from memory
        if(mMovie.getBackdropUrl()!=null){
            Glide.with(this).load(mMovie.getBackdropUrl()).into(mMovieImage);
            playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MovieDetailsActivity.this, TrailerActivity.class);
                    i.putExtra("MovieID",mMovie);
                    startActivity(i);
                }
            });
        }


        if(mMovie.getTitle()!=null && mMovie.getReleaseYear()!=null)
            mMovieName.setText(mMovie.getTitle());
        if(mMovie.getReleaseYear()!=null){
            mMovieName.append(" ("+mMovie.getReleaseYear()+")");
        }

        if(mMovie.getMovieGenres().size()>0)
            mMovieGenre.setText(mMovie.getMovieGenres().get(0));
        else
            mMovieGenre.setText(getString(R.string.not_sorted));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);

        if(MovieAppApplication.isUserLoggedIn()){
            if(MovieAppApplication.getUser().getFavMovieList()!=null)
                if(MovieAppApplication.getUser().getFavMovieList().contains(mMovie.getId())){
                    MenuItem item = menu.findItem(R.id.action_like_movie);
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.like_filled_menu));
                    liked = true;
                }
            if(MovieAppApplication.getUser().getWatchlistMovieList()!=null)
                if(MovieAppApplication.getUser().getWatchlistMovieList().contains(mMovie.getId())){
                    MenuItem item = menu.findItem(R.id.action_watchlist_movie);
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_filled_menu));
                    watchlist = true;
                }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_like_movie:
                if(!liked && MovieAppApplication.isUserLoggedIn()){
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.like_filled_menu));
                    if(!MovieAppApplication.getUser().getFavMovieList().contains(mMovie.getId())){
                        MovieAppApplication.getUser().addToFavoriteMoviesList(mMovie.getId());

                        ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                MovieAppApplication.getUser().getSessionId(),
                                new Favorite("movie",mMovie.getId(),true),
                                new ApiHandler.PostResponseListener() {
                                    @Override
                                    public void success(PostResponse response) {
                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                        Log.d("RESPONSE", response.statusMessage);
                                    }
                                });
                    }
                    liked = true;
                }else if(liked && MovieAppApplication.isUserLoggedIn()){
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.like_menu));
                    if(MovieAppApplication.getUser().getFavMovieList().contains(mMovie.getId())){
                        MovieAppApplication.getUser().removeFromFavoriteMovieList(mMovie.getId());

                        ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                MovieAppApplication.getUser().getSessionId(),
                                new Favorite("movie",mMovie.getId(),false),
                                new ApiHandler.PostResponseListener() {
                                    @Override
                                    public void success(PostResponse response) {
                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                        Log.d("RESPONSE", response.statusMessage);
                                    }
                                });
                    }
                    liked = false;
                }else if(!MovieAppApplication.isUserLoggedIn()){
                    showLoginDialog();
                }
                return true;
            case R.id.action_watchlist_movie:
                if(!watchlist && MovieAppApplication.isUserLoggedIn()){
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_filled_menu));
                    if(!MovieAppApplication.getUser().getWatchlistMovieList().contains(mMovie.getId())){
                        MovieAppApplication.getUser().addToWatchlistMoviesList(mMovie.getId());

                        ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                MovieAppApplication.getUser().getSessionId(),
                                new Watchlist("movie",mMovie.getId(),true),
                                new ApiHandler.PostResponseListener() {
                                    @Override
                                    public void success(PostResponse response) {
                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                        Log.d("RESPONSE", response.statusMessage);
                                    }
                                });
                    }
                    watchlist = true;
                }else if(watchlist && MovieAppApplication.isUserLoggedIn()){
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_menu));
                    if(MovieAppApplication.getUser().getWatchlistMovieList().contains(mMovie.getId())){
                        MovieAppApplication.getUser().removeFromWatchlistMovieList(mMovie.getId());

                        ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                MovieAppApplication.getUser().getSessionId(),
                                new Watchlist("movie",mMovie.getId(),false),
                                new ApiHandler.PostResponseListener() {
                                    @Override
                                    public void success(PostResponse response) {
                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                        Log.d("RESPONSE", response.statusMessage);
                                    }
                                });
                    }
                    watchlist = false;
                }else if(!MovieAppApplication.isUserLoggedIn()){
                    showLoginDialog();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(resultCode == RESULT_OK){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
        }
    }
}
