package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public List<Video> videos = null;

}