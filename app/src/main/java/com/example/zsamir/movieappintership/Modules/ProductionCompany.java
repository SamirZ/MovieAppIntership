package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Parcelable
{

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public int id;
    public final static Parcelable.Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            ProductionCompany instance = new ProductionCompany();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}