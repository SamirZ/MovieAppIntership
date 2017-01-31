package com.example.zsamir.movieappintership.Modules;

public enum TVSeriesGenres {

    ACTION(10759,"Action & Adventure"),
    ANIMATION(16,"Animation"),
    COMEDY(35,"Comedy"),
    CRIME(80,"Crime"),
    DOCUMENTARY(99,"Documentary"),
    DRAMA(18,"Drama"),
    FAMILY(10751,"Family"),
    KIDS(10762,"Kids"),
    MYSTERY(9648,"Mystery"),
    NEWS(10763,"News"),
    REALITY(10764,"Reality"),
    SCIENCE_FICTION(10765,"Sci-Fi & Fantasy"),
    SOAP(10766,"Soap"),
    TALK(10767,"Talk"),
    WAR(10768,"War & Politics"),
    WESTERN(37,"Western");

    int id;
    String title;

    TVSeriesGenres(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static TVSeriesGenres getById(int id) {
        for (TVSeriesGenres movieGenre : values()) {
            if (movieGenre.id == id) {
                return movieGenre;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
