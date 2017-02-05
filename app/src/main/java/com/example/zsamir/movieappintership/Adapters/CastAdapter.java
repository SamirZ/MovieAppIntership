package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.EpisodeCast;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.CastViewHolder;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    ArrayList<Cast> mCast;
    ArrayList<EpisodeCast> mEpisodeCast;

    public CastAdapter(ArrayList<Cast> mCast) {
        this.mCast = mCast;
    }

    public CastAdapter(ArrayList<EpisodeCast> mEpisodeCast,int i) {
        this.mEpisodeCast = mEpisodeCast;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new CastViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        if(mCast!=null){
            Cast cast = mCast.get(position);
            holder.bindCast(cast);
        }

        if(mEpisodeCast!=null){
            EpisodeCast cast = mEpisodeCast.get(position);
            holder.bindCast(cast);
        }
    }

    @Override
    public int getItemCount() {
        if(mCast!=null)
            return mCast.size();
        else if(mEpisodeCast!=null)
            return mEpisodeCast.size();
        return 0;
    }
}
