package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.UserListAdapter;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Favorite;
import com.example.zsamir.movieappintership.LoginModules.PostResponse;
import com.example.zsamir.movieappintership.LoginModules.Watchlist;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;
import com.example.zsamir.movieappintership.Widgets.OnSwipeTouchListener;

import java.util.Locale;

public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView date;
    private TextView rating;
    private TextView delete;
    private ImageView image;
    private Movie movie;
    private TVShow TVShow;
    private Boolean clicked = false;


    public UserListViewHolder(View itemView, final UserListAdapter adapter) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.result_name);
        date = (TextView) itemView.findViewById(R.id.result_date);
        rating = (TextView) itemView.findViewById(R.id.result_rating);
        image = (ImageView) itemView.findViewById(R.id.result_image);
        delete = (TextView) itemView.findViewById(R.id.delete_item);
        delete.setVisibility(View.GONE);

        image.setOnClickListener(this);
        itemView.setOnTouchListener(new OnSwipeTouchListener(itemView.getContext()) {

            public void onSwipeLeft() {
                if(movie!=null){
                    if(!clicked) {
                        delete.setVisibility(View.VISIBLE);
                        image.setVisibility(View.GONE);
                        if(MovieAppApplication.getType().equalsIgnoreCase("RATINGS")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApiHandler.getInstance().deleteRatingMovie(movie.getId(),
                                            MovieAppApplication.getUser().getSessionId(), new ApiHandler.PostResponseListener() {
                                                @Override
                                                public void success(PostResponse response) {
                                                    Log.d("DELETED", String.valueOf(movie.getId()));
                                                    if(MovieAppApplication.getUser().getRatedMovieList().contains(movie.getId()))
                                                        MovieAppApplication.getUser().getRatedMovieList().remove(movie.getId());
                                                    delete.setVisibility(View.GONE);
                                                    image.setVisibility(View.VISIBLE);
                                                    adapter.getMovies().remove(movie);
                                                    adapter.notifyItemRemoved(getAdapterPosition());
                                                }
                                            });
                                }
                            });
                        }else if(MovieAppApplication.getType().equalsIgnoreCase("WATCHLIST")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(MovieAppApplication.getUser().getWatchlistMovieList().contains(movie.getId())){
                                        MovieAppApplication.getUser().removeFromWatchlistMovieList(movie.getId());

                                        ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                                MovieAppApplication.getUser().getSessionId(),
                                                new Watchlist("movie",movie.getId(),false),
                                                new ApiHandler.PostResponseListener() {
                                                    @Override
                                                    public void success(PostResponse response) {
                                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                                        Log.d("RESPONSE", response.statusMessage);
                                                        delete.setVisibility(View.GONE);
                                                        image.setVisibility(View.VISIBLE);
                                                        adapter.getMovies().remove(movie);
                                                        adapter.notifyItemRemoved(getAdapterPosition());
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if(MovieAppApplication.getType().equalsIgnoreCase("FAVORITES")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(MovieAppApplication.getUser().getFavMovieList().contains(movie.getId())){
                                        MovieAppApplication.getUser().removeFromFavoriteMovieList(movie.getId());

                                        ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                                MovieAppApplication.getUser().getSessionId(),
                                                new Favorite("movie",movie.getId(),false),
                                                new ApiHandler.PostResponseListener() {
                                                    @Override
                                                    public void success(PostResponse response) {
                                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                                        Log.d("RESPONSE", response.statusMessage);
                                                        delete.setVisibility(View.GONE);
                                                        image.setVisibility(View.VISIBLE);
                                                        adapter.getMovies().remove(movie);
                                                        adapter.notifyItemRemoved(getAdapterPosition());
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                        clicked = true;
                    }
                    else {
                        delete.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        clicked = false;
                    }
                }
                if(TVShow !=null){
                    if(!clicked) {
                        image.setVisibility(View.GONE);
                        delete.setVisibility(View.VISIBLE);
                        if(MovieAppApplication.getType().equalsIgnoreCase("RATINGS")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApiHandler.getInstance().deleteRatingTVShow(TVShow.getId(),
                                            MovieAppApplication.getUser().getSessionId(), new ApiHandler.PostResponseListener() {
                                                @Override
                                                public void success(PostResponse response) {
                                                    Log.d("DELETED", String.valueOf(TVShow.getId()));
                                                    if(MovieAppApplication.getUser().getRatedTVSeriesList().contains(TVShow.getId()))
                                                        MovieAppApplication.getUser().getRatedTVSeriesList().remove(TVShow.getId());
                                                    delete.setVisibility(View.GONE);
                                                    image.setVisibility(View.VISIBLE);
                                                    adapter.getTVSeries().remove(TVShow);
                                                    adapter.notifyItemRemoved(getAdapterPosition());
                                                }
                                            });
                                }
                            });
                        }else if(MovieAppApplication.getType().equalsIgnoreCase("WATCHLIST")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(MovieAppApplication.getUser().getWatchlistTVSeriesList().contains(TVShow.getId())){
                                        MovieAppApplication.getUser().removeFromWatchlistTVSeriesList(TVShow.getId());

                                        ApiHandler.getInstance().sendToWatchlist(MovieAppApplication.getUser().getId(),
                                                MovieAppApplication.getUser().getSessionId(),
                                                new Watchlist("tv", TVShow.getId(),false),
                                                new ApiHandler.PostResponseListener() {
                                                    @Override
                                                    public void success(PostResponse response) {
                                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                                        Log.d("RESPONSE", response.statusMessage);
                                                        delete.setVisibility(View.GONE);
                                                        image.setVisibility(View.VISIBLE);
                                                        adapter.getTVSeries().remove(TVShow);
                                                        adapter.notifyItemRemoved(getAdapterPosition());
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if(MovieAppApplication.getType().equalsIgnoreCase("FAVORITES")){
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(MovieAppApplication.getUser().getFavTVSeriesList().contains(TVShow.getId())){
                                        MovieAppApplication.getUser().removeFromFavoriteTVSeriesList(TVShow.getId());

                                        ApiHandler.getInstance().sendFavorite(MovieAppApplication.getUser().getId(),
                                                MovieAppApplication.getUser().getSessionId(),
                                                new Favorite("tv", TVShow.getId(),false),
                                                new ApiHandler.PostResponseListener() {
                                                    @Override
                                                    public void success(PostResponse response) {
                                                        Log.d("RESPONSE", String.valueOf(response.statusCode));
                                                        Log.d("RESPONSE", response.statusMessage);
                                                        delete.setVisibility(View.GONE);
                                                        image.setVisibility(View.VISIBLE);
                                                        adapter.getTVSeries().remove(TVShow);
                                                        adapter.notifyItemRemoved(getAdapterPosition());
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                        clicked = true;
                    }
                }
            }

            public void onSwipeRight() {
                delete.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                clicked = false;
            }


        });

    }


    public void bindMovie(Movie movie) {
        this.movie = movie;
        name.setText(movie.getTitle());
        date.setText("("+movie.getReleaseYear()+")");
        rating.setText(String.format(Locale.getDefault(),"%1$.1f",movie.getVoteAverage()));
        rating.append(" /10");
        if(movie.getPosterPath()==null){
            Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
        }else{
            Glide.with(image.getContext()).load(movie.getPosterUrl()).into(image);
        }
    }

    public void bindTVSeries(TVShow TVShow){
        this.TVShow = TVShow;
        name.setText(TVShow.getName());
        date.setText("("+itemView.getContext().getString(R.string.tv_series_name)+" "+ TVShow.getReleaseYear()+")");
        rating.setText(String.format(Locale.getDefault(),"%1$.1f", TVShow.getVoteAverage()));
        rating.append(" /10");
        if(TVShow.getPosterPath()==null){
            Glide.with(image.getContext()).load(R.color.colorBlack).into(image);
        }else{
            Glide.with(image.getContext()).load(TVShow.getPosterUrl()).into(image);
        }
    }

    @Override
    public void onClick(View view) {
        if(movie!=null){
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.putExtra("Movie", movie);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            view.getContext().startActivity(i);
        }else if(TVShow !=null){
            Intent i = new Intent(view.getContext(), TVSeriesDetailsActivity.class);
            i.putExtra("TVSeries", TVShow);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            view.getContext().startActivity(i);
        }
    }
}
