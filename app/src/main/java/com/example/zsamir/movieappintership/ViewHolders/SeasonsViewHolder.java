package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.zsamir.movieappintership.R;

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

    public void bindSeason(String seasons, String year) {
        seasonNum = seasons;
        this.year = year;
        num.setText(seasonNum);
    }

}
