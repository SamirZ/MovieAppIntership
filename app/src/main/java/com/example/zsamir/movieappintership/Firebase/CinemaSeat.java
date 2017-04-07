package com.example.zsamir.movieappintership.Firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class CinemaSeat implements Parcelable {

    private String id;
    private boolean free;

    public CinemaSeat() {
    }

    public CinemaSeat(String id, boolean free) {
        this.id = id;
        this.free = free;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeByte(this.free ? (byte) 1 : (byte) 0);
    }

    protected CinemaSeat(Parcel in) {
        this.id = in.readString();
        this.free = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CinemaSeat> CREATOR = new Parcelable.Creator<CinemaSeat>() {
        @Override
        public CinemaSeat createFromParcel(Parcel source) {
            return new CinemaSeat(source);
        }

        @Override
        public CinemaSeat[] newArray(int size) {
            return new CinemaSeat[size];
        }
    };
}
