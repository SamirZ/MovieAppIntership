package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("aspect_ratio")
    @Expose
    public float aspectRatio;
    @SerializedName("file_path")
    @Expose
    public String filePath;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("width")
    @Expose
    public int width;

}
