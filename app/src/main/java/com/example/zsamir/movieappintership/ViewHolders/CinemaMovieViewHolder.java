package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Cinema.CinemaMovieActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Modules.ImageFormat;
import com.example.zsamir.movieappintership.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CinemaMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMovieImage;
    private TextView mMovieName;
    private TextView mMovieGenre;
    private TextView mMovieRating;
    private Fragment fragment;

    private CinemaMovie mMovie;

    public CinemaMovieViewHolder(View itemView) {
        super(itemView);
        mMovieImage = (ImageView) itemView.findViewById(R.id.cinema_movie_image);
        mMovieGenre = (TextView) itemView.findViewById(R.id.cinema_movie_genres);
        mMovieName = (TextView) itemView.findViewById(R.id.cinema_movie_name);
        mMovieRating = (TextView) itemView.findViewById(R.id.cinema_movie_rating);
        mMovieImage.setOnClickListener(this);
    }

    public void bindMovie(Fragment fragment, CinemaMovie movie) {
        mMovie = movie;
        this.fragment = fragment;

        if(movie.getName()!=null)
            mMovieName.setText(movie.getName()+" ("+mMovie.getReleaseYear()+")");

        if(mMovie.getGenres()!=null)
            mMovieGenre.setText(mMovie.getGenres());

        if(Double.toString(movie.getRating())!=null)
            mMovieRating.setText(String.format(Locale.getDefault(),"%.1f", movie.getRating()));


        Glide.with(mMovieImage.getContext())
                .load(ImageFormat.BASE_IMG_URL + ImageFormat.BACKDROP_SIZE_W780 + movie.getBackdropPath())
                .centerCrop()
                .into(mMovieImage);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(itemView.getContext(), CinemaMovieActivity.class);
        i.putExtra("MOVIE",mMovie);
        String title = fragment.getArguments().getString("TITLE");
        if(title.equalsIgnoreCase("today")){
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.MONDAY:
                    title = "Monday";
                    break;
                case Calendar.TUESDAY:
                    title = "Tuesday";
                    break;
                case Calendar.WEDNESDAY:
                    title = "Wednesday";
                    break;
                case Calendar.THURSDAY:
                    title = "Thursday";
                    break;
                case Calendar.FRIDAY:
                    title = "Friday";
                    break;
                case Calendar.SATURDAY:
                    title = "Saturday";
                    break;
                case Calendar.SUNDAY:
                    title = "Sunday";
                    break;
            }
        }


        i.putExtra("DAY",title);
        itemView.getContext().startActivity(i);
    }
}
