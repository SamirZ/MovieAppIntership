package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmUserLists extends RealmObject {

    private RealmList<Movie> favMovies;
    private RealmList<Movie> watchMovies;
    private RealmList<Movie> ratedMovies;

    private RealmList<TVShow> favTVShow;
    private RealmList<TVShow> watchTVShow;
    private RealmList<TVShow> ratedTVShow;

    public RealmList<Movie> getFavMovies() {
        return favMovies;
    }

    public void setFavMovies(RealmList<Movie> favMovies) {
        this.favMovies = favMovies;
    }

    public RealmList<Movie> getWatchMovies() {
        return watchMovies;
    }

    public void setWatchMovies(RealmList<Movie> watchMovies) {
        this.watchMovies = watchMovies;
    }

    public RealmList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(RealmList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public RealmList<TVShow> getFavTVShow() {
        return favTVShow;
    }

    public void setFavTVShow(RealmList<TVShow> favTVShow) {
        this.favTVShow = favTVShow;
    }

    public RealmList<TVShow> getWatchTVShow() {
        return watchTVShow;
    }

    public void setWatchTVShow(RealmList<TVShow> watchTVShow) {
        this.watchTVShow = watchTVShow;
    }

    public RealmList<TVShow> getRatedTVShow() {
        return ratedTVShow;
    }

    public void setRatedTVShow(RealmList<TVShow> ratedTVShow) {
        this.ratedTVShow = ratedTVShow;
    }
}
