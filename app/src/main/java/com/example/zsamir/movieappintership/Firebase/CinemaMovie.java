package com.example.zsamir.movieappintership.Firebase;

import java.util.ArrayList;

public class CinemaMovie {

    private int id;
    private String name;
    private String playTime;

    private ArrayList<CinemaSeat> seats;

    public CinemaMovie(int id, String name, String playTime, ArrayList<CinemaSeat> seats) {
        this.id = id;
        this.name = name;
        this.playTime = playTime;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlayTime() {
        return playTime;
    }

    public ArrayList<CinemaSeat> getSeats() {
        return seats;
    }
}
