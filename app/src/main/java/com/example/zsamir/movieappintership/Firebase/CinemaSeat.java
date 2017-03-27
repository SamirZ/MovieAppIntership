package com.example.zsamir.movieappintership.Firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class CinemaSeat implements Parcelable {

    private int id;
    private boolean free;

    public CinemaSeat() {
    }

    public CinemaSeat(int id, boolean free) {
        this.id = id;
        this.free = free;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        dest.writeInt(this.id);
        dest.writeByte(this.free ? (byte) 1 : (byte) 0);
    }

    protected CinemaSeat(Parcel in) {
        this.id = in.readInt();
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
