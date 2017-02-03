package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.EpisodeActivity;

public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView rating;
    private TextView airdate;
    private Episode episode;
    private TvSeriesDetails tvSeriesDetails;

    public EpisodeViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.episode_name);
        rating = (TextView) itemView.findViewById(R.id.episode_rating);
        airdate = (TextView) itemView.findViewById(R.id.episode_airdate);

        itemView.setOnClickListener(this);
    }

    public void bindEpisode(Episode episode, TvSeriesDetails tvSeriesDetails) {

        if(tvSeriesDetails!=null){
            this.tvSeriesDetails = tvSeriesDetails;
        }

        if(episode!=null){
            this.episode = episode;
        }

        if(episode.getEpisodeNumber()>=0 && episode.getName()!=null)
            name.setText(episode.getEpisodeNumber()+". "+episode.getName());
        if(episode.getVoteAverage()>=0)
            rating.setText(String.format("%.1f", episode.getVoteAverage())+" |");
        if(episode.getAirDate()!=null)
            if(episode.getAirDate().length()>1)
            airdate.setText(episode.getAirDate());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(v.getContext(), EpisodeActivity.class);
        i.putExtra("EpisodeDetails",episode);
        i.putExtra("TVSeriesDetails",tvSeriesDetails);
        if(!episode.getAirDate().equalsIgnoreCase("TBD")){
            v.getContext().startActivity(i);
        }else{
            Toast.makeText(v.getContext(), "No details!", Toast.LENGTH_SHORT).show();
        }
    }
}
