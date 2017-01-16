package com.example.zsamir.movieappintership;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Adapters.ImageAdapter;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieDetails;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie mMovie;
    Credits mCredits;
    Images mMovieImages = new Images();

    Crew director;
    ArrayList<Crew> writers = new ArrayList<>();
    ArrayList<Cast> actors = new ArrayList<>();
    ArrayList<Backdrop> backdrops = new ArrayList<>();
    CastAdapter mCastAdapter = new CastAdapter(actors);
    ImageAdapter mImageAdapter;
    // Genre ids sometimes empty take care of that

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (getIntent().hasExtra("Movie")) {
            mMovie = getIntent().getParcelableExtra("Movie");
            mImageAdapter = new ImageAdapter(backdrops,mMovie);
        }

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.requestMovieImages(mMovie.getId(), new ApiHandler.ImagesListener() {
            @Override
            public void success(Images response) {
                mMovieImages = response;
                backdrops.addAll(mMovieImages.backdrops);
                mImageAdapter.notifyDataSetChanged();
            }
        });


        apiHandler.requestMovieCredits(mMovie.getId(), new ApiHandler.CreditsListener() {
            @Override
            public void success(Credits response) {
                mCredits = response;

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

                    TextView mMovieWriters = (TextView) findViewById(R.id.movie_details_writers_2);
                    if (writers.size() > 2)
                        mMovieWriters.setText(writers.get(0).name + ", " + writers.get(1).name + ", " + writers.get(2).name);
                    else {
                        if (!writers.isEmpty())
                            mMovieWriters.setText(writers.get(0).name);
                        else
                            mMovieWriters.setText("");
                    }
                }
                StringBuilder sb = new StringBuilder();
                if(mCredits.cast!=null){
                actors.addAll(mCredits.cast);
                mCastAdapter.notifyDataSetChanged();
                if(actors.size()>0) {
                    for(int i=0;i<actors.size() && i<3;i++){
                        sb.append(actors.get(i).name);
                        if(i!=2)
                        sb.append(", ");
                    }
                    TextView mMovieStars = (TextView) findViewById(R.id.movie_details_stars_2);
                    mMovieStars.setText(sb.toString());
                }

            }}
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

        ImageView mMovieImage = (ImageView) findViewById(R.id.movie_details_image);
        if(mMovie.getBackdropUrl()!=null)
        Glide.with(this).load(mMovie.getBackdropUrl()).into(mMovieImage);

        TextView mMovieName = (TextView) findViewById(R.id.movie_details_name);
        if(mMovie.getTitle()!=null && mMovie.getReleaseYear()!=null)
        mMovieName.setText(mMovie.getTitle()+" ("+mMovie.getReleaseYear()+")");

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

        TextView mRating = (TextView) findViewById(R.id.movie_details_raiting);
        mRating.setText(Float.toString(mMovie.getVoteAverage())+" /10");

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
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }
}
