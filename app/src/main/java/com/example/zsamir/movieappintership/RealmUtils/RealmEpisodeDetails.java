package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.EpisodeDetails;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmEpisodeDetails extends RealmObject {

    @PrimaryKey
    private int id;
    private int seasonNumber;
    private int episodeNumber;

    private EpisodeDetails episodeDetails;
    private RealmList<Cast> episodeCast;

    public RealmEpisodeDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public EpisodeDetails getEpisodeDetails() {
        return episodeDetails;
    }

    public void setEpisodeDetails(EpisodeDetails episodeDetails) {
        this.episodeDetails = episodeDetails;
    }

    public RealmList<Cast> getEpisodeCast() {
        return episodeCast;
    }

    public void setEpisodeCast(RealmList<Cast> episodeCast) {
        this.episodeCast = episodeCast;
    }

}
