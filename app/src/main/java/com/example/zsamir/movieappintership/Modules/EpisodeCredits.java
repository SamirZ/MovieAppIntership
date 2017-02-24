package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeCredits {

    @SerializedName("cast")
    @Expose
    private List<EpisodeCast> cast = null;

    public List<EpisodeCast> getCast() {
        return cast;
    }
}
