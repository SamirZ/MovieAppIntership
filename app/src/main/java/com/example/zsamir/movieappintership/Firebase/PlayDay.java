package com.example.zsamir.movieappintership.Firebase;

import java.util.ArrayList;

public class PlayDay {

    private String name;
    private ArrayList<CinemaMovie> movies;

    public PlayDay(String name, ArrayList<CinemaMovie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CinemaMovie> getMovies() {
        return movies;
    }
}
