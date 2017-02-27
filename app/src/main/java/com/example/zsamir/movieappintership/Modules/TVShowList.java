package com.example.zsamir.movieappintership.Modules;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShowList implements Parcelable
{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<TVShow> TVShow = null;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    public final static Parcelable.Creator<TVShowList> CREATOR = new Creator<TVShowList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TVShowList createFromParcel(Parcel in) {
            TVShowList instance = new TVShowList();
            instance.page = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.TVShow, (TVShow.class.getClassLoader()));
            instance.totalResults = ((int) in.readValue((int.class.getClassLoader())));
            instance.totalPages = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public TVShowList[] newArray(int size) {
            return (new TVShowList[size]);
        }

    }
            ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TVShow> getTVShow() {
        return TVShow;
    }

    public void setTVShow(List<TVShow> results) {
        this.TVShow = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(TVShow);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return 0;
    }

}
