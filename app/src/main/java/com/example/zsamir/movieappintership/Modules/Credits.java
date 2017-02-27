package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credits implements Parcelable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    public List<Crew> crew = null;
    public final static Parcelable.Creator<Credits> CREATOR = new Creator<Credits>() {


        public Credits createFromParcel(Parcel in) {
            Credits instance = new Credits();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.cast, (com.example.zsamir.movieappintership.Modules.Cast.class.getClassLoader()));
            in.readList(instance.crew, (com.example.zsamir.movieappintership.Modules.Crew.class.getClassLoader()));
            return instance;
        }

        public Credits[] newArray(int size) {
            return (new Credits[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return 0;
    }

}