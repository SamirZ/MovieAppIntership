package com.example.zsamir.movieappintership.TVSeries;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.CastAdapter;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.EpisodeCast;
import com.example.zsamir.movieappintership.Modules.EpisodeCredits;
import com.example.zsamir.movieappintership.Modules.EpisodeDetails;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EpisodeActivity extends AppCompatActivity {

    Episode episode;
    TvSeriesDetails tvSeriesDetails;
    ArrayList<EpisodeCast> actors = new ArrayList<>();
    CastAdapter mCastAdapter = new CastAdapter(actors,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        TextView episodeName = (TextView) findViewById(R.id.episode_details_name);
        final TextView episodeCastLabel = (TextView) findViewById(R.id.episode_details_cast_label);
        TextView episodeDate = (TextView) findViewById(R.id.episode_details_date);
        TextView episodeRating = (TextView) findViewById(R.id.episode_details_rating);
        TextView episodeRating2 = (TextView) findViewById(R.id.episode_details_rating_2);
        final TextView episodeOverview = (TextView) findViewById(R.id.episode_details_overview);
        final View breakline = findViewById(R.id.episode_break_line_details_2);
        ImageView episodeImage = (ImageView) findViewById(R.id.episode_details_image);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.episode_details_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCastAdapter);

        if(getIntent().hasExtra("EpisodeDetails")){
            episode = getIntent().getParcelableExtra("EpisodeDetails");
        }

        if(getIntent().hasExtra("TVSeriesDetails")){
            tvSeriesDetails = getIntent().getParcelableExtra("TVSeriesDetails");
            this.setTitle(tvSeriesDetails.getName());

            ApiHandler.getInstance().requestEpisodeDetails(tvSeriesDetails.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber(), new ApiHandler.EpisodeDetailsListener() {
                @Override
                public void success(EpisodeDetails response) {
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

        }

        if(tvSeriesDetails.getBackdropPath()!=null){
            Glide.with(episodeImage.getContext()).load(tvSeriesDetails.getBackdropUrl()).into(episodeImage);
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
            episodeRating2.setText(" /10");
        }else{
            episodeRating.setVisibility(View.GONE);
            episodeRating2.setVisibility(View.GONE);
        }

        // get Cast
        ApiHandler.getInstance().requestEpisodeCredits(tvSeriesDetails.getId(), episode.getSeasonNumber(), episode.getEpisodeNumber(), new ApiHandler.EpisodeCreditsListener() {
            @Override
            public void success(EpisodeCredits response) {
                actors.clear();
                if(response!=null){
                    if(response.cast.size()>0){
                        actors.addAll(response.cast);
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
