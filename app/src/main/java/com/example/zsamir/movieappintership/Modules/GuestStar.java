package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestStar {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("order")
    @Expose
    public int order;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;

}
