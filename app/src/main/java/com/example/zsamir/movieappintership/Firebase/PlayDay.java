package com.example.zsamir.movieappintership.Firebase;

import java.util.ArrayList;

public class PlayDay {

    private String date;
    private String name;
    private ArrayList<CinemaMovie> movies;

    public PlayDay(String date, String name, ArrayList<CinemaMovie> movies) {
        this.name = name;
        this.movies = movies;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CinemaMovie> getMovies() {
        return movies;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(ArrayList<CinemaMovie> movies) {
        this.movies = movies;
    }
}
