package com.example.zsamir.movieappintership.Modules;

/**
 * Created by zsami on 18-Jan-17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonDetails {

    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episodes")
    @Expose
    public List<Episode> episodes;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("season_number")
    @Expose
    public Integer seasonNumber;

}