package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeCredits {

    @SerializedName("cast")
    @Expose
    public List<EpisodeCast> cast = null;

}
