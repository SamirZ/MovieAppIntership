package com.example.zsamir.movieappintership.Modules;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EpisodeDetails extends RealmObject{

    @SerializedName("name")
    @Expose
    @PrimaryKey
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview = null;

    @SerializedName("vote_average")
    @Expose
    private Float voteAverage = null;

    public EpisodeDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }
}