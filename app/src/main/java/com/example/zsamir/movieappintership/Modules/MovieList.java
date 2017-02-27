package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieList {

    @SerializedName("results")
    private List<Movie> movies = null;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public List<Movie> getMovies() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
