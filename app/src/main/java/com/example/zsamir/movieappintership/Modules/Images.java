package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("id")
    @Expose
    public int id;

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public int getId() {
        return id;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    @SerializedName("backdrops")
    @Expose
    public List<Backdrop> backdrops = null;
    @SerializedName("posters")
    @Expose
    public List<Poster> posters = null;

}
