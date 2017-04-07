package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject implements Parcelable {

    public int numOfBackdrops;
    public int lastLoadedBackdrop;
    @Ignore
    public List<Backdrop> backdropList;

    public boolean favorite;
    public boolean watch;
    public boolean rated;

    public boolean popular;
    public boolean latest;
    public boolean highestrated;

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
    @Ignore
    private int[] genreIds = new int[0];
    @SerializedName("id")
    @Expose
    @PrimaryKey
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

    public String allGenres;

    public int getRating() {
        return rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    // Returns a poster with default size
    public String getPosterUrl() {
        return ImageFormat.BASE_IMG_URL + ImageFormat.POSTER_SIZE_W154 + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        if(releaseDate!=null){
        String[] s = releaseDate.split("-");
            if(s.length>1){
                if(s[2].startsWith("0")){
                    String s1 = s[2].substring(1);
                    return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
                }
                return s[2]+" "+ getMonth(Integer.parseInt(s[1]))+ " "+ s[0];
            }
        }return null;

    }

    public String getOrgReleaseDate(){
        return releaseDate;
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

    public  int[] getGenreIds() {
        return genreIds;
    }

    public List<String> getMovieGenres() {
        List<String> genres = new ArrayList<>();
        int[] ids = getGenreIds();
        if(allGenres!=null){
            String[] g = allGenres.split(",");
            for (String s:g) {
                if(s.length()>0)
                    genres.add(MovieGenres.getById(Integer.parseInt(s)).getTitle());
            }
        }
        else{
            for (int id1 : ids) {
                MovieGenres genre = MovieGenres.getById(id1);
                if (genre != null) {
                    genres.add(genre.getTitle());
                }
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
        return ImageFormat.BASE_IMG_URL + ImageFormat.BACKDROP_SIZE_W780 + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }



    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean getVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public Movie() {
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
        dest.writeByte(this.popular ? (byte) 1 : (byte) 0);
        dest.writeByte(this.latest ? (byte) 1 : (byte) 0);
        dest.writeByte(this.highestrated ? (byte) 1 : (byte) 0);
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
        dest.writeString(this.allGenres);
    }

    protected Movie(Parcel in) {
        this.numOfBackdrops = in.readInt();
        this.lastLoadedBackdrop = in.readInt();
        this.backdropList = in.createTypedArrayList(Backdrop.CREATOR);
        this.popular = in.readByte() != 0;
        this.latest = in.readByte() != 0;
        this.highestrated = in.readByte() != 0;
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
        this.allGenres = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(String posterPath, String overview, String releaseDate, int id, String title, String backdropPath, float voteAverage, String allGenres, boolean popular, boolean latest, boolean highestrated) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.allGenres = allGenres;
        this.popular = popular;
        this.latest = latest;
        this.highestrated = highestrated;
    }

    public Movie(boolean popular, boolean latest, boolean highestrated, String posterPath, String overview, String releaseDate, int id, String title, String backdropPath, boolean video, float voteAverage, int rating, String allGenres) {
        this.popular = popular;
        this.latest = latest;
        this.highestrated = highestrated;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.title = title;
        this.backdropPath = backdropPath;
        this.video = video;
        this.voteAverage = voteAverage;
        this.rating = rating;
        this.allGenres = allGenres;
    }
}
