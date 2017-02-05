package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable
{
    public Cast() {
    }

    public Cast(int castId, String character, String creditId, int id, String name, int order, String profilePath) {
        this.castId = castId;
        this.character = character;
        this.creditId = creditId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profilePath = profilePath;
    }

    static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    // Poster image sizes
    static final String POSTER_SIZE_W92 = "w92";
    static final String POSTER_SIZE_W154 = "w154";
    static final String POSTER_SIZE_W185 = "w185";
    static final String POSTER_SIZE_W342 = "w342";
    static final String POSTER_SIZE_W500 = "w500";
    static final String POSTER_W780 = "w780";
    static final String POSTER_SIZE_ORIGINAL = "original";

    @SerializedName("cast_id")
    @Expose
    public int castId;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("order")
    @Expose
    public int order;

    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + profilePath;
    }

    public String getImageUrl() {
        return BASE_IMG_URL +  POSTER_W780 + profilePath;
    }

    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    public final static Parcelable.Creator<Cast> CREATOR = new Creator<Cast>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Cast createFromParcel(Parcel in) {
            Cast instance = new Cast();
            instance.castId = ((int) in.readValue((int.class.getClassLoader())));
            instance.character = ((String) in.readValue((String.class.getClassLoader())));
            instance.creditId = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.order = ((int) in.readValue((int.class.getClassLoader())));
            instance.profilePath = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Cast[] newArray(int size) {
            return (new Cast[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(castId);
        dest.writeValue(character);
        dest.writeValue(creditId);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(order);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return 0;
    }

}