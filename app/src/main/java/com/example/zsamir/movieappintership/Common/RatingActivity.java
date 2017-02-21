package com.example.zsamir.movieappintership.Common;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.LoginModules.PostResponse;
import com.example.zsamir.movieappintership.LoginModules.Rating;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.Modules.TVSeriesList;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends BaseActivity {

    private RatingBar ratingBar;
    private Movie movie;
    private TVSeries tvSeries;
    private List<Movie> ratedMovies = new ArrayList<>();
    private List<TVSeries> ratedTVSeries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        if(getIntent().hasExtra("MOVIE")){
            setTitle(getString(R.string.rate_this)+" movie");
            movie = getIntent().getParcelableExtra("MOVIE");
            requestRatedMovies();
        }

        if(getIntent().hasExtra("TV")){
            setTitle(getString(R.string.rate_this)+" TV show");
            tvSeries = getIntent().getParcelableExtra("TV");
            requestRatedTVSeries();
        }


        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        ratingBar.setNumStars(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_rate:
                rateMedia();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void rateMedia() {
        if(movie!=null){
            Log.d("RATING", String.valueOf((double)ratingBar.getRating()));
            ApiHandler.getInstance().rateMovie(movie.getId(),
                    MovieAppApplication.getUser().getSessionId(),
                    new Rating((double) ratingBar.getRating()), new ApiHandler.PostResponseListener() {
                        @Override
                        public void success(PostResponse response) {
                            Toast.makeText(RatingActivity.this, "Successfully rated", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else if(tvSeries!=null){
            Log.d("RATING", String.valueOf((double)ratingBar.getRating()));
            ApiHandler.getInstance().rateTVShow(tvSeries.getId(),
                    MovieAppApplication.getUser().getSessionId(),
                    new Rating((double) ratingBar.getRating()), new ApiHandler.PostResponseListener() {
                        @Override
                        public void success(PostResponse response) {
                            Toast.makeText(RatingActivity.this, "Successfully rated", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void requestRatedMovies() {
        ApiHandler.getInstance().requestAccountRatedMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    ratedMovies.add(t);
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountRatedMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    ratedMovies.add(t);
                                }
                            }
                        });
                    }
                    for (Movie m:ratedMovies) {
                        if(movie!=null){
                            if(movie.getId()==m.getId()){
                                Log.d("MOVIE_RATING", String.valueOf((float)m.getRating()));
                                ratingBar.setRating((float)m.getRating());
                            }
                        }
                    }
                }else{
                    for (Movie m:ratedMovies) {
                        if(movie!=null){
                            if(movie.getId()==m.getId()){
                                Log.d("MOVIE_RATING", String.valueOf((float)m.getRating()));
                                ratingBar.setRating((float)m.getRating());
                            }
                        }
                    }
                }
            }
        });
    }

    private void requestRatedTVSeries(){
        ApiHandler.getInstance().requestAccountRatedTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVSeriesList response) {
                for(TVSeries t:response.getTVSeries()){
                    ratedTVSeries.add(t);
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountRatedTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVSeriesList response) {
                                for (TVSeries t: response.getTVSeries()) {
                                    ratedTVSeries.add(t);
                                }
                            }
                        });
                    }
                    for (TVSeries t:ratedTVSeries) {
                        if(tvSeries!=null){
                            if(tvSeries.getId()==t.getId()){
                                Log.d("TV_RATING", String.valueOf((float)t.getRating()));
                                ratingBar.setRating((float)t.getRating());
                            }
                        }
                    }
                }else{
                    for (TVSeries t:ratedTVSeries) {
                        if(tvSeries!=null){
                            if(tvSeries.getId()==t.getId()){
                                Log.d("TV_RATING", String.valueOf((float)t.getRating()));
                                ratingBar.setRating((float)t.getRating());
                            }
                        }
                    }
                }
            }
        });
    }
}
