package com.example.zsamir.movieappintership.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.example.zsamir.movieappintership.TVSeries.SeasonActivity;
import com.example.zsamir.movieappintership.ViewHolders.SeasonsViewHolder;

import java.util.List;

public class SeasonsAdapter  extends RecyclerView.Adapter<SeasonsViewHolder>{

    private List<String> seasonsList;
    private List<String> seasonsYearsList;
    private int selected_position = 0;
    private TVShowDetails tvShowDetails;
    private Context context;

    public SeasonsAdapter(List<String> seasonsList, List<String> seasonsYearsList, Context context, TVShowDetails tvShowDetails){
        this.seasonsList = seasonsList;
        this.seasonsYearsList = seasonsYearsList;
        this.context = context;
        this.tvShowDetails = tvShowDetails;
    }

    @Override
    public SeasonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item, parent, false);
        return new SeasonsViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final SeasonsViewHolder holder, int position) {
        final String seasons = seasonsList.get(position);
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
                if(isNetworkAvailable() || RealmUtils.getInstance().readRealmSeasonDetails(tvShowDetails.getId()+holder.getSeasonNum())!=null) {
                    notifyItemChanged(selected_position);
                    selected_position = holder.getAdapterPosition();
                    notifyItemChanged(selected_position);

                    if (context instanceof SeasonActivity) {
                        ((SeasonActivity) context).setSeason(Integer.parseInt(holder.getSeasonNum()));
                        ((SeasonActivity) context).setYear(holder.getYear());
                    }
                }else{
                    showNoDataDialog();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

    private void showNoDataDialog(){
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                .setTitle(context.getString(R.string.connection_warrning))
                .setMessage(context.getString(R.string.connection_required) + "\n" + "\n" + "\n" + "\n" + "\n")
                .setPositiveButton(R.string.connect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with login
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // set pref to no
                    }
                })
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorText));

        //Delete user from shared preferences
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
