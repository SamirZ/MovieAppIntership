package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cast extends RealmObject implements Parcelable
{
    public Cast() {
    }

    public Cast(Cast c) {
        this.castId = c.castId;
        this.character = c.character;
        this.creditId = c.creditId;
        this.id = c.id;
        this.name = c.name;
        this.order = c.order;
        this.profilePath = c.profilePath;
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

    @SerializedName("cast_id")
    @Expose
    private int castId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public String getPosterUrl() {
        return ImageFormat.BASE_IMG_URL + ImageFormat.POSTER_SIZE_W185 + profilePath;
    }

    public String getImageUrl() {
        return ImageFormat.BASE_IMG_URL +  ImageFormat.POSTER_W780 + profilePath;
    }

    public final static Parcelable.Creator<Cast> CREATOR = new Creator<Cast>() {


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

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }


}