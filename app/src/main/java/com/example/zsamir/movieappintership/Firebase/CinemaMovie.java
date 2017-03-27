package com.example.zsamir.movieappintership.Firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CinemaMovie implements Parcelable {

    private int id;
    private String name;
    private String time;
    private String genres;
    private double rating;
    private String overview;
    private String backdropPath;
    private String releaseDate;

    private ArrayList<CinemaSeat> seats;

    public CinemaMovie() {
    }

    public CinemaMovie(int id, String name, String time, String genres, double rating, String overview, String backdropPath, String releaseDate, ArrayList<CinemaSeat> seats) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.genres = genres;
        this.rating = rating;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<CinemaSeat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<CinemaSeat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "CinemaMovie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", genres='" + genres + '\'' +
                ", rating=" + rating +
                ", overview='" + overview + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", seats=" + seats +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeString(this.genres);
        dest.writeDouble(this.rating);
        dest.writeString(this.overview);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeList(this.seats);
    }

    protected CinemaMovie(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.time = in.readString();
        this.genres = in.readString();
        this.rating = in.readDouble();
        this.overview = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.seats = new ArrayList<>();
        in.readList(this.seats, CinemaSeat.class.getClassLoader());
    }

    public static final Parcelable.Creator<CinemaMovie> CREATOR = new Parcelable.Creator<CinemaMovie>() {
        @Override
        public CinemaMovie createFromParcel(Parcel source) {
            return new CinemaMovie(source);
        }

        @Override
        public CinemaMovie[] newArray(int size) {
            return new CinemaMovie[size];
        }
    };
}
