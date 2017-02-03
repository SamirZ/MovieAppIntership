package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("name")
    @Expose
    public String name;

}
