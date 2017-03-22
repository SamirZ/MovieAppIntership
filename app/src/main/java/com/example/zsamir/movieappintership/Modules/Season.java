package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Season extends RealmObject implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_count")
    @Expose
    private int episodeCount;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    public final static Parcelable.Creator<Season> CREATOR = new Creator<Season>() {

        public Season createFromParcel(Parcel in) {
            Season instance = new Season();
            instance.airDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.episodeCount = ((int) in.readValue((int.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.posterPath = ((String) in.readValue((String.class.getClassLoader())));
            instance.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Season[] newArray(int size) {
            return (new Season[size]);
        }

    };


    public String getAirYear() {
        if(airDate!=null){
            String[] s = airDate.split("-");
            return s[0];
        }
        else
            return "TBD";
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeValue(episodeCount);
        dest.writeValue(id);
        dest.writeValue(posterPath);
        dest.writeValue(seasonNumber);
    }

    public int describeContents() {
        return 0;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }
}
