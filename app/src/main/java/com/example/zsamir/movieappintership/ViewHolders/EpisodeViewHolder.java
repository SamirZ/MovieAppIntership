package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.R;

public class EpisodeViewHolder extends RecyclerView.ViewHolder{

    private TextView name;
    private TextView rating;
    private TextView airdate;

    public EpisodeViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.episode_name);
        rating = (TextView) itemView.findViewById(R.id.episode_rating);
        airdate = (TextView) itemView.findViewById(R.id.episode_airdate);

    }

    public void bindEpisode(Episode episode) {
        if(episode.getEpisodeNumber()>=0 && episode.getName()!=null)
            name.setText(episode.getEpisodeNumber()+". "+episode.getName());
        if(episode.getVoteAverage()>=0)
            rating.setText(String.format("%.1f", episode.getVoteAverage())+" |");
        if(episode.getAirDate()!=null && episode.getAirDate().length()>1)
            airdate.setText(episode.getAirDate());
    }
}
