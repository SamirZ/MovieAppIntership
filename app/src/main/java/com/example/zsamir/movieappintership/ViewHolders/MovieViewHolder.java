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
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.List;
import java.util.Locale;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMovieImage;
    private TextView mMovieName;
    private TextView mMovieGenre;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;

    private Movie mMovie;

    private ImageView mMovieImageFav;
    private ImageView mMovieImageWatch;

    public MovieViewHolder(View itemView) {
        super(itemView);

        mMovieImage = (ImageView) itemView.findViewById(R.id.movie_image);
        mMovieGenre = (TextView) itemView.findViewById(R.id.movie_genre);
        mMovieReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date);
        mMovieName = (TextView) itemView.findViewById(R.id.movie_name);
        mMovieRating = (TextView) itemView.findViewById(R.id.movie_rating);
        mMovieImageFav = (ImageView) itemView.findViewById(R.id.movie_favorites_image);
        mMovieImageWatch = (ImageView) itemView.findViewById(R.id.movie_bookmark_image);
        mMovieImage.setOnClickListener(this);
    }

    public void bindMovie(Movie movie) {
        mMovie = movie;


        if(movie.getTitle()!=null && movie.getReleaseYear()!=null)
        mMovieName.setText(movie.getTitle()+" ("+movie.getReleaseYear()+")");

        List<String> s = movie.getMovieGenres();
        // Genre ids sometimes empty
        if(s.size()>0)
            mMovieGenre.setText(s.get(0));
        else
            mMovieGenre.setText(mMovieGenre.getContext().getString(R.string.not_sorted)); ///!!!!!!
        if(movie.getReleaseDate()!=null)
            mMovieReleaseDate.setText(movie.getReleaseDate());
        if(Float.toString(movie.getVoteAverage())!=null)
            mMovieRating.setText(String.format(Locale.getDefault(),"%.1f", movie.getVoteAverage()));

        if(MovieAppApplication.isUserLoggedIn()){

            mMovieImageFav.setVisibility(View.VISIBLE);
            mMovieImageWatch.setVisibility(View.VISIBLE);

            if(MovieAppApplication.getUser().getFavMovieList()!=null){
                if(MovieAppApplication.getUser().getFavMovieList().contains(mMovie.getId())){
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mMovieImageFav.getContext(),R.drawable.like_filled_2));
                    mMovieImageFav.setBackground(likeIcon);
                }else{
                    Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mMovieImageFav.getContext(),R.drawable.like_2));
                    mMovieImageFav.setBackground(likeIcon);
                }
            }

            if(MovieAppApplication.getUser().getWatchlistMovieList()!=null){
                if(MovieAppApplication.getUser().getWatchlistMovieList().contains(mMovie.getId())){
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mMovieImageWatch.getContext(),R.drawable.bookmark_filled_2));
                    mMovieImageWatch.setBackground(bookmarkIcon);
                }else{
                    Drawable bookmarkIcon = DrawableCompat.wrap(ContextCompat.getDrawable(mMovieImageWatch.getContext(),R.drawable.bookmark));
                    mMovieImageWatch.setBackground(bookmarkIcon);
                }
            }
        }else{
            mMovieImageFav.setVisibility(View.GONE);
            mMovieImageWatch.setVisibility(View.GONE);
        }

        Glide.with(mMovieImage.getContext()).load(movie.getPosterUrl()).into(mMovieImage);
    }



    @Override
    public void onClick(View view) {
        if(isNetworkAvailable() || RealmUtils.getInstance().readRealmMovieDetails(mMovie.getId())!=null){
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("Movie", mMovie);
            view.getContext().startActivity(i);
        }else {
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