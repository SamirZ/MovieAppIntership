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
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

public class ActorMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Movie mMovie;
    private Cast mActor;
    private ImageView mMovieImage;
    private TextView mMovieName;
    private TextView mMovieRoleName;

    public ActorMovieViewHolder(View itemView) {
        super(itemView);
        mMovieImage = (ImageView) itemView.findViewById(R.id.actor_movie_image);
        mMovieName = (TextView) itemView.findViewById(R.id.actor_movie_name);
        mMovieRoleName = (TextView) itemView.findViewById(R.id.actor_movie_role_name);
        itemView.setOnClickListener(this);
    }

    public void bindMovie(Movie movie, Cast actor) {
        mMovie = movie;
        mActor = actor;

        if(isNetworkAvailable()) {
            // Not the best implementation
            final List<Cast> allActors = new ArrayList<>();

            // CALLS MULTIPLE TIMES
            ApiHandler.getInstance().requestMovieCredits(movie.getId(), new ApiHandler.CreditsListener() {
                @Override
                public void success(Credits response) {
                    allActors.addAll(response.cast);
                    for (Cast a : allActors) {
                        if (a.getId() == mActor.getId()) {
                            mMovieRoleName.setText(a.getCharacter());
                        }
                    }

                }
            });
        }else{
            mMovieRoleName.setVisibility(View.GONE);
        }

        if(movie.getTitle()!=null)
        mMovieName.setText(movie.getTitle());

        // from this movie not previous one
        if(movie.getPosterUrl()!=null)
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
