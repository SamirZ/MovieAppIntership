package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Backdrop extends RealmObject implements Parcelable
{

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @SerializedName("file_path")
    @Expose
    @PrimaryKey
    private String filePath;

    public Backdrop() {
    }

    public Backdrop(Backdrop b) {
        this.filePath = b.filePath;
    }

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

    public String getBackdropSizeW300(){
        return ImageFormat.BASE_IMG_URL + ImageFormat.BACKDROP_SIZE_W300 + filePath;
    }

    public String getBackdropSizeW780(){
        return ImageFormat.BASE_IMG_URL + ImageFormat.BACKDROP_SIZE_W780 + filePath;
    }

    /*
    public String getBackdropSizeW1280(){
        return BASE_IMG_URL + BACKDROP_SIZE_W1280 + filePath;
    }

    public String getBackdropSizeOriginal(){
        return BASE_IMG_URL + BACKDROP_SIZE_ORIGINAL + filePath;
    }
    */

}
