package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeCast implements Parcelable {

    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    // Poster image sizes
    //private static final String POSTER_SIZE_W92 = "w92";
    //private static final String POSTER_SIZE_W154 = "w154";
    private static final String POSTER_SIZE_W185 = "w185";
    //private static final String POSTER_SIZE_W342 = "w342";
    //private static final String POSTER_SIZE_W500 = "w500";
    private static final String POSTER_W780 = "w780";
    //private static final String POSTER_SIZE_ORIGINAL = "original";

    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + profilePath;
    }

    public String getImageUrl() {
        return BASE_IMG_URL +  POSTER_W780 + profilePath;
    }

    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("order")
    @Expose
    private Integer order;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.character);
        dest.writeString(this.creditId);
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.profilePath);
        dest.writeValue(this.order);
    }

    private EpisodeCast(Parcel in) {
        this.character = in.readString();
        this.creditId = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.profilePath = in.readString();
        this.order = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<EpisodeCast> CREATOR = new Parcelable.Creator<EpisodeCast>() {
        @Override
        public EpisodeCast createFromParcel(Parcel source) {
            return new EpisodeCast(source);
        }

        @Override
        public EpisodeCast[] newArray(int size) {
            return new EpisodeCast[size];
        }
    };

    public Cast toCast(){
        return new Cast(0,character,creditId,id,name,order,profilePath);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
}
