package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor implements Parcelable
{

    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("also_known_as")
    @Expose
    public List<String> alsoKnownAs = null;
    @SerializedName("biography")
    @Expose
    public String biography;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("deathday")
    @Expose
    public String deathday;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("place_of_birth")
    @Expose
    public String placeOfBirth;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
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