package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;

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

        Glide.with(mMovieImage.getContext()).load(movie.getPosterUrl()).centerCrop().into(mMovieImage);
    }



    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), mMovie.getTitle(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
        i.putExtra("Movie", mMovie);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(i);
    }
}