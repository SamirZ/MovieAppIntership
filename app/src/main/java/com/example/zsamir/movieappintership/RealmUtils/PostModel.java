package com.example.zsamir.movieappintership.RealmUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PostModel extends RealmObject {

    @PrimaryKey
    private int id;
    private boolean isFav;
    private boolean isWatch;
    private String rating;
    private boolean isMovie;
    private boolean isTV;

    @Override
    public String toString() {
        return "ID: "+String.valueOf(id)+", Favorite: "+String.valueOf(isFav)+", Watchlist: "+String.valueOf(isWatch)
                + ", Rating: "+rating+", isMovie: "+String.valueOf(isMovie)+", isTV: "+String.valueOf(isTV);
    }

    public PostModel() {
    }

    public PostModel(int id, boolean isFav, boolean isMove, boolean isTV) {
        this.id = id;
        this.isFav = isFav;
        this.isMovie = isMove;
        this.isTV = isTV;
    }

    public PostModel(int id, boolean isWatch, boolean isMove, boolean isTV, int watchlist) {
        this.id = id;
        this.isWatch = isWatch;
        this.isMovie = isMove;
        this.isTV = isTV;
    }

    public PostModel(int id, String rating, boolean isMove, boolean isTV) {
        this.id = id;
        this.rating = rating;
        this.isMovie = isMove;
        this.isTV = isTV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isWatch() {
        return isWatch;
    }

    public void setWatch(boolean watch) {
        isWatch = watch;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public boolean isTV() {
        return isTV;
    }

    public void setTV(boolean TV) {
        isTV = TV;
    }
}
