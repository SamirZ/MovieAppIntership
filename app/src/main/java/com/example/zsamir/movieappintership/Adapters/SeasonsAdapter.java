package com.example.zsamir.movieappintership.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.SeasonActivity;
import com.example.zsamir.movieappintership.ViewHolders.SeasonsViewHolder;

import java.util.List;

public class SeasonsAdapter  extends RecyclerView.Adapter<SeasonsViewHolder>{

    private List<String> seasonsList;
    private List<String> seasonsYearsList;
    private int selected_position = 0;
    private Context context;

    public SeasonsAdapter(List<String> seasonsList,List<String> seasonsYearsList, Context context){
        this.seasonsList = seasonsList;
        this.seasonsYearsList = seasonsYearsList;
        this.context = context;
    }

    @Override
    public SeasonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item, parent, false);
        return new SeasonsViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final SeasonsViewHolder holder, int position) {
        String seasons = seasonsList.get(position);
        String year = seasonsYearsList.get(position);
        holder.bindSeason(seasons,year);
        if(selected_position == position){
            holder.getNum().setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else{
            holder.getNum().setTextColor(ContextCompat.getColor(context, R.color.colorText));
        }

        if(position==0)
            ((SeasonActivity)context).setYear(holder.getYear());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating old as well as new positions
                notifyItemChanged(selected_position);
                selected_position = holder.getAdapterPosition();
                notifyItemChanged(selected_position);

                if(context instanceof SeasonActivity){
                    ((SeasonActivity)context).setSeason(Integer.parseInt(holder.getSeasonNum()));
                    ((SeasonActivity)context).setYear(holder.getYear());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }



}
