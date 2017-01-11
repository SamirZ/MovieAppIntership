package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Modules.Genres;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;

import java.util.List;

/**
 * Created by zsami on 31-Dec-16.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMovieImage;
    private TextView mMovieName;
    private TextView mMovieGenre;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;
    private Movie mMovie;

    public MovieViewHolder(View itemView) {
        super(itemView);

        mMovieImage = (ImageView) itemView.findViewById(R.id.movie_image);
        mMovieGenre = (TextView) itemView.findViewById(R.id.movie_genre);
        mMovieReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date);
        mMovieName = (TextView) itemView.findViewById(R.id.movie_name);
        mMovieRating = (TextView) itemView.findViewById(R.id.movie_rating);
        mMovieImage.setOnClickListener(this);
    }

    public void bindMovie(Movie movie) {
        mMovie = movie;

        int[] genreIds = movie.getGenreIds();
        mMovieName.setText(movie.getTitle()+" ("+movie.getReleaseYear()+")");
        List<String> s = movie.getMovieGenres();
        // Genre ids sometimes empty
        if(s.size()>0)
            mMovieGenre.setText(s.get(0));
        else
            mMovieGenre.setText("Not Sorted"); ///!!!!!!
        mMovieReleaseDate.setText(movie.getReleaseDate());
        mMovieRating.setText(Float.toString(movie.getVoteAverage()));
        Glide.with(mMovieImage.getContext()).load(movie.getPosterUrl()).into(mMovieImage);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), mMovie.getTitle(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
        i.putExtra("Movie", mMovie);
        view.getContext().startActivity(i);
    }
}