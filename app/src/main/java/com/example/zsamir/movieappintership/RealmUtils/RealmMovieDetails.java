package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.MovieReview;
import com.example.zsamir.movieappintership.Modules.ProductionCountry;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovieDetails extends RealmObject {

    @PrimaryKey
    private int id;

    private Crew director;
    private ProductionCountry productionCountry;

    private RealmList<Crew> writers;
    private RealmList<Cast> actors;
    private RealmList<MovieReview> movieReviews;
    private RealmList<Backdrop> backdrops;

    public RealmMovieDetails() {
    }

    public int getId() {
        return id;
    }

    public Crew getDirector() {
        return director;
    }

    public ProductionCountry getProductionCountry() {
        return productionCountry;
    }

    public RealmList<Crew> getWriters() {
        return writers;
    }

    public RealmList<Cast> getActors() {
        return actors;
    }

    public RealmList<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public RealmList<Backdrop> getBackdrops() {
        return backdrops;
    }




    public void setId(int id) {
        this.id = id;
    }

    public void setDirector(Crew director) {
        this.director = director;
    }

    public void setProductionCountry(ProductionCountry productionCountry) {
        this.productionCountry = productionCountry;
    }

    public void setWriters(RealmList<Crew> writers) {
        this.writers = writers;
    }

    public void setActors(RealmList<Cast> actors) {
        this.actors = actors;
    }

    public void setMovieReviews(RealmList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public void setBackdrops(RealmList<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

}
