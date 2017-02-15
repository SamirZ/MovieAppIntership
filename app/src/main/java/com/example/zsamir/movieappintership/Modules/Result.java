package com.example.zsamir.movieappintership.Modules;

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

    public TVSeries toTvSeries(){
        if(mediaType.equalsIgnoreCase("tv"))
            return new TVSeries(posterPath,backdropPath,voteAverage,overview,firstAirDate,genreIds,name,id);
        return null;
    }

    @Override
    public String toString() {
        return "Result{" +
                ", id=" + id +
                ", voteAverage=" + voteAverage +
                ", mediaType='" + mediaType + '\'' +
                ", firstAirDate='" + firstAirDate + '\'' +
                ", name='" + name + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Result(String posterPath, Integer id, String overview, String backdropPath, float voteAverage, String mediaType, String firstAirDate, int[] genreIds, Integer voteCount, String name, String releaseDate, String title, String profilePath) {
        this.posterPath = posterPath;
        this.id = id;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.mediaType = mediaType;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.voteCount = voteCount;
        this.name = name;
        this.releaseDate = releaseDate;
        this.title = title;
        this.profilePath = profilePath;
    }
}