package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.EpisodeViewHolder;

import java.util.List;

/**
 * Created by zsami on 19-Jan-17.
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

    List<Episode> episodeList;

    public EpisodeAdapter(List<Episode> episodeList){
        this.episodeList = episodeList;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent, false);
        return new EpisodeViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.bindEpisode(episode);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }
}
