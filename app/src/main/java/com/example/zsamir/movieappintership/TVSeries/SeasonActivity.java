package com.example.zsamir.movieappintership.TVSeries;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.EpisodeAdapter;
import com.example.zsamir.movieappintership.Adapters.SeasonsAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.SeasonDetails;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmSeasonDetails;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.List;


public class SeasonActivity extends BaseActivity {

    private TVShowDetails TVShowDetails;

    private TextView seasonYear;
    private ArrayList<Episode> episodes;
    private EpisodeAdapter episodeAdapter;

    private List<String> list = new ArrayList<>();
    private List<String> years = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        seasonYear = (TextView)findViewById(R.id.season_year);

        if(getIntent().hasExtra("TVSeriesDetails")){

            TVShowDetails = getIntent().getParcelableExtra("TVSeriesDetails");

            this.setTitle(TVShowDetails.getName());

            setUpSeasons();

            setUpEpisodes();

            setSeason(1);
            setYear(TVShowDetails.getReleaseYear());

        }
    }

    private void setUpSeasons() {

        for(int i = 0; i< TVShowDetails.getSeasons().size(); i++){
            if(TVShowDetails.getSeasons().get(i).getSeasonNumber()!=0){
                list.add(Integer.toString(TVShowDetails.getSeasons().get(i).getSeasonNumber()));
                years.add(TVShowDetails.getSeasons().get(i).getAirYear());
            }
        }

        SeasonsAdapter seasonsAdapter = new SeasonsAdapter(list, years, this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.season_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(seasonsAdapter);
    }

    private void setUpEpisodes() {
        episodes = new ArrayList<>();

        if(TVShowDetails==null && !isNetworkAvailable()){
            TVShowDetails = RealmUtils.getInstance().readRealmTVShowDetails(TVShowDetails.getId()).getTvShowDetails();
        }
        episodeAdapter = new EpisodeAdapter(episodes, TVShowDetails);
        RecyclerView mEpisodeRecyclerView = (RecyclerView) findViewById(R.id.episode_recycler_view);
        LinearLayoutManager linearVerticalLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mEpisodeRecyclerView.setLayoutManager(linearVerticalLayoutManager);
        mEpisodeRecyclerView.setAdapter(episodeAdapter);
    }


    public void setSeason(final int i){
        if(isNetworkAvailable()){
            ApiHandler.getInstance().requestTVSeriesSeasons(TVShowDetails.getId(), i, new ApiHandler.TvSeriesSeasonListener() {
                @Override
                public void success(SeasonDetails response) {
                    episodes.clear();
                    if(response!=null)
                        if(response.getEpisodes()!=null)
                            if(response.getEpisodes().size()>0){
                                episodes.addAll(response.getEpisodes());
                                RealmUtils.getInstance().createRealmSeasonDetails(TVShowDetails.getId()+""+i);
                                RealmUtils.getInstance().addRealmSeasonsDetailsEpisodes(TVShowDetails.getId()+""+i,episodes);
                            }
                    episodeAdapter.notifyDataSetChanged();
                }
            });
        }else{
            episodes.clear();
            RealmSeasonDetails realmSeasonDetails = RealmUtils.getInstance().readRealmSeasonDetails(TVShowDetails.getId()+""+i);
            if(realmSeasonDetails!=null)
                episodes.addAll(realmSeasonDetails.getEpisodes());
            episodeAdapter.notifyDataSetChanged();
        }
    }

    public void setYear(String year) {
        if(year!=null)
            seasonYear.setText(year);
        else{
            seasonYear.setText("");
        }
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
