package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.CreatedBy;
import com.example.zsamir.movieappintership.Modules.Season;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmTVShowDetails extends RealmObject {

    @PrimaryKey
    private int id;
    private String lastAirDate;
    private boolean inProduction;

    private CreatedBy director;

    private TVShowDetails tvShowDetails;

    private RealmList<CreatedBy> writers;
    private RealmList<Cast> actors;
    private RealmList<Backdrop> backdrops;
    private RealmList<Season> seasons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreatedBy getCreatedBy() {
        return director;
    }

    public void setCreatedBy(CreatedBy director) {
        this.director = director;
    }

    public RealmList<CreatedBy> getWriters() {
        return writers;
    }

    public void setWriters(RealmList<CreatedBy> writers) {
        this.writers = writers;
    }

    public RealmList<Cast> getActors() {
        return actors;
    }

    public void setActors(RealmList<Cast> actors) {
        this.actors = actors;
    }

    public RealmList<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(RealmList<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public RealmList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(RealmList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public CreatedBy getDirector() {
        return director;
    }

    public void setDirector(CreatedBy director) {
        this.director = director;
    }

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }

    public void setTvShowDetails(TVShowDetails tvShowDetails) {
        this.tvShowDetails = tvShowDetails;
    }

    public String getFinishYear() {
        if(!inProduction){
            if(lastAirDate!=null) {
                String[] s = lastAirDate.split("-");
                if (s.length > 0) {
                    return s[0];
                } else {
                    return null;
                }
            }return null;
        }
        else
            return null;
    }

}
