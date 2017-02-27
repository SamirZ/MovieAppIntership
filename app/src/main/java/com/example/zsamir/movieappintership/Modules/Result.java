package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genre_ids")
    @Expose
    private int[] genreIds = new int[0];
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("title")
    @Expose
    private String title;

    public Movie toMovie(){
        if(mediaType.equalsIgnoreCase("movie"))
            return new Movie(posterPath,overview,releaseDate,genreIds,id,title,backdropPath,voteAverage);
        return null;
    }

    public TVShow toTvSeries(){
        if(mediaType.equalsIgnoreCase("tv"))
            return new TVShow(posterPath,backdropPath,voteAverage,overview,firstAirDate,genreIds,name,id);
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

    public Result(String posterPath, Integer id, String overview, String backdropPath, float voteAverage, String mediaType, String firstAirDate, int[] genreIds, String name, String releaseDate, String title) {
        this.posterPath = posterPath;
        this.id = id;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.mediaType = mediaType;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.name = name;
        this.releaseDate = releaseDate;
        this.title = title;
    }

    public String getMediaType() {
        return mediaType;
    }
}