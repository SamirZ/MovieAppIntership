package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonDetails {

    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;

    public List<Episode> getEpisodes() {
        return episodes;
    }
}