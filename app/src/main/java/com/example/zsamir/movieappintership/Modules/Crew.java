package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Crew extends RealmObject implements Parcelable
{

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("id")
    @Expose
    @PrimaryKey
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

    };

    public Crew() {
    }

    public Crew(Crew c){
        this.id = c.id;
        this.department = c.department;
        this.creditId = c.creditId;
        this.name = c.name;
        this.job = c.job;
    }

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

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

}