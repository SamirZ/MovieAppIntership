package com.example.zsamir.movieappintership.TVSeries;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.EpisodeAdapter;
import com.example.zsamir.movieappintership.Adapters.SeasonsAdapter;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.SeasonDetails;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class SeasonActivity extends AppCompatActivity {

    public TvSeriesDetails tvSeriesDetails;

    private TextView seasonYear;
    private ArrayList<Episode> episodes;
    private EpisodeAdapter episodeAdapter;
    private SeasonsAdapter seasonsAdapter;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        Fabric.with(this, new Crashlytics());

        seasonYear = (TextView)findViewById(R.id.season_year);

        if(getIntent().hasExtra("TVSeriesDetails")){

            tvSeriesDetails = getIntent().getParcelableExtra("TVSeriesDetails");
            List<String> list = new ArrayList<>();
            List<String> years = new ArrayList<>();

            this.setTitle(tvSeriesDetails.getName());

            // Season 0 Specials not included

            for(int i = 0; i<tvSeriesDetails.getSeasons().size();i++){
                if(tvSeriesDetails.getSeasons().get(i).getSeasonNumber()!=0){
                    list.add(Integer.toString(tvSeriesDetails.getSeasons().get(i).getSeasonNumber()));
                    years.add(tvSeriesDetails.getSeasons().get(i).getAirYear());
                }
            }

            seasonsAdapter = new SeasonsAdapter(list,years,this);

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.season_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(seasonsAdapter);

            episodes = new ArrayList<>();

            episodeAdapter = new EpisodeAdapter(episodes,tvSeriesDetails);
            RecyclerView mEpisodeRecyclerView = (RecyclerView) findViewById(R.id.episode_recycler_view);
            LinearLayoutManager linearVerticalLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            mEpisodeRecyclerView.setLayoutManager(linearVerticalLayoutManager);
            mEpisodeRecyclerView.setAdapter(episodeAdapter);

            setSeason(1);
            if(year!=null)
            setYear(years.get(0));
            // need to retrieve from

        }
    }

    public void setSeason(int i){
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.requestTVSeriesSeasons(tvSeriesDetails.getId(), i, new ApiHandler.TvSeriesSeasonListener() {
            @Override
            public void success(SeasonDetails response) {
                episodes.clear();
                if(response!=null)
                    if(response.episodes!=null)
                        if(response.episodes.size()>0)
                        episodes.addAll(response.episodes);
                episodeAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setYear(String year) {
        seasonYear.setText(year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_season_details, menu);
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
