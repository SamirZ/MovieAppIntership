package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

}