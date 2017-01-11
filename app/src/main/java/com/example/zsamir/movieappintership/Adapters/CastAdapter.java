package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.CastViewHolder;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    ArrayList<Cast> mCast;

    public CastAdapter(ArrayList<Cast> mCast) {
        this.mCast = mCast;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new CastViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Cast cast = mCast.get(position);
        holder.bindCast(cast);
    }

    @Override
    public int getItemCount() {
        return mCast.size();
    }
}
