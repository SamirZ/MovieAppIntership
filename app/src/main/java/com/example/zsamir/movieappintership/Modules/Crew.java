package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew implements Parcelable
{

    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("department")
    @Expose
    public String department;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("job")
    @Expose
    public String job;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("profile_path")
    @Expose
    public Object profilePath;
    public final static Parcelable.Creator<Crew> CREATOR = new Creator<Crew>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Crew createFromParcel(Parcel in) {
            Crew instance = new Crew();
            instance.creditId = ((String) in.readValue((String.class.getClassLoader())));
            instance.department = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.job = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.profilePath = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Crew[] newArray(int size) {
            return (new Crew[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(creditId);
        dest.writeValue(department);
        dest.writeValue(id);
        dest.writeValue(job);
        dest.writeValue(name);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return 0;
    }

}