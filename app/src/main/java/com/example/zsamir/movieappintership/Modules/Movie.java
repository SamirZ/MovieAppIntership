package com.example.zsamir.movieappintership.Modules;

/**
 * Created by zsami on 06-Jan-17.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    public int numOfPosters;
    public int numOfBackdrops;
    public int lastLoadedBackdrop;

    static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    // Poster image sizes
    static final String POSTER_SIZE_W92 = "w92";
    static final String POSTER_SIZE_W154 = "w154";
    static final String POSTER_SIZE_W185 = "w185";
    static final String POSTER_SIZE_W342 = "w342";
    static final String POSTER_SIZE_W500 = "w500";
    static final String POSTER_W780 = "w780";
    static final String POSTER_SIZE_ORIGINAL = "original";

    // Backdrop image sizes
    static final String BACKDROP_SIZE_W300 = "w300";
    static final String BACKDROP_SIZE_W780 = "w780";
    static final String BACKDROP_SIZE_W1280 = "w1280";
    static final String BACKDROP_SIZE_ORIGINAL = "original";

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    int[] genreIds = new int[0];
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.adult = in.readByte() != 0;
        this.backdropPath = in.readString();
        this.genreIds = in.createIntArray();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readFloat();
        this.voteCount = in.readInt();
        this.numOfBackdrops=in.readInt();
        this.lastLoadedBackdrop = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    // Returns a poster with default size
    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        String[] s = releaseDate.split("-");
        return s[2]+" "+getMonth(Integer.parseInt(s[1]))+ " "+ s[0];
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
        return s[0];
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public List<String> getMovieGenres() {
        List<String> genres = new ArrayList<>();
        int[] ids = getGenreIds();
        for (int i=0; i<getGenreIds().length-1; i++) {
            Genres genre = Genres.getById(ids[i]);
            if (genre != null) {
                genres.add(genre.getTitle());
            }
        }
        return genres;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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
    public String getBackdropSizeOriginalUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_ORIGINAL+ backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdropPath);
        dest.writeIntArray(this.genreIds);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeFloat(this.popularity);
        dest.writeString(this.title);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.voteAverage);
        dest.writeInt(this.voteCount);
        dest.writeInt(this.numOfBackdrops);
        dest.writeInt(this.lastLoadedBackdrop);
    }
}
