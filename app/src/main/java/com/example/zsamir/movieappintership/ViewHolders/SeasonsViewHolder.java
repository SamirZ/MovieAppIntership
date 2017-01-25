package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.Adapters.SeasonsAdapter;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.SeasonActivity;

public class SeasonsViewHolder extends RecyclerView.ViewHolder{

    private TextView num;
    private String seasonNum;
    private String year;

    public TextView getNum() {
        return num;
    }

    public String getYear() {
        return year;
    }

    public String getSeasonNum() {
        return seasonNum;
    }

    public SeasonsViewHolder(View itemView) {
        super(itemView);
        num = (TextView)itemView.findViewById(R.id.season_name);
    }

    public void bindSeason(String seasons, String year, Context context) {
        seasonNum = seasons;
        this.year = year;
        num.setText(seasonNum);
    }

}
