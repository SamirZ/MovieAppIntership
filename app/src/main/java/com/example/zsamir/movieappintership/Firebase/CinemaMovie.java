package com.example.zsamir.movieappintership.Firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CinemaMovie implements Parcelable {

    private int id;
    private String name;
    private String genres;
    private double rating;
    private String overview;
    private String backdropPath;
    private String releaseDate;

    private ArrayList<PlayTime> playTimes;

    public CinemaMovie() {
    }

    public CinemaMovie(int id, String name, String genres, double rating, String overview, String backdropPath, String releaseDate, ArrayList<PlayTime> playTimes) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.rating = rating;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.playTimes = playTimes;
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

    public ArrayList<PlayTime> getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(ArrayList<PlayTime> playTimes) {
        this.playTimes = playTimes;
    }



    public String getStringReleaseDate() {
        if(releaseDate!=null){
            String[] s = releaseDate.split("-");
            if(s.length>1){
                if(s[2].startsWith("0")){
                    String s1 = s[2].substring(1);
                    return s1 + " " + toMonth(Integer.parseInt(s[1])) + " " + s[0];
                }
                return s[2]+" "+ toMonth(Integer.parseInt(s[1]))+ " "+ s[0];
            }
        }return null;

    }

    private String toMonth(int i) {
        switch (i){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "Wrong Month Format";
    }

    public String getReleaseYear() {
        String[] s = releaseDate.split("-");
        if(s.length>0){
            return s[0];
        }else {
            return null;
        }
    }


    @Override
    public String toString() {
        return "CinemaMovie{" +
                ", name='" + name + '\'' +
                ", playTimes=" + playTimes +
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
        dest.writeString(this.genres);
        dest.writeDouble(this.rating);
        dest.writeString(this.overview);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeTypedList(this.playTimes);
    }

    protected CinemaMovie(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.genres = in.readString();
        this.rating = in.readDouble();
        this.overview = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.playTimes = in.createTypedArrayList(PlayTime.CREATOR);
    }

    public static final Creator<CinemaMovie> CREATOR = new Creator<CinemaMovie>() {
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
