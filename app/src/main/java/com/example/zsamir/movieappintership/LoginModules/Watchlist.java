package com.example.zsamir.movieappintership.LoginModules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Watchlist {

    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("media_id")
    @Expose
    public Integer mediaId;
    @SerializedName("watchlist")
    @Expose
    public Boolean watchlist;

    public Watchlist(String mediaType, Integer mediaId, Boolean watchlist) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.watchlist = watchlist;
    }
}
