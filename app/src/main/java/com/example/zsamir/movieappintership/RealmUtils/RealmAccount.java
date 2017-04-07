package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmAccount extends RealmObject {

    private RealmList<RealmInteger> favMovieList;
    private RealmList<RealmInteger> favTVSeriesList;

    private RealmList<RealmInteger> ratedMovieList;
    private RealmList<RealmInteger> ratedTVSeriesList;

    private RealmList<RealmInteger> watchlistMovieList;
    private RealmList<RealmInteger> watchlistTVSeriesList;

    public RealmAccount() {
    }

    public RealmList<RealmInteger> getFavMovieList() {
        return favMovieList;
    }

    public RealmList<RealmInteger> getFavTVSeriesList() {
        return favTVSeriesList;
    }

    public RealmList<RealmInteger> getRatedMovieList() {
        return ratedMovieList;
    }

    public RealmList<RealmInteger> getRatedTVSeriesList() {
        return ratedTVSeriesList;
    }

    public RealmList<RealmInteger> getWatchlistMovieList() {
        return watchlistMovieList;
    }

    public RealmList<RealmInteger> getWatchlistTVSeriesList() {
        return watchlistTVSeriesList;
    }

    public List<Integer> getFavMovieListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: favMovieList) {
            l.add(i.getI());
        }
        return l;
    }

    public List<Integer> getWatchMovieListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: watchlistMovieList) {
            l.add(i.getI());
        }
        return l;
    }

    public List<Integer> getRatedMovieListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: ratedMovieList) {
            l.add(i.getI());
        }
        return l;
    }

    public List<Integer> getFavTVShowListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: favTVSeriesList) {
            l.add(i.getI());
        }
        return l;
    }

    public List<Integer> getWatchTVShowListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: watchlistTVSeriesList) {
            l.add(i.getI());
        }
        return l;
    }

    public List<Integer> getRatedTVShowListInteger() {
        List<Integer> l = new ArrayList<>();
        for (RealmInteger i: ratedTVSeriesList) {
            l.add(i.getI());
        }
        return l;
    }
}
