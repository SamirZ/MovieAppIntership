package com.example.zsamir.movieappintership.Modules;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorImages {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("profiles")
    @Expose
    public List<Profile> profiles = null;

}