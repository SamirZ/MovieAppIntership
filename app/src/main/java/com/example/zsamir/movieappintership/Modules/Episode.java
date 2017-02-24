package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable {

    @SerializedName("air_date")
    @Expose
    private String airDate;

    @SerializedName("episode_number")
    @Expose
    private int episodeNumber;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.airDate);
        dest.writeInt(this.episodeNumber);
        dest.writeString(this.name);
        dest.writeInt(this.seasonNumber);
        dest.writeDouble(this.voteAverage);
    }

    private Episode(Parcel in) {
        this.airDate = in.readString();
        this.episodeNumber = in.readInt();
        this.name = in.readString();
        this.seasonNumber = in.readInt();
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel source) {
            return new Episode(source);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getAirDate() {
        if(airDate!=null) {
            String[] s = airDate.split("-");
            if (s.length > 2){
                if(s[2].startsWith("0")){
                    String s1 = s[2].substring(1);
                    return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
                }else
                    return s[2] + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];

            }

        }
        return "TBD";
    }



    private String getMonth(int i) {
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

    public String getAirYear() {
        if(airDate!=null){
        if(!airDate.equalsIgnoreCase("TBD")){
        String[] s = airDate.split("-");
        return s[0];
        }return "";
        }return "";
    }
}
