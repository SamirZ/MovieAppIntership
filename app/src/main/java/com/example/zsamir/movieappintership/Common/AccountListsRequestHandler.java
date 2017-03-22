package com.example.zsamir.movieappintership.Common;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import java.util.ArrayList;

public class AccountListsRequestHandler {

    private static AccountListsRequestHandler instance = null;

    private AccountListsRequestHandler() {
    }

    public static AccountListsRequestHandler getInstance() {
        if(instance == null) {
            instance = new AccountListsRequestHandler();
        }
        return instance;
    }

    public void requestFavoriteMovies(){
        ApiHandler.getInstance().requestAccountFavoriteMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountFavMovies(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountFavMovies(l);

                            }
                        });
                    }
                }
            }
        });
    }

    public void requestFavoriteTVSeries(){
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVShowList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountFavTVShow(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountFavTVShow(l);
                            }
                        });
                    }
                }
            }
        });
    }

    public void requestWatchlistMovies(){
        ApiHandler.getInstance().requestAccountWatchlistMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountWatchlistMovies(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistMovies(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountWatchlistMovies(l);
                            }
                        });
                    }
                }
            }
        });
    }

    public void requestWatchlistTVSeries(){
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVShowList response) {
                ArrayList<Integer> l = new ArrayList<>();
                for (TVShow t: response.getTVShow()) {
                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                    l.add(t.getId());
                }
                RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(MovieAppApplication.getUser().getId(), MovieAppApplication.getUser().getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVShowList response) {
                                ArrayList<Integer> l = new ArrayList<>();
                                for (TVShow t: response.getTVShow()) {
                                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                                    l.add(t.getId());
                                }
                                RealmUtils.getInstance().addRealmAccountWatchlistTVShow(l);
                            }
                        });
                    }
                }

            }
        });
    }
}
