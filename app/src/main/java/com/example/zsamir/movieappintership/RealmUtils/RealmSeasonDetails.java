package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Episode;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmSeasonDetails extends RealmObject{

    @PrimaryKey
    private String id;
    private RealmList<Episode> episodes;

    public RealmSeasonDetails() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public RealmList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(RealmList<Episode> episodes) {
        this.episodes = episodes;
    }
}
