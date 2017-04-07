package com.example.zsamir.movieappintership.Firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlayTime implements Parcelable {

    private String time;
    private ArrayList<CinemaSeat> seats;

    public PlayTime(String time, ArrayList<CinemaSeat> seats) {
        this.time = time;
        this.seats = seats;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<CinemaSeat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<CinemaSeat> seats) {
        this.seats = seats;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeTypedList(this.seats);
    }

    public PlayTime() {
    }

    protected PlayTime(Parcel in) {
        this.time = in.readString();
        this.seats = in.createTypedArrayList(CinemaSeat.CREATOR);
    }

    public static final Parcelable.Creator<PlayTime> CREATOR = new Parcelable.Creator<PlayTime>() {
        @Override
        public PlayTime createFromParcel(Parcel source) {
            return new PlayTime(source);
        }

        @Override
        public PlayTime[] newArray(int size) {
            return new PlayTime[size];
        }
    };

    @Override
    public String toString() {
        return "PlayTime{" +
                "time='" + time + '\'' +
                '}';
    }
}
