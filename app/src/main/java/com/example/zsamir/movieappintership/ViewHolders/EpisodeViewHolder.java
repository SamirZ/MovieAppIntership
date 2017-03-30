package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.example.zsamir.movieappintership.TVSeries.EpisodeActivity;

import java.util.Locale;

public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView rating;
    private TextView airdate;
    private Episode episode;
    private TVShowDetails TVShowDetails;

    public EpisodeViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.episode_name);
        rating = (TextView) itemView.findViewById(R.id.episode_rating);
        airdate = (TextView) itemView.findViewById(R.id.episode_airdate);

        itemView.setOnClickListener(this);
    }

    public void bindEpisode(Episode episode, TVShowDetails TVShowDetails) {

        if(TVShowDetails !=null){
            this.TVShowDetails = TVShowDetails;
        }

        if(episode!=null){
            this.episode = episode;

            if(episode.getEpisodeNumber()>=0 && episode.getName()!=null)
                name.setText(episode.getEpisodeNumber()+". "+episode.getName());
            if(episode.getVoteAverage()>=0) {
                rating.setText(String.format(Locale.getDefault(), "%.1f", episode.getVoteAverage()));
                rating.append(" |");
            }
            if(episode.getAirDate()!=null)
                if(episode.getAirDate().length()>1)
                    airdate.setText(episode.getAirDate());
        }
    }

    @Override
    public void onClick(View v) {
        if(isNetworkAvailable() || RealmUtils.getInstance().readEpisodeDetails(episode.getName())!=null) {
            Intent i = new Intent(v.getContext(), EpisodeActivity.class);
            i.putExtra("EpisodeDetails", episode);
            i.putExtra("TVSeriesDetails", TVShowDetails);
            if (!episode.getAirDate().equalsIgnoreCase("TBD")) {
                v.getContext().startActivity(i);
            } else {
                Toast.makeText(v.getContext(), "No details!", Toast.LENGTH_SHORT).show();
            }
        }else{
            showNoDataDialog();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoDataDialog(){
        AlertDialog dialog = new AlertDialog.Builder(itemView.getContext(), R.style.MyDialogTheme)
                .setTitle(itemView.getContext().getString(R.string.connection_warrning))
                .setMessage(itemView.getContext().getString(R.string.connection_required) + "\n" + "\n" + "\n" + "\n" + "\n")
                .setPositiveButton(R.string.connect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with login
                        itemView.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // set pref to no
                    }
                })
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorText));

        //Delete user from shared preferences
    }
}
