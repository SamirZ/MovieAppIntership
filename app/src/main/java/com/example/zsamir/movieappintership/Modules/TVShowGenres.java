package com.example.zsamir.movieappintership.Modules;

public enum TVShowGenres {

    ACTION(10759,"Action & Adventure"),
    ACTION2(28, "Action"),
    ADVENTURE(12, "Adventure"),
    ANIMATION(16,"Animation"),
    COMEDY(35,"Comedy"),
    CRIME(80,"Crime"),
    DOCUMENTARY(99,"Documentary"),
    DRAMA(18,"Drama"),
    FAMILY(10751,"Family"),
    FANTASY(14,"Fantasy"),
    HORROR(27, "Horror"),
    KIDS(10762,"Kids"),
    MYSTERY(9648,"Mystery"),
    NEWS(10763,"News"),
    REALITY(10764,"Reality"),
    ROMANCE(10749,"Romance"),
    SCIENCE_FICTION(10765,"Sci-Fi & Fantasy"),
    SCIENCE_FICTION2(878, "Science Fiction"),
    SOAP(10766,"Soap"),
    TALK(10767,"Talk"),
    THRILLER(53, "Thriller"),
    WAR(10768,"War & Politics"),
    WESTERN(37,"Western"),
    NO(0,"Not Sorted");

    int id;
    String title;

    TVShowGenres(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static TVShowGenres getById(int id) {
        for (TVShowGenres movieGenre : values()) {
            if (movieGenre.id == id) {
                return movieGenre;
            }
        }
        return TVShowGenres.getById(0);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
