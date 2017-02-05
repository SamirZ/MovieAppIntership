package com.example.zsamir.movieappintership.TVSeries;

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
import com.example.zsamir.movieappintership.Common.GalleryActivity;
import com.example.zsamir.movieappintership.Common.TrailerActivity;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.Season;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

// TO DO

public class TVSeriesDetailsActivity extends AppCompatActivity {

    TvSeries mTVSeries;
    TvSeriesDetails mTVSeriesDetails;
    Credits mCredits;
    Images mTVSeriesImages = new Images();

    List<Season> seasons = new ArrayList<>();
    ArrayList<Cast> actors = new ArrayList<>();
    ArrayList<Backdrop> backdrops = new ArrayList<>();
    CastAdapter mCastAdapter = new CastAdapter(actors);
    ImageAdapter mImageAdapter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries_details);
        Fabric.with(this, new Crashlytics());

        if (getIntent().hasExtra("TVSeries")) {
            mTVSeries = getIntent().getParcelableExtra("TVSeries");
            mImageAdapter = new ImageAdapter(backdrops, mTVSeries);
        }

        setDetailedData();
        setKnowData();

        RecyclerView mImageRecyclerView = (RecyclerView) findViewById(R.id.tv_series_images_recyclerView);
        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageRecyclerView.setLayoutManager(layoutManagerImage);
        mImageRecyclerView.setAdapter(mImageAdapter);

        RecyclerView mCastRecyclerView = (RecyclerView) findViewById(R.id.tv_series_cast_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mCastRecyclerView.setLayoutManager(layoutManager);
        mCastRecyclerView.setAdapter(mCastAdapter);
    }

    private void setKnowData() {
        TextView mTvSeriesName = (TextView) findViewById(R.id.tv_series_details_name);
        if(mTVSeries.getName()!=null && mTVSeries.getReleaseYear()!=null)
        mTvSeriesName.setText(mTVSeries.getName()+" ("+mTVSeries.getReleaseYear()+")");

        TextView mTvSeriesGenre = (TextView) findViewById(R.id.tv_series_details_genre);
        if(mTVSeries.getTvSeriesGenres().size()>0){
            String s = "";
            for (int i =0;i<mTVSeries.getTvSeriesGenres().size();i++) {
                if(i!=mTVSeries.getTvSeriesGenres().size()-1)
                    s = s + mTVSeries.getTvSeriesGenres().get(i) +", ";
                else
                    s = s + mTVSeries.getTvSeriesGenres().get(i);
            }
            mTvSeriesGenre.setText(s);
        }
        else
            mTvSeriesGenre.setText(" ");

        ImageView mTvSeriesImage = (ImageView) findViewById(R.id.tv_series_details_image);
        if(mTVSeries.getBackdropUrl()!=null){
            Glide.with(this).load(mTVSeries.getBackdropUrl()).into(mTvSeriesImage);
            mTvSeriesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent i = new Intent(TVSeriesDetailsActivity.this, TrailerActivity.class);
                    //i.putExtra("TVID",String.valueOf(mTVSeries.getId()));
                    //startActivity(i);
                }
            });
        }

        TextView seeGallery = (TextView) findViewById(R.id.tv_series_see_all);
        seeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GalleryActivity.class);
                if(mTVSeries!=null)
                i.putExtra("TVSeries",mTVSeries);
                view.getContext().startActivity(i);
            }
        });


        TextView mMovieOverview = (TextView) findViewById(R.id.tv_series_details_overview);
        mMovieOverview.setText(mTVSeries.getOverview());
    }

    private void setDetailedData() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.requestTVSeriesDetails(mTVSeries.getId(), new ApiHandler.TvSeriesDetailsListener() {
            @Override
            public void success(TvSeriesDetails response) {

                mTVSeriesDetails = response;


                TextView seeSeasons = (TextView) findViewById(R.id.tv_series_seasons_see_all);
                if(mTVSeriesDetails.getNumberOfSeasons()>0){
                    seeSeasons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(view.getContext(), SeasonActivity.class);
                            if(mTVSeriesDetails!=null)
                                i.putExtra("TVSeriesDetails",mTVSeriesDetails);
                            view.getContext().startActivity(i);
                        }
                    });
                }
                else{
                    seeSeasons.setVisibility(View.GONE);
                }

                TextView mTVSeriesDirectorLabel = (TextView) findViewById(R.id.tv_series_details_director_1);
                TextView mTVSeriesDirector = (TextView) findViewById(R.id.tv_series_details_director_2);
                if(response.getCreatedBy()!=null && response.getCreatedBy().size()>0)
                    mTVSeriesDirector.setText(response.getCreatedBy().get(0).getName());
                else{
                    mTVSeriesDirector.setVisibility(View.GONE);
                    mTVSeriesDirectorLabel.setVisibility(View.GONE);
                }

                TextView mTVSeriesFirstAiringDate = (TextView) findViewById(R.id.tv_series_details_release_date);
                if(response.getReleaseYear()!=null && response.getFinishYear()!=null)
                    mTVSeriesFirstAiringDate.setText("TV Series ("+response.getReleaseYear()+"-"+response.getFinishYear()+")");
                else
                mTVSeriesFirstAiringDate.setVisibility(View.GONE);

                TextView mRating = (TextView) findViewById(R.id.tv_series_details_rating_1);
                TextView mRating2 = (TextView) findViewById(R.id.tv_series_details_rating_2);
                if(response.getVoteAverage()!=null){
                    mRating.setText(String.format(Locale.getDefault(),"%1$.1f",response.getVoteAverage()));
                    mRating2.setText(" /10");
                }

                TextView mTVSeriesWritersLablel = (TextView) findViewById(R.id.tv_series_details_writers_1);
                TextView mTVSeriesWriters = (TextView) findViewById(R.id.tv_series_details_writers_2);
                String writers = "";
                if(response.getCreatedBy().size() > 1) {
                    for (int i = 0; i < response.getCreatedBy().size(); i++) {
                        if (i != response.getCreatedBy().size() - 1)
                            writers = writers + response.getCreatedBy().get(i).getName() + " ,";
                        else
                            writers = writers + response.getCreatedBy().get(i).getName();
                    }
                    mTVSeriesWriters.setText(writers);
                }
                else {
                    mTVSeriesWriters.setVisibility(View.GONE);
                    mTVSeriesWritersLablel.setVisibility(View.GONE);
                }


                TextView mTVSeriesSeasonsLabel = (TextView) findViewById(R.id.tv_series_details_seasons_1);
                TextView mTVSeriesSeasonsYearsLabel = (TextView) findViewById(R.id.tv_series_details_years_1);
                TextView mTVSeriesSeasons = (TextView) findViewById(R.id.tv_series_details_seasons_2);
                TextView mTVSeriesSeasonsYears = (TextView) findViewById(R.id.tv_series_details_years_2);

                if(response.getSeasons()!=null) {
                    String seasonsText = "";
                    String yearsText = "";
                    seasons = response.getSeasons();
                    for (int i = seasons.size() - 1; i >= 0; i--) {
                        if(seasons.get(i).getSeasonNumber()!=0){
                            seasonsText = seasonsText + seasons.get(i).getSeasonNumber() + " ";
                            yearsText = yearsText + seasons.get(i).getAirYear() + " ";
                        }
                    }
                    if(seasonsText.length()>1 && yearsText.length()>1){
                        mTVSeriesSeasons.setText(seasonsText);
                        mTVSeriesSeasonsYears.setText(yearsText);
                    }else{
                        mTVSeriesSeasons.setVisibility(View.GONE);
                        mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                        mTVSeriesSeasonsYears.setVisibility(View.GONE);
                        mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                    }
                }else{
                    mTVSeriesSeasons.setVisibility(View.GONE);
                    mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                    mTVSeriesSeasonsYears.setVisibility(View.GONE);
                    mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                }
            }
        });

        final TextView tvSeriesImagesLabel = (TextView) findViewById(R.id.tv_series_details_images_label);
        final TextView tvSeriesImagesSeeAll = (TextView) findViewById(R.id.tv_series_see_all);
        final View tvSeriesImagesBreakline = findViewById(R.id.tv_series_details_images_breakline);
        final TextView castLabel = (TextView) findViewById(R.id.tv_series_cast_label);
        final TextView mTVSeriesStars = (TextView) findViewById(R.id.tv_series_details_stars_2);
        final TextView mTVSeriesStarsLabel = (TextView) findViewById(R.id.tv_series_details_stars_1);

        apiHandler.requestTVSeriesImages(mTVSeries.getId(), new ApiHandler.ImagesListener() {
            @Override
            public void success(Images response) {
                mTVSeriesImages = response;
                if(response!=null){
                    if(mTVSeriesImages.backdrops.size()>0){
                        backdrops.addAll(mTVSeriesImages.backdrops);
                        mImageAdapter.notifyDataSetChanged();
                    }else{
                        tvSeriesImagesLabel.setVisibility(View.GONE);
                        tvSeriesImagesSeeAll.setVisibility(View.GONE);
                        tvSeriesImagesBreakline.setVisibility(View.GONE);
                    }
                }else{
                    tvSeriesImagesLabel.setVisibility(View.GONE);
                    tvSeriesImagesSeeAll.setVisibility(View.GONE);
                    tvSeriesImagesBreakline.setVisibility(View.GONE);
                }
            }
        });


        apiHandler.requestTVSeriesCredits(mTVSeries.getId(), new ApiHandler.CreditsListener() {
            @Override
            public void success(Credits response) {
                if(response!=null){
                    mCredits = response;
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
                            mTVSeriesStars.setText(sb.toString());
                        }else{
                            mTVSeriesStarsLabel.setVisibility(View.GONE);
                            mTVSeriesStars.setVisibility(View.GONE);
                            castLabel.setVisibility(View.GONE);
                        }

                    }else{
                        castLabel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
