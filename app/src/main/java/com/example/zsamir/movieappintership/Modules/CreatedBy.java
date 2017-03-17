package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CreatedBy extends RealmObject implements Parcelable
{

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

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public final static Parcelable.Creator<CreatedBy> CREATOR = new Creator<CreatedBy>() {

        public CreatedBy createFromParcel(Parcel in) {
            CreatedBy instance = new CreatedBy();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.profilePath = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CreatedBy[] newArray(int size) {
            return (new CreatedBy[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return 0;
    }

}