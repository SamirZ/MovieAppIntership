package com.example.zsamir.movieappintership.Cinema;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.ImageFormat;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class CinemaMovieActivity extends BaseActivity {

    private CinemaMovie cinemaMovie;

    private Credits mCredits;

    private Crew director;
    private ArrayList<Crew> writers = new ArrayList<>();
    private ArrayList<Cast> actors = new ArrayList<>();
    private CastAdapter mCastAdapter = new CastAdapter(actors);

    private TextView mMovieOverview;
    private TextView mMovieDirectorLabel;
    private TextView mMovieDirector;
    private TextView mMovieWritersLabel;
    private TextView mMovieWriters;
    private TextView mRating;
    private TextView mRating2;
    private TextView rateText;
    private TextView mMovieStarsLabel;
    private TextView mMovieStars;
    private TextView mMovieName;
    private TextView mMovieGenre;

    private ImageView mMovieImage;
    private ImageView rateImage;

    private Spinner timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_movie);

        if(getIntent().hasExtra("MOVIE")){
            cinemaMovie = getIntent().getParcelableExtra("MOVIE");

            setUpViews();

            setUpData();
        }
    }

    private void setUpViews() {
        mMovieOverview = (TextView) findViewById(R.id.cinema_movie_details_overview);
        mMovieImage = (ImageView) findViewById(R.id.cinema_movie_details_image);
        mMovieDirectorLabel = (TextView) findViewById(R.id.cinema_movie_details_director_1);
        mMovieDirector = (TextView) findViewById(R.id.cinema_movie_details_director_2);
        mMovieWritersLabel = (TextView) findViewById(R.id.cinema_movie_details_writers_1);
        mMovieWriters = (TextView) findViewById(R.id.cinema_movie_details_writers_2);
        mRating = (TextView) findViewById(R.id.cinema_movie_details_rating_1);
        mRating2 = (TextView) findViewById(R.id.cinema_movie_details_rating_2);
        rateImage = (ImageView) findViewById(R.id.cinema_movie_details_star_image);
        rateText = (TextView) findViewById(R.id.cinema_movie_details_rating_3);
        mMovieStarsLabel = (TextView) findViewById(R.id.cinema_movie_details_stars_1);
        mMovieStars = (TextView) findViewById(R.id.cinema_movie_details_stars_2);
        mMovieName = (TextView) findViewById(R.id.cinema_movie_details_name);
        mMovieGenre = (TextView) findViewById(R.id.cinema_movie_details_genre);
        timePicker = (Spinner) findViewById(R.id.time_picker);
    }

    private void setUpData() {
        mRating.setText(String.format(Locale.getDefault(),"%1$.1f",cinemaMovie.getRating()));
        mRating2.setText(getString(R.string.max_rating));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MovieAppApplication.isUserLoggedIn()){
                    //Intent i = new Intent(CinemaMovieActivity.this, RatingActivity.class);
                    //i.putExtra("MOVIE",cinemaMovie);
                    //startActivityForResult(i,1);
                }else{
                    showLoginDialog();
                }
            }
        };

        //rateImage.setOnClickListener(onClickListener);
        rateText.setOnClickListener(onClickListener);


        if(cinemaMovie.getOverview()!=null)
            mMovieOverview.setText(cinemaMovie.getOverview());


        // load image from memory
        if(cinemaMovie.getBackdropPath()!=null){
            Glide.with(this).load(ImageFormat.BASE_IMG_URL+ImageFormat.BACKDROP_SIZE_W780+cinemaMovie.getBackdropPath()).into(mMovieImage);
        }


        if(cinemaMovie.getName()!=null)
            mMovieName.setText(cinemaMovie.getName());


        if(cinemaMovie.getGenres()!=null)
            mMovieGenre.setText(cinemaMovie.getGenres());

        ApiHandler.getInstance().requestMovie(cinemaMovie.getId(), new ApiHandler.MovieDetailsListener() {
            @Override
            public void success(MovieDetails response) {
                TextView mMovieReleaseDate = (TextView) findViewById(R.id.cinema_movie_details_release_date);
                if(!response.getProductionCountries().isEmpty()) {
                    mMovieReleaseDate.setText(cinemaMovie.getReleaseDate() + " " + "(" + response.getProductionCountries().get(0).getName() + ")");
                }
                else mMovieReleaseDate.setText(cinemaMovie.getReleaseDate());
            }
        });


        ApiHandler.getInstance().requestMovieCredits(cinemaMovie.getId(), new ApiHandler.CreditsListener() {
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
                    }
                }else{
                    mMovieDirector.setVisibility(View.GONE);
                    mMovieDirectorLabel.setVisibility(View.GONE);
                }
                StringBuilder sb = new StringBuilder();


                if(mCredits.cast!=null){
                    if(mCredits.cast.size()>0){
                        actors.addAll(mCredits.cast);
                        mCastAdapter.notifyDataSetChanged();
                    }else{
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

        ArrayList<String> times = new ArrayList<>();
        times.add("16:00");
        times.add("20:00");
        times.add("22:00");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.selected_time_item, times);
        adapter.setDropDownViewResource(R.layout.time_item);
        timePicker.setAdapter(adapter);
        timePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}
