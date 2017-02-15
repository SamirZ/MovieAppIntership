package com.example.zsamir.movieappintership.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.TvSeriesViewHolder;

import java.util.ArrayList;

public class TvSeriesAdapter extends RecyclerView.Adapter<TvSeriesViewHolder>{
    private ArrayList<TVSeries> mTVSeries;

    public TvSeriesAdapter(ArrayList<TVSeries> mTVSeries) {
        this.mTVSeries = mTVSeries;
    }

    @Override
    public TvSeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_series_item, parent, false);
        return new TvSeriesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TvSeriesViewHolder holder, int position) {
        TVSeries TVSeries = mTVSeries.get(position);
        holder.bindTvSeries(TVSeries);
    }

    @Override
    public int getItemCount() {
        return mTVSeries.size();
    }

}