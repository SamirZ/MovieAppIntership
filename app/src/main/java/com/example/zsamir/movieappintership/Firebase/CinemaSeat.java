package com.example.zsamir.movieappintership.Firebase;

public class CinemaSeat {

    private int id;
    private boolean free;

    public CinemaSeat(int id, boolean free) {
        this.id = id;
        this.free = free;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
