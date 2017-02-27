package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable {


    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops = null;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.backdrops);
    }

    public Images() {
    }

    private Images(Parcel in) {
        this.backdrops = in.createTypedArrayList(Backdrop.CREATOR);
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
