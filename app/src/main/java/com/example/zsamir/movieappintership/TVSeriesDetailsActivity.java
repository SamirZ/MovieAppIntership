package com.example.zsamir.movieappintership;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Adapters.ImageAdapter;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.TvSeries;

import java.util.ArrayList;

// TO DO

public class TVSeriesDetailsActivity extends AppCompatActivity {

    TvSeries mTVseries;
    Credits mCredits;
    Images mMovieImages = new Images();

    Crew director;
    ArrayList<Crew> writers = new ArrayList<>();
    ArrayList<Cast> actors = new ArrayList<>();
    ArrayList<Backdrop> backdrops = new ArrayList<>();
    CastAdapter mCastAdapter = new CastAdapter(actors);
    ImageAdapter mImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries_details);
    }
}
