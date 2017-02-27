package com.example.zsamir.movieappintership.Modules;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeDetails {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview = null;

    @SerializedName("vote_average")
    @Expose
    private Float voteAverage = null;

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
}