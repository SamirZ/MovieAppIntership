package com.example.zsamir.movieappintership.LoginModules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("media_id")
    @Expose
    public Integer mediaId;
    @SerializedName("favorite")
    @Expose
    public Boolean favorite;

    public Favorite(String mediaType, Integer mediaId, Boolean favorite) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.favorite = favorite;
    }
}
