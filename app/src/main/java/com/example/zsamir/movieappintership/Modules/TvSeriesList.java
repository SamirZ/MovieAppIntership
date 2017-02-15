package com.example.zsamir.movieappintership.Modules;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVSeriesList implements Parcelable
{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<TVSeries> TVSeries = null;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    public final static Parcelable.Creator<TVSeriesList> CREATOR = new Creator<TVSeriesList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TVSeriesList createFromParcel(Parcel in) {
            TVSeriesList instance = new TVSeriesList();
            instance.page = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.TVSeries, (TVSeries.class.getClassLoader()));
            instance.totalResults = ((int) in.readValue((int.class.getClassLoader())));
            instance.totalPages = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public TVSeriesList[] newArray(int size) {
            return (new TVSeriesList[size]);
        }

    }
            ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TVSeries> getTVSeries() {
        return TVSeries;
    }

    public void setTVSeries(List<TVSeries> results) {
        this.TVSeries = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(TVSeries);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return 0;
    }

}
