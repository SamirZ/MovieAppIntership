package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew implements Parcelable
{

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("name")
    @Expose
    private String name;

    public final static Parcelable.Creator<Crew> CREATOR = new Creator<Crew>() {

        public Crew createFromParcel(Parcel in) {
            Crew instance = new Crew();
            instance.creditId = ((String) in.readValue((String.class.getClassLoader())));
            instance.department = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.job = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
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
    }

    public int describeContents() {
        return 0;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}