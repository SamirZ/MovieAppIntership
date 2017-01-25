package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable {

    @SerializedName("air_date")
    @Expose
    private String airDate;

    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    @SerializedName("episode_number")
    @Expose
    private int episodeNumber;

    @SerializedName("guest_stars")
    @Expose
    private List<GuestStar> guestStars = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;


    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<GuestStar> guestStars) {
        this.guestStars = guestStars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.airDate);
        dest.writeTypedList(this.crew);
        dest.writeInt(this.episodeNumber);
        dest.writeList(this.guestStars);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeInt(this.id);
        dest.writeInt(this.seasonNumber);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.voteCount);
    }

    public Episode() {
    }

    protected Episode(Parcel in) {
        this.airDate = in.readString();
        this.crew = in.createTypedArrayList(Crew.CREATOR);
        this.episodeNumber = in.readInt();
        this.guestStars = new ArrayList<GuestStar>();
        in.readList(this.guestStars, GuestStar.class.getClassLoader());
        this.name = in.readString();
        this.overview = in.readString();
        this.id = in.readInt();
        this.seasonNumber = in.readInt();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
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
        return "To be dated";
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
}
