package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestStar implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("order")
    @Expose
    public int order;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.creditId);
        dest.writeString(this.character);
        dest.writeInt(this.order);
        dest.writeString(this.profilePath);
    }

    public GuestStar() {
    }

    protected GuestStar(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.creditId = in.readString();
        this.character = in.readString();
        this.order = in.readInt();
        this.profilePath = in.readString();
    }

    public static final Parcelable.Creator<GuestStar> CREATOR = new Parcelable.Creator<GuestStar>() {
        @Override
        public GuestStar createFromParcel(Parcel source) {
            return new GuestStar(source);
        }

        @Override
        public GuestStar[] newArray(int size) {
            return new GuestStar[size];
        }
    };
}
