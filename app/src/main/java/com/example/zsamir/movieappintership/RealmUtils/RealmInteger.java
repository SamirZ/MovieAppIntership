package com.example.zsamir.movieappintership.RealmUtils;

import io.realm.RealmObject;


public class RealmInteger extends RealmObject{

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
