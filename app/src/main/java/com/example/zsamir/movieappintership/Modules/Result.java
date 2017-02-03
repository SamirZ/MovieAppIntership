package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("genre_ids")
    @Expose
    public int[] genreIds = new int[0];
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;

    public Movie toMovie(){
        if(mediaType.equalsIgnoreCase("movie"))
            return new Movie(posterPath,overview,releaseDate,genreIds,id,title,backdropPath,voteAverage);
        return null;
    }

    public TvSeries toTvSeries(){
        if(mediaType.equalsIgnoreCase("tv"))
            return new TvSeries(posterPath,backdropPath,voteAverage,overview,firstAirDate,genreIds,name,id);
        return null;
    }

}