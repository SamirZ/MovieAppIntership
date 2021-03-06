package com.example.zsamir.movieappintership.TVSeries;

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
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.EpisodeCast;
import com.example.zsamir.movieappintership.Modules.EpisodeCredits;
import com.example.zsamir.movieappintership.Modules.EpisodeDetails;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.Locale;

public class EpisodeActivity extends BaseActivity {

    private Episode episode;
    private TVShowDetails TVShowDetails;
    private ArrayList<EpisodeCast> actors = new ArrayList<>();
    private ArrayList<Cast> offActors = new ArrayList<>();
    private CastAdapter mCastAdapter = new CastAdapter(actors,0);

    private TextView episodeName;
    private TextView episodeCastLabel;
    private TextView episodeDate;
    private TextView episodeRating;
    private TextView episodeRating2;
    private TextView episodeOverview;
    private View breakline;
    private ImageView episodeImage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        setUpViews();

        setUpCast();

        if(getIntent().hasExtra("EpisodeDetails")){
            episode = getIntent().getParcelableExtra("EpisodeDetails");
        }

        setUpDetails();

    }

    private void setUpCast() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCastAdapter);
    }

    private void setUpDetails() {
        if(getIntent().hasExtra("TVSeriesDetails")){
            TVShowDetails = getIntent().getParcelableExtra("TVSeriesDetails");
            this.setTitle(TVShowDetails.getName());

            if(isNetworkAvailable()){
                ApiHandler.getInstance().requestEpisodeDetails(TVShowDetails.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber(), new ApiHandler.EpisodeDetailsListener() {
                    @Override
                    public void success(EpisodeDetails response) {
                        RealmUtils.getInstance().deleteEpisodeDetails(episode.getName());
                        RealmUtils.getInstance().createRealmEpisodeDetails(episode.getName(), episode.getSeasonNumber(), episode.getEpisodeNumber());
                        RealmUtils.getInstance().addRealmEpisodeDetailsData(episode.getName(),response);
                        if(response.getOverview()!=null){
                            if(response.getOverview().length()>1)
                                episodeOverview.setText(response.getOverview());
                            else{
                                episodeOverview.setVisibility(View.GONE);
                                breakline.setVisibility(View.GONE);
                            }
                        }else {
                            episodeOverview.setVisibility(View.GONE);
                            breakline.setVisibility(View.GONE);
                        }
                    }
                });
            }else{
                if(RealmUtils.getInstance().readEpisodeDetails(episode.getName())!=null){
                    if(RealmUtils.getInstance().readEpisodeDetails(episode.getName()).getEpisodeDetails().getOverview().length()>1)
                        episodeOverview.setText(RealmUtils.getInstance().readEpisodeDetails(episode.getName()).getEpisodeDetails().getOverview());
                    else{
                        episodeOverview.setVisibility(View.GONE);
                        breakline.setVisibility(View.GONE);
                    }
                }else {
                    episodeOverview.setVisibility(View.GONE);
                    breakline.setVisibility(View.GONE);
                }
            }

        }

        if(TVShowDetails.getBackdropPath()!=null){
            Glide.with(episodeImage.getContext()).load(TVShowDetails.getBackdropUrl()).into(episodeImage);
        }

        if(episode.getName()!=null){
            episodeName.setText(episode.getName()+" ("+episode.getAirYear()+")");
        }else{
            episodeName.setVisibility(View.GONE);
        }

        if(episode.getAirDate()!=null){
            episodeDate.setText(episode.getAirDate());
        }else{
            episodeDate.setVisibility(View.GONE);
        }

        if(episode.getVoteAverage()>=0){
            episodeRating.setText(String.format(Locale.getDefault(),"%1$.1f",episode.getVoteAverage()));
            episodeRating2.setText(getString(R.string.max_rating));
        }else{
            episodeRating.setVisibility(View.GONE);
            episodeRating2.setVisibility(View.GONE);
        }

        // get Cast
        if(isNetworkAvailable()){
            ApiHandler.getInstance().requestEpisodeCredits(TVShowDetails.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber(), new ApiHandler.EpisodeCreditsListener() {
                @Override
                public void success(EpisodeCredits response) {
                    actors.clear();
                    if(response!=null){
                        if(response.getCast().size()>0){
                            actors.addAll(response.getCast());
                            ArrayList<Cast> c = new ArrayList<>();
                            for (EpisodeCast eCast:actors) {
                                c.add(eCast.toCast());
                            }
                            RealmUtils.getInstance().addRealmEpisodeDetailsCast(episode.getName(),c);
                            mCastAdapter.notifyDataSetChanged();
                        }
                        else{
                            episodeCastLabel.setVisibility(View.GONE);
                        }
                    }else{
                        episodeCastLabel.setVisibility(View.GONE);
                    }
                }
            });
        }else{
            actors.clear();
            if(RealmUtils.getInstance().readEpisodeDetails(episode.getName())!=null){
                if(RealmUtils.getInstance().readEpisodeDetails(episode.getName()).getEpisodeCast().size()>0){
                    offActors.addAll(RealmUtils.getInstance().readEpisodeDetails(episode.getName()).getEpisodeCast());

                    mCastAdapter = new CastAdapter(offActors);
                    recyclerView.setAdapter(mCastAdapter);
                    mCastAdapter.notifyDataSetChanged();
                }
                else{
                    episodeCastLabel.setVisibility(View.GONE);
                }
            }else{
                episodeCastLabel.setVisibility(View.GONE);
            }
        }
    }

    private void setUpViews() {
        episodeName = (TextView) findViewById(R.id.episode_details_name);
        episodeCastLabel = (TextView) findViewById(R.id.episode_details_cast_label);
        episodeDate = (TextView) findViewById(R.id.episode_details_date);
        episodeRating = (TextView) findViewById(R.id.episode_details_rating);
        episodeRating2 = (TextView) findViewById(R.id.episode_details_rating_2);
        episodeOverview = (TextView) findViewById(R.id.episode_details_overview);
        breakline = findViewById(R.id.episode_break_line_details_2);
        episodeImage = (ImageView) findViewById(R.id.episode_details_image);
        recyclerView = (RecyclerView) findViewById(R.id.episode_details_recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_episode_details, menu);
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
