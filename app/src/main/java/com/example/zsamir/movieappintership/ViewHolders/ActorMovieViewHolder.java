package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

public class ActorMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Movie mMovie;
    private Cast mActor;
    private Credits mCredits;
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

    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Movie", mMovie);
        view.getContext().startActivity(i);
    }

    public void bindMovie(Movie movie, Cast actor) {
        mMovie = movie;
        mActor = actor;

        // Not the best implementation
        final List<Cast> allActors = new ArrayList<>();
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.requestMovieCredits(movie.getId(), new ApiHandler.CreditsListener() {
            @Override
            public void success(Credits response) {
                allActors.addAll(response.cast);
                for (Cast a:allActors) {
                    if(a.id==mActor.id){
                        mMovieRoleName.setText(a.character);
                    }
                }

            }
        });
        if(movie.getTitle()!=null)
        mMovieName.setText(movie.getTitle());
        // from this movie not previous one
        if(movie.getPosterUrl()!=null)
        Glide.with(mMovieImage.getContext()).load(movie.getPosterUrl()).into(mMovieImage);

    }
}
