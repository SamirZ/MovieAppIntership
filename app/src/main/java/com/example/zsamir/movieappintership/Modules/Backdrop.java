package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backdrop implements Parcelable
{

    @SerializedName("aspect_ratio")
    @Expose
    private float aspectRatio;

    public String getFilePath() {
        return filePath;
    }

    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("width")
    @Expose
    private int width;

    public static final Parcelable.Creator<Backdrop> CREATOR = new Parcelable.Creator<Backdrop>() {
        public Backdrop createFromParcel(Parcel source) {
            return new Backdrop(source);
        }

        public Backdrop[] newArray(int size) {
            return new Backdrop[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filePath);
    }

    protected Backdrop(Parcel in) {
        this.filePath = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    // Backdrop image sizes
    private static final String BACKDROP_SIZE_W300 = "w300";
    private static final String BACKDROP_SIZE_W780 = "w780";
    private static final String BACKDROP_SIZE_W1280 = "w1280";
    private static final String BACKDROP_SIZE_ORIGINAL = "original";

    public String getBackdropSizeW300(){
        return BASE_IMG_URL + BACKDROP_SIZE_W300 + filePath;
    }

    public String getBackdropSizeW780(){
        return BASE_IMG_URL + BACKDROP_SIZE_W780 + filePath;
    }

    public String getBackdropSizeW1280(){
        return BASE_IMG_URL + BACKDROP_SIZE_W1280 + filePath;
    }

    public String getBackdropSizeOriginal(){
        return BASE_IMG_URL + BACKDROP_SIZE_ORIGINAL + filePath;
    }

}
