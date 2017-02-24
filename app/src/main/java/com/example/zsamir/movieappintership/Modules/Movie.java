package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie extends ImageFormat implements Parcelable {

    public int numOfBackdrops;
    public int lastLoadedBackdrop;
    public List<Backdrop> backdropList;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private int[] genreIds = new int[0];
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("rating")
    @Expose
    private int rating;

    public int getRating() {
        return rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    // Returns a poster with default size
    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        String[] s = releaseDate.split("-");
        if(s[2].startsWith("0")){
            String s1 = s[2].substring(1);
            return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
        }
        return s[2]+" "+ getMonth(Integer.parseInt(s[1]))+ " "+ s[0];

    }

    private String getMonth(int i) {
        switch (i){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "Wrong Month Format";
    }

    public String getReleaseYear() {
        String[] s = releaseDate.split("-");
        if(s.length>0){
            return s[0];
        }else {
            return null;
        }
    }

    private  int[] getGenreIds() {
        return genreIds;
    }

    public List<String> getMovieGenres() {
        List<String> genres = new ArrayList<>();
        int[] ids = getGenreIds();
        for (int i=0; i<getGenreIds().length; i++) {
            MovieGenres genre = MovieGenres.getById(ids[i]);
            if (genre != null) {
                genres.add(genre.getTitle());
            }
        }
        return genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_W1280+ backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public Movie(String posterPath, String overview, String releaseDate, int[] genreIds, int id, String title, String backdropPath, float voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numOfBackdrops);
        dest.writeInt(this.lastLoadedBackdrop);
        dest.writeTypedList(this.backdropList);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeIntArray(this.genreIds);
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.voteAverage);
        dest.writeInt(this.rating);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.numOfBackdrops = in.readInt();
        this.lastLoadedBackdrop = in.readInt();
        this.backdropList = in.createTypedArrayList(Backdrop.CREATOR);
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = in.createIntArray();
        this.id = in.readInt();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readFloat();
        this.rating = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
