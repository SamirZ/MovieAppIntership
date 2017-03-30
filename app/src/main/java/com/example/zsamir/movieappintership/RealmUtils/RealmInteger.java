package com.example.zsamir.movieappintership.RealmUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmInteger extends RealmObject{

    @PrimaryKey
    private int i;

    public RealmInteger() {
    }

    public RealmInteger(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
