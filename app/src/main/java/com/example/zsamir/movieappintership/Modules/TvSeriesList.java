package com.example.zsamir.movieappintership.Modules;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvSeriesList implements Parcelable
{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<TvSeries> tvSeries = null;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    public final static Parcelable.Creator<TvSeriesList> CREATOR = new Creator<TvSeriesList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TvSeriesList createFromParcel(Parcel in) {
            TvSeriesList instance = new TvSeriesList();
            instance.page = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.tvSeries, (TvSeries.class.getClassLoader()));
            instance.totalResults = ((int) in.readValue((int.class.getClassLoader())));
            instance.totalPages = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public TvSeriesList[] newArray(int size) {
            return (new TvSeriesList[size]);
        }

    }
            ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TvSeries> getTvSeries() {
        return tvSeries;
    }

    public void setTvSeries(List<TvSeries> results) {
        this.tvSeries = results;
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
        dest.writeList(tvSeries);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return 0;
    }

}
