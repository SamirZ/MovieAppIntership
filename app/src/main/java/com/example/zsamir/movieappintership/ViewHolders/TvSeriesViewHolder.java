package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;

import java.util.Locale;

public class TvSeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mTvSeriesImage;
    private TextView mTvSeriesName;
    private TextView mTvSeriesGenre;
    private TextView mTvSeriesReleaseDate;
    private TextView mTvSeriesRating;

    private TVShow tvShow;

    private ImageView mTVSeriesImageFav;
    private ImageView mTVSeriesImageWatch;

    public TvSeriesViewHolder(View itemView) {
        super(itemView);

        mTvSeriesImage = (ImageView) itemView.findViewById(R.id.tv_series_image);
        mTvSeriesGenre = (TextView) itemView.findViewById(R.id.tv_series_genre);
        mTvSeriesReleaseDate = (TextView) itemView.findViewById(R.id.tv_series_release_date);
        mTvSeriesName = (TextView) itemView.findViewById(R.id.tv_series_name);
        mTvSeriesRating = (TextView) itemView.findViewById(R.id.tv_series_rating);
        mTVSeriesImageFav = (ImageView) itemView.findViewById(R.id.tv_series_favorites_image);
        mTVSeriesImageWatch = (ImageView) itemView.findViewById(R.id.tv_series_bookmark_image);
        mTvSeriesImage.setOnClickListener(this);
    }

    public void bindTvSeries(TVShow TVShow) {
        this.tvShow = TVShow;

        if(TVShow.getName()!=null)
        mTvSeriesName.setText(TVShow.getName());

        if(TVShow.getTvSeriesGenres().size()>0){
            mTvSeriesGenre.setText(TVShow.getTvSeriesGenres().get(0));
        }
        else
            mTvSeriesGenre.setText(" ");


        // no fin date!
        if(TVShow.getReleaseYear()!=null)
            mTvSeriesReleaseDate.setText("(TV Series"+" "+ TVShow.getReleaseYear()+""+")");
        if(String.format(Locale.getDefault(),"%1$.1f", TVShow.getVoteAverage())!=null)
            mTvSeriesRating.setText(String.format(Locale.getDefault(),"%1$.1f", TVShow.getVoteAverage()));

        if(MovieAppApplication.isUserLoggedIn()){


            mTVSeriesImageFav.setVisibility(View.VISIBLE);
            mTVSeriesImageWatch.setVisibility(View.VISIBLE);

            if(MovieAppApplication.getUser().getFavTVSeriesList()!=null){
                if(MovieAppApplication.getUser().getFavTVSeriesList().contains(TVShow.getId())){
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageFav.getContext(),R.drawable.like_filled_2));
                    mTVSeriesImageFav.setBackground(likeIcon);

                } else{
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageFav.getContext(),R.drawable.like_2));
                    mTVSeriesImageFav.setBackground(likeIcon);
                }
            }

            if(MovieAppApplication.getUser().getWatchlistTVSeriesList()!=null){
                if(MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(TVShow.getId())){
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageWatch.getContext(),R.drawable.bookmark_filled_2));
                    mTVSeriesImageWatch.setBackground(bookmarkIcon);
                }else{
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mTVSeriesImageWatch.getContext(),R.drawable.bookmark));
                    mTVSeriesImageWatch.setBackground(bookmarkIcon);
                }
            }
        }else{
            mTVSeriesImageFav.setVisibility(View.GONE);
            mTVSeriesImageWatch.setVisibility(View.GONE);
        }

        Glide.with(mTvSeriesImage.getContext()).load(TVShow.getPosterUrl()).into(mTvSeriesImage);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), TVSeries.getName(), Toast.LENGTH_SHORT).show();
        if(isNetworkAvailable() || RealmUtils.getInstance().readRealmTVShowDetails(tvShow.getId())!=null){
            Intent i = new Intent(view.getContext(), TVSeriesDetailsActivity.class);
            i.putExtra("TVSeries", tvShow);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            view.getContext().startActivity(i);
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
