package com.example.zsamir.movieappintership.TVSeries;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Adapters.ImageAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.GalleryActivity;
import com.example.zsamir.movieappintership.Common.TrailerActivity;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowGenres;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Favorite;
import com.example.zsamir.movieappintership.LoginModules.PostResponse;
import com.example.zsamir.movieappintership.LoginModules.Watchlist;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.Season;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.Common.RatingActivity;
import com.example.zsamir.movieappintership.RealmUtils.PostModel;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TVSeriesDetailsActivity extends BaseActivity {

    private TVShow mTVShow;
    private TVShowDetails mTVShowDetails;
    private Credits mCredits;
    private Images mTVSeriesImages = new Images();
    private boolean liked = false;
    private boolean watchlist = false;

    private List<Season> seasons = new ArrayList<>();
    private ArrayList<Cast> actors = new ArrayList<>();
    private ArrayList<Backdrop> backdrops = new ArrayList<>();
    private CastAdapter mCastAdapter = new CastAdapter(actors);
    private ImageAdapter mImageAdapter;

    private TextView mTvSeriesName;
    private TextView mTvSeriesGenre;
    private TextView mMovieOverview;
    private TextView seeSeasons;
    private TextView mTVSeriesDirectorLabel;
    private TextView mTVSeriesDirector;
    private TextView rateText;
    private TextView mTVSeriesFirstAiringDate;
    private TextView mRating;
    private TextView mRating2;
    private TextView mTVSeriesWritersLablel;
    private TextView mTVSeriesWriters;
    private TextView mTVSeriesSeasonsLabel;
    private TextView mTVSeriesSeasonsYearsLabel;
    private TextView mTVSeriesSeasons;
    private TextView mTVSeriesSeasonsYears;
    private TextView tvSeriesImagesLabel;
    private TextView tvSeriesImagesSeeAll;
    private TextView castLabel;
    private TextView mTVSeriesStars;
    private TextView mTVSeriesStarsLabel;

    private View tvSeriesImagesBreakline;

    private ImageView mTvSeriesImage;
    private ImageView rateImage;
    private ImageView playVideo;

    private RecyclerView mImageRecyclerView;
    private RecyclerView mCastRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries_details);

        if (getIntent().hasExtra("TVSeries")) {
            mTVShow = getIntent().getParcelableExtra("TVSeries");

            mImageAdapter = new ImageAdapter(backdrops, mTVShow);
        }

        setUpViews();

        setKnowData();

        setDetailedData();

        setUpImages();

        setUpCast();
    }

    private void setUpViews() {
        mTvSeriesName = (TextView) findViewById(R.id.tv_series_details_name);
        mTvSeriesGenre = (TextView) findViewById(R.id.tv_series_details_genre);
        mMovieOverview = (TextView) findViewById(R.id.tv_series_details_overview);
        seeSeasons = (TextView) findViewById(R.id.tv_series_seasons_see_all);
        mTVSeriesDirectorLabel = (TextView) findViewById(R.id.tv_series_details_director_1);
        mTVSeriesDirector = (TextView) findViewById(R.id.tv_series_details_director_2);
        rateText = (TextView) findViewById(R.id.tv_series_details_rating_3);
        mTVSeriesFirstAiringDate = (TextView) findViewById(R.id.tv_series_details_release_date);
        mRating = (TextView) findViewById(R.id.tv_series_details_rating_1);
        mRating2 = (TextView) findViewById(R.id.tv_series_details_rating_2);
        mTVSeriesWritersLablel = (TextView) findViewById(R.id.tv_series_details_writers_1);
        mTVSeriesWriters = (TextView) findViewById(R.id.tv_series_details_writers_2);
        mTVSeriesSeasonsLabel = (TextView) findViewById(R.id.tv_series_details_seasons_1);
        mTVSeriesSeasonsYearsLabel = (TextView) findViewById(R.id.tv_series_details_years_1);
        mTVSeriesSeasons = (TextView) findViewById(R.id.tv_series_details_seasons_2);
        mTVSeriesSeasonsYears = (TextView) findViewById(R.id.tv_series_details_years_2);
        tvSeriesImagesLabel = (TextView) findViewById(R.id.tv_series_details_images_label);
        tvSeriesImagesSeeAll = (TextView) findViewById(R.id.tv_series_see_all);
        castLabel = (TextView) findViewById(R.id.tv_series_cast_label);
        mTVSeriesStars = (TextView) findViewById(R.id.tv_series_details_stars_2);
        mTVSeriesStarsLabel = (TextView) findViewById(R.id.tv_series_details_stars_1);
        playVideo = (ImageView) findViewById(R.id.tv_play_video);

        tvSeriesImagesBreakline = findViewById(R.id.tv_series_details_images_breakline);

        mTvSeriesImage = (ImageView) findViewById(R.id.tv_series_details_image);
        rateImage = (ImageView) findViewById(R.id.tv_series_details_star_image);

        mImageRecyclerView = (RecyclerView) findViewById(R.id.tv_series_images_recyclerView);
        mCastRecyclerView = (RecyclerView) findViewById(R.id.tv_series_cast_recyclerView);
    }

    private void setUpCast() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mCastRecyclerView.setLayoutManager(layoutManager);
        mCastRecyclerView.setAdapter(mCastAdapter);
    }

    private void setUpImages() {
        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageRecyclerView.setLayoutManager(layoutManagerImage);
        mImageRecyclerView.setAdapter(mImageAdapter);
    }

    private void setKnowData() {
        if(mTVShow.getName()!=null && mTVShow.getReleaseYear()!=null)
            mTvSeriesName.setText(mTVShow.getName()+" ("+ mTVShow.getReleaseYear()+")");


        if(isNetworkAvailable()) {
            if (mTVShow.getTvSeriesGenres().size() > 0) {
                String s = "";
                for (int i = 0; i < mTVShow.getTvSeriesGenres().size(); i++) {
                    if (i != mTVShow.getTvSeriesGenres().size() - 1)
                        s = s + mTVShow.getTvSeriesGenres().get(i) + ", ";
                    else
                        s = s + mTVShow.getTvSeriesGenres().get(i);
                }
                mTvSeriesGenre.setText(s);
            } else
                mTvSeriesGenre.setText(" ");
        }else {
            if (mTVShow.allGenres != null) {
                String[] genres = mTVShow.allGenres.split(",");
                String s = "";
                for (int i = 0; i < genres.length; i++) {
                    if (i != genres.length - 1){
                        s = s + TVShowGenres.getById(Integer.parseInt(genres[i])).getTitle() + ", ";
                    }
                    else
                        s = s + TVShowGenres.getById(Integer.parseInt(genres[i])).getTitle();
                }
                mTvSeriesGenre.setText(s);
            }
        }

        if(mTVShow.getBackdropUrl()!=null){
            Glide.with(this).load(mTVShow.getBackdropUrl()).into(mTvSeriesImage);
            playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkAvailable()) {
                        Intent i = new Intent(TVSeriesDetailsActivity.this, TrailerActivity.class);
                        i.putExtra("TVID", mTVShow);
                        startActivity(i);
                    }else{
                        showNoDataDialog();
                    }
                }
            });
        }

        tvSeriesImagesSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    Intent i = new Intent(view.getContext(), GalleryActivity.class);
                    if (mTVShow != null)
                        i.putExtra("TVSeries", mTVShow);
                    view.getContext().startActivity(i);
                }else{
                    showNoDataDialog();
                }
            }
        });


        mMovieOverview.setText(mTVShow.getOverview());
    }

    private void setDetailedData() {

        if (isNetworkAvailable()) {

            RealmUtils.getInstance().deleteTVShowDetails(mTVShow.getId());
            RealmUtils.getInstance().createRealmTVShowDetails(mTVShow.getId());

            ApiHandler apiHandler = ApiHandler.getInstance();
            apiHandler.requestTVSeriesDetails(mTVShow.getId(), new ApiHandler.TvSeriesDetailsListener() {
                @Override
                public void success(TVShowDetails response) {

                    mTVShowDetails = response;
                    RealmUtils.getInstance().addRealmTVShowDetails(mTVShow.getId(),mTVShowDetails);

                    if (mTVShowDetails != null) {
                        if (mTVShowDetails.getSeasons() != null) {
                            RealmUtils.getInstance().addRealmTVShowDetailsSeasons(mTVShow.getId(),mTVShowDetails.getSeasons());
                            if (mTVShowDetails.getNumberOfSeasons() > 0) {
                                seeSeasons.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(view.getContext(), SeasonActivity.class);
                                        if (mTVShowDetails != null)
                                            i.putExtra("TVSeriesDetails", mTVShowDetails);
                                        view.getContext().startActivity(i);
                                    }
                                });
                            } else {
                                seeSeasons.setVisibility(View.GONE);
                            }
                        } else {
                            seeSeasons.setVisibility(View.GONE);
                        }
                    } else {
                        seeSeasons.setVisibility(View.GONE);
                    }

                    if (response != null) {
                        if (response.getCreatedBy() != null && response.getCreatedBy().size() > 0){
                            mTVSeriesDirector.setText(response.getCreatedBy().get(0).getName());
                            RealmUtils.getInstance().addRealmTVShowDetailsCreatedBy(mTVShow.getId(),response.getCreatedBy().get(0));
                        }
                        else {
                            mTVSeriesDirector.setVisibility(View.GONE);
                            mTVSeriesDirectorLabel.setVisibility(View.GONE);
                        }
                    } else {
                        mTVSeriesDirector.setVisibility(View.GONE);
                        mTVSeriesDirectorLabel.setVisibility(View.GONE);
                    }

                    if (response != null) {
                        if (response.getReleaseYear() != null && response.getFinishYear() != null) {
                            mTVSeriesFirstAiringDate.setText("TV Series (" + response.getReleaseYear() + "-" + response.getFinishYear() + ")");
                            RealmUtils.getInstance().addRealmTVShowDetailsLastAirDate(mTVShow.getId(),response.getLastAirDate());
                        }else
                            mTVSeriesFirstAiringDate.setVisibility(View.GONE);
                    } else
                        mTVSeriesFirstAiringDate.setVisibility(View.GONE);


                    mRating.setText(String.format(Locale.getDefault(), "%1$.1f", mTVShow.getVoteAverage()));
                    mRating2.setText(getString(R.string.max_rating));


                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MovieAppApplication.isUserLoggedIn()) {
                                Intent i = new Intent(TVSeriesDetailsActivity.this, RatingActivity.class);
                                i.putExtra("TV", mTVShow);
                                startActivityForResult(i, 1);
                            } else {
                                showLoginDialog();
                            }
                        }
                    };

                    rateImage.setOnClickListener(onClickListener);
                    rateText.setOnClickListener(onClickListener);


                    String writers = "";
                    if (response != null) {
                        if (response.getCreatedBy().size() > 1) {
                            for (int i = 0; i < response.getCreatedBy().size(); i++) {
                                if (i != response.getCreatedBy().size() - 1)
                                    writers = writers + response.getCreatedBy().get(i).getName() + " ,";
                                else
                                    writers = writers + response.getCreatedBy().get(i).getName();
                                RealmUtils.getInstance().addRealmTVShowDetailsWriters(mTVShow.getId(),response.getCreatedBy());
                            }
                            mTVSeriesWriters.setText(writers);
                        } else {
                            mTVSeriesWriters.setVisibility(View.GONE);
                            mTVSeriesWritersLablel.setVisibility(View.GONE);
                        }
                    } else {
                        mTVSeriesWriters.setVisibility(View.GONE);
                        mTVSeriesWritersLablel.setVisibility(View.GONE);
                    }

                    if (response != null) {
                        if (response.getSeasons() != null) {
                            String seasonsText = "";
                            String yearsText = "";
                            seasons = response.getSeasons();
                            for (int i = seasons.size() - 1; i >= 0; i--) {
                                if (seasons.get(i).getSeasonNumber() != 0) {
                                    seasonsText = seasonsText + seasons.get(i).getSeasonNumber() + " ";
                                    yearsText = yearsText + seasons.get(i).getAirYear() + " ";
                                }
                            }
                            if (seasonsText.length() > 1 && yearsText.length() > 1) {
                                mTVSeriesSeasons.setText(seasonsText);
                                mTVSeriesSeasonsYears.setText(yearsText);
                            } else {
                                mTVSeriesSeasons.setVisibility(View.GONE);
                                mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                                mTVSeriesSeasonsYears.setVisibility(View.GONE);
                                mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                            }
                        } else {
                            mTVSeriesSeasons.setVisibility(View.GONE);
                            mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                            mTVSeriesSeasonsYears.setVisibility(View.GONE);
                            mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                        }
                    } else {
                        mTVSeriesSeasons.setVisibility(View.GONE);
                        mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                        mTVSeriesSeasonsYears.setVisibility(View.GONE);
                        mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                    }
                }
            });

//
            apiHandler.requestTVSeriesImages(mTVShow.getId(), new ApiHandler.ImagesListener() {
                @Override
                public void success(Images response) {
                    mTVSeriesImages = response;
                    if (response != null) {
                        if (mTVSeriesImages.getBackdrops().size() > 0) {
                            backdrops.addAll(mTVSeriesImages.getBackdrops());
                            RealmUtils.getInstance().addRealmTVShowDetailsBackdrops(mTVShow.getId(),backdrops);
                            mImageAdapter.notifyDataSetChanged();
                        } else {
                            tvSeriesImagesLabel.setVisibility(View.GONE);
                            tvSeriesImagesSeeAll.setVisibility(View.GONE);
                            tvSeriesImagesBreakline.setVisibility(View.GONE);
                        }
                    } else {
                        tvSeriesImagesLabel.setVisibility(View.GONE);
                        tvSeriesImagesSeeAll.setVisibility(View.GONE);
                        tvSeriesImagesBreakline.setVisibility(View.GONE);
                    }
                }
            });


            apiHandler.requestTVSeriesCredits(mTVShow.getId(), new ApiHandler.CreditsListener() {
                @Override
                public void success(Credits response) {
                    if (response != null) {
                        mCredits = response;
                        StringBuilder sb = new StringBuilder();
                        if (mCredits.cast != null) {
                            actors.addAll(mCredits.cast);
                            RealmUtils.getInstance().addRealmTVShowDetailsActors(mTVShow.getId(),actors);
                            mCastAdapter.notifyDataSetChanged();
                            if (actors.size() > 0) {
                                for (int i = 0; i < actors.size() && i < 3; i++) {
                                    sb.append(actors.get(i).getName());
                                    if (i != 2)
                                        sb.append(", ");
                                }
                                mTVSeriesStars.setText(sb.toString());
                            } else {
                                mTVSeriesStarsLabel.setVisibility(View.GONE);
                                mTVSeriesStars.setVisibility(View.GONE);
                                castLabel.setVisibility(View.GONE);
                            }

                        } else {
                            castLabel.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }else{
            //Offline mode
            //RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId())


            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                mTVShowDetails = RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getTvShowDetails();
                mTVShowDetails.setSeasons(RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getSeasons());
                if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getSeasons() != null) {
                    if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getSeasons().size() > 0) {
                        seeSeasons.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(view.getContext(), SeasonActivity.class);
                                if (mTVShowDetails != null)
                                    i.putExtra("TVSeriesDetails", mTVShowDetails);
                                view.getContext().startActivity(i);
                            }
                        });
                    } else {
                        seeSeasons.setVisibility(View.GONE);
                    }
                } else {
                    seeSeasons.setVisibility(View.GONE);
                }
            } else {
                seeSeasons.setVisibility(View.GONE);
            }

            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getCreatedBy() != null)
                    mTVSeriesDirector.setText(RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getCreatedBy().getName());
                else {
                    mTVSeriesDirector.setVisibility(View.GONE);
                    mTVSeriesDirectorLabel.setVisibility(View.GONE);
                }
            } else {
                mTVSeriesDirector.setVisibility(View.GONE);
                mTVSeriesDirectorLabel.setVisibility(View.GONE);
            }

            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                if (mTVShow.getReleaseYear() != null && RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getFinishYear() != null)
                    mTVSeriesFirstAiringDate.setText("TV Series (" + mTVShow.getReleaseYear() + "-" + RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getFinishYear() + ")");
                else
                    mTVSeriesFirstAiringDate.setVisibility(View.GONE);
            } else
                mTVSeriesFirstAiringDate.setVisibility(View.GONE);


            if (mTVShow != null) {
                mRating.setText(String.format(Locale.getDefault(), "%1$.1f", mTVShow.getVoteAverage()));
                mRating2.setText(getString(R.string.max_rating));
            }


            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MovieAppApplication.isUserLoggedIn()) {
                        Intent i = new Intent(TVSeriesDetailsActivity.this, RatingActivity.class);
                        i.putExtra("TV", mTVShow);
                        startActivityForResult(i, 1);
                    } else {
                        showLoginDialog();
                    }
                }
            };

            rateImage.setOnClickListener(onClickListener);
            rateText.setOnClickListener(onClickListener);


            String writers = "";
            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getWriters().size() > 1) {
                    for (int i = 0; i < RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getWriters().size(); i++) {
                        if (i != RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getWriters().size() - 1)
                            writers = writers + RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getWriters().get(i).getName() + " ,";
                        else
                            writers = writers + RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getWriters().get(i).getName();
                    }
                    mTVSeriesWriters.setText(writers);
                } else {
                    mTVSeriesWriters.setVisibility(View.GONE);
                    mTVSeriesWritersLablel.setVisibility(View.GONE);
                }
            } else {
                mTVSeriesWriters.setVisibility(View.GONE);
                mTVSeriesWritersLablel.setVisibility(View.GONE);
            }

            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getSeasons() != null) {
                    String seasonsText = "";
                    String yearsText = "";
                    seasons = RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getSeasons();

                    for (int i = seasons.size() - 1; i >= 0; i--) {
                        if (seasons.get(i).getSeasonNumber() != 0) {
                            seasonsText = seasonsText + seasons.get(i).getSeasonNumber() + " ";
                            yearsText = yearsText + seasons.get(i).getAirYear() + " ";
                        }
                    }
                    if (seasonsText.length() > 1 && yearsText.length() > 1) {
                        mTVSeriesSeasons.setText(seasonsText);
                        mTVSeriesSeasonsYears.setText(yearsText);
                    } else {
                        mTVSeriesSeasons.setVisibility(View.GONE);
                        mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                        mTVSeriesSeasonsYears.setVisibility(View.GONE);
                        mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                    }
                } else {
                    mTVSeriesSeasons.setVisibility(View.GONE);
                    mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                    mTVSeriesSeasonsYears.setVisibility(View.GONE);
                    mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
                }
            } else {
                mTVSeriesSeasons.setVisibility(View.GONE);
                mTVSeriesSeasonsLabel.setVisibility(View.GONE);
                mTVSeriesSeasonsYears.setVisibility(View.GONE);
                mTVSeriesSeasonsYearsLabel.setVisibility(View.GONE);
            }

            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getBackdrops().size() > 0) {
                    backdrops.addAll(RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getBackdrops());
                    mImageAdapter.notifyDataSetChanged();
                } else {
                    tvSeriesImagesLabel.setVisibility(View.GONE);
                    tvSeriesImagesSeeAll.setVisibility(View.GONE);
                    tvSeriesImagesBreakline.setVisibility(View.GONE);
                }
            } else {
                tvSeriesImagesLabel.setVisibility(View.GONE);
                tvSeriesImagesSeeAll.setVisibility(View.GONE);
                tvSeriesImagesBreakline.setVisibility(View.GONE);
            }

            StringBuilder sb = new StringBuilder();
            if (RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()) != null) {
                actors.addAll(RealmUtils.getInstance().readRealmTVShowDetails(mTVShow.getId()).getActors());
                mCastAdapter.notifyDataSetChanged();
                if (actors.size() > 0) {
                    for (int i = 0; i < actors.size() && i < 3; i++) {
                        sb.append(actors.get(i).getName());
                        if (i != 2)
                            sb.append(", ");
                    }
                    mTVSeriesStars.setText(sb.toString());
                } else {
                    mTVSeriesStarsLabel.setVisibility(View.GONE);
                    mTVSeriesStars.setVisibility(View.GONE);
                    castLabel.setVisibility(View.GONE);
                }

            } else {
                castLabel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvseries_details, menu);

        if(MovieAppApplication.isUserLoggedIn()){
            if(MovieAppApplication.getUser().getFavTVSeriesList()!=null)
                if(MovieAppApplication.getUser().getFavTVSeriesList().contains(mTVShow.getId())){
                    MenuItem item = menu.findItem(R.id.action_like_tv);
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.like_filled_menu));
                    liked = true;
                }

            if(MovieAppApplication.getUser().getWatchlistTVSeriesList()!=null)
                if(MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(mTVShow.getId())){
                    MenuItem item = menu.findItem(R.id.action_watchlist_tv);
                    item.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_filled_menu));
                    watchlist = true;
                }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_like_tv:
                if(!liked && MovieAppApplication.isUserLoggedIn()){
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.like_filled_menu));
                    if(!MovieAppApplication.getUser().getFavTVSeriesList().contains(mTVShow.getId())){
                        if(isNetworkAvailable()) {
                            ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                    MovieAppApplication.getUser().getSessionId(),
                                    new Favorite("tv", mTVShow.getId(), true),
                                    new ApiHandler.PostResponseListener() {
                                        @Override
                                        public void success(PostResponse response) {
                                            ArrayList<Integer> l = new ArrayList<>();
                                            l.add(mTVShow.getId());
                                            MovieAppApplication.getUser().addToFavoriteTVSeriesList(mTVShow.getId());
                                            liked = true;
                                            RealmUtils.getInstance().addRealmAccountFavTVShow(l);
                                        }
                                    });
                        }else{
                            //OFFLINE LIKE
                            ArrayList<Integer> l = new ArrayList<>();
                            l.add(mTVShow.getId());
                            MovieAppApplication.getUser().addToFavoriteTVSeriesList(mTVShow.getId());
                            liked = true;
                            RealmUtils.getInstance().addRealmAccountFavTVShow(l);

                            //POST FOR SYNC
                            PostModel postModel = RealmUtils.getInstance().readPostModel(mTVShow.getId());
                            if(postModel!=null) {
                                RealmUtils.getInstance().setRating(postModel,true);
                                RealmUtils.getInstance().createOrUpdatePostModel(postModel);
                            }else{
                                RealmUtils.getInstance().createOrUpdatePostModel(new PostModel(mTVShow.getId(),true,false,true));
                            }
                        }
                    }
                }else if(liked && MovieAppApplication.isUserLoggedIn()){
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.like_menu));
                    if(MovieAppApplication.getUser().getFavTVSeriesList().contains(mTVShow.getId())){
                        if(isNetworkAvailable()) {
                            ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                    MovieAppApplication.getUser().getSessionId(),
                                    new Favorite("tv", mTVShow.getId(), false),
                                    new ApiHandler.PostResponseListener() {
                                        @Override
                                        public void success(PostResponse response) {
                                            MovieAppApplication.getUser().removeFromFavoriteTVSeriesList(mTVShow.getId());
                                            liked = false;
                                            RealmUtils.getInstance().removeRealmAccountFavTVShow(mTVShow.getId());
                                        }
                                    });
                        }else{
                            //OFFLINE UNLIKE
                            MovieAppApplication.getUser().removeFromFavoriteTVSeriesList(mTVShow.getId());
                            liked = false;
                            RealmUtils.getInstance().removeRealmAccountFavTVShow(mTVShow.getId());

                            //DELETE POST
                            PostModel postModel = RealmUtils.getInstance().readPostModel(mTVShow.getId());
                            if(postModel!=null) {
                                RealmUtils.getInstance().setRating(postModel,false);
                                RealmUtils.getInstance().createOrUpdatePostModel(postModel);
                            }else{
                                RealmUtils.getInstance().createOrUpdatePostModel(new PostModel(mTVShow.getId(),false,false,true));
                            }
                        }
                    }
                }else if(!MovieAppApplication.isUserLoggedIn()){
                    showLoginDialog();
                }
                return true;
            case R.id.action_watchlist_tv:
                if(!watchlist && MovieAppApplication.isUserLoggedIn()){
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_filled_menu));
                    if(!MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(mTVShow.getId())){
                        if(isNetworkAvailable()) {
                            ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                    MovieAppApplication.getUser().getSessionId(),
                                    new Watchlist("tv", mTVShow.getId(), true),
                                    new ApiHandler.PostResponseListener() {
                                        @Override
                                        public void success(PostResponse response) {
                                            ArrayList<Integer> l = new ArrayList<>();
                                            l.add(mTVShow.getId());
                                            MovieAppApplication.getUser().addToWatchlistTVSeriesList(mTVShow.getId());
                                            watchlist = true;
                                            RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);
                                        }
                                    });
                        }else{
                            //OFFLINE PUT ON WATCHLIST
                            ArrayList<Integer> l = new ArrayList<>();
                            l.add(mTVShow.getId());
                            MovieAppApplication.getUser().addToWatchlistTVSeriesList(mTVShow.getId());
                            watchlist = true;
                            RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);

                            //POST FOR SYNC
                            PostModel postModel = RealmUtils.getInstance().readPostModel(mTVShow.getId());
                            if(postModel!=null) {
                                RealmUtils.getInstance().setWatch(postModel,true);
                                RealmUtils.getInstance().createOrUpdatePostModel(postModel);
                            }else{
                                RealmUtils.getInstance().createOrUpdatePostModel(new PostModel(mTVShow.getId(),true,false,true,1));
                            }
                        }
                    }
                }else if(watchlist && MovieAppApplication.isUserLoggedIn()){
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.bookmark_menu));
                    if(MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(mTVShow.getId())){
                        if(isNetworkAvailable()) {
                            ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                    MovieAppApplication.getUser().getSessionId(),
                                    new Watchlist("tv", mTVShow.getId(), false),
                                    new ApiHandler.PostResponseListener() {
                                        @Override
                                        public void success(PostResponse response) {
                                            MovieAppApplication.getUser().removeFromWatchlistTVSeriesList(mTVShow.getId());
                                            watchlist = false;
                                            RealmUtils.getInstance().removeRealmAccountWatchlistTVShow(mTVShow.getId());
                                        }
                                    });
                        }else{
                            // OFFLINE REMOVE FROM WATCHLIST
                            MovieAppApplication.getUser().removeFromWatchlistTVSeriesList(mTVShow.getId());
                            watchlist = false;
                            RealmUtils.getInstance().removeRealmAccountWatchlistTVShow(mTVShow.getId());

                            //DELETE POST
                            PostModel postModel = RealmUtils.getInstance().readPostModel(mTVShow.getId());
                            if(postModel!=null) {
                                RealmUtils.getInstance().setWatch(postModel,false);
                                RealmUtils.getInstance().createOrUpdatePostModel(postModel);
                            }else{
                                RealmUtils.getInstance().createOrUpdatePostModel(new PostModel(mTVShow.getId(),false,false,true,1));
                            }
                        }
                    }
                }else if(!MovieAppApplication.isUserLoggedIn()){
                    showLoginDialog();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
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