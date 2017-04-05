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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;

import java.util.Locale;

public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView date;
    private TextView rating;
    private ImageView image;
    private Result media;

    public ResultViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.result_name);
        date = (TextView) itemView.findViewById(R.id.result_date);
        rating = (TextView) itemView.findViewById(R.id.result_rating);
        image = (ImageView) itemView.findViewById(R.id.result_image);

        itemView.setOnClickListener(this);

    }

    public void bindResult(Result result) {

        media = result;
        if(result.getMediaType().equalsIgnoreCase("movie")){
            name.setText(result.toMovie().getTitle());
            if(result.toMovie().getReleaseYear().length()>0)
                date.setText("("+result.toMovie().getReleaseYear()+")");
            else{
                date.setVisibility(View.GONE);
            }
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",result.toMovie().getVoteAverage()));
            rating.append(rating.getContext().getString(R.string.max_rating));
            if(result.toMovie().getPosterPath()==null){
                Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
            }else{
                Glide.with(image.getContext()).load(result.toMovie().getPosterUrl()).into(image);
            }
        }
        if(result.getMediaType().equalsIgnoreCase("tv")){
            name.setText(result.toTvSeries().getName());
            if(result.toTvSeries().getReleaseYear().length()>0)
                date.setText("("+itemView.getContext().getString(R.string.tv_series_name)+" "+result.toTvSeries().getReleaseYear()+")");
            else{
                date.setVisibility(View.GONE);
            }
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",result.toTvSeries().getVoteAverage()));
            rating.append(rating.getContext().getString(R.string.max_rating));
            if(result.toTvSeries().getPosterPath()==null){
                Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
            }else{
                Glide.with(image.getContext()).load(result.toTvSeries().getPosterUrl()).into(image);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(isNetworkAvailable()) {
            if (media.getMediaType().equalsIgnoreCase("movie")) {
                Intent i = new Intent(v.getContext(), MovieDetailsActivity.class);
                i.putExtra("Movie", media.toMovie());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(i);
            }
            if (media.getMediaType().equalsIgnoreCase("tv")) {
                Intent i = new Intent(v.getContext(), TVSeriesDetailsActivity.class);
                i.putExtra("TVSeries", media.toTvSeries());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(i);
            }
        }else{

        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showNoDataDialog(){
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
