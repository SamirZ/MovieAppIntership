package com.example.zsamir.movieappintership.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.TvSeriesViewHolder;

import java.util.ArrayList;

public class TvSeriesAdapter extends RecyclerView.Adapter<TvSeriesViewHolder>{
    ArrayList<TvSeries> mTvSeries;

    public TvSeriesAdapter(ArrayList<TvSeries> mTvSeries) {
        this.mTvSeries = mTvSeries;
    }

    @Override
    public TvSeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_series_item, parent, false);
        return new TvSeriesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TvSeriesViewHolder holder, int position) {
        TvSeries tvSeries = mTvSeries.get(position);
        holder.bindTvSeries(tvSeries);
    }

    @Override
    public int getItemCount() {
        return mTvSeries.size();
    }

    public void addItem(TvSeries tvSeries) {
        mTvSeries.add(0, tvSeries);
        notifyItemInserted(0);
    }
}