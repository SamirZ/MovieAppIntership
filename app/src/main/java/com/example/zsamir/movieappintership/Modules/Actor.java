package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor implements Parcelable
{

    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("also_known_as")
    @Expose
    private List<String> alsoKnownAs = null;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("deathday")
    @Expose
    private String deathday;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHomepage() {
        return homepage;
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

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public final static Parcelable.Creator<Actor> CREATOR = new Creator<Actor>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Actor createFromParcel(Parcel in) {
            Actor instance = new Actor();
            instance.adult = ((boolean) in.readValue((boolean.class.getClassLoader())));
            in.readList(instance.alsoKnownAs, (java.lang.String.class.getClassLoader()));
            instance.biography = ((String) in.readValue((String.class.getClassLoader())));
            instance.birthday = ((String) in.readValue((String.class.getClassLoader())));
            instance.deathday = ((String) in.readValue((String.class.getClassLoader())));
            instance.gender = ((int) in.readValue((int.class.getClassLoader())));
            instance.homepage = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.imdbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.placeOfBirth = ((String) in.readValue((String.class.getClassLoader())));
            instance.popularity = ((float) in.readValue((float.class.getClassLoader())));
            instance.profilePath = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Actor[] newArray(int size) {
            return (new Actor[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeList(alsoKnownAs);
        dest.writeValue(biography);
        dest.writeValue(birthday);
        dest.writeValue(deathday);
        dest.writeValue(gender);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(imdbId);
        dest.writeValue(name);
        dest.writeValue(placeOfBirth);
        dest.writeValue(popularity);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return 0;
    }

}