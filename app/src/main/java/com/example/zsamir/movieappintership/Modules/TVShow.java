package com.example.zsamir.movieappintership.Modules;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShow extends ImageFormat implements Parcelable {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("popularity")
    @Expose
    private float popularity;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;

    @SerializedName("genre_ids")
    @Expose
    private int[] genreIds  = new int[0];

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("rating")
    @Expose
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getFirstAirDate(){
        if(firstAirDate!=null){
            if(firstAirDate.length()>0){
                String[] s = firstAirDate.split("-");
                if(s[2].startsWith("0")){
                    String s1 = s[2].substring(1);
                    return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
                }
                return s[2]+" "+ getMonth(Integer.parseInt(s[1]))+ " "+ s[0];
            }else
                return null;
        }else
            return null;
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
        String[] s = firstAirDate.split("-");
        return s[0];
    }

    private int[] getGenres() {
        return genreIds;
    }

    public List<String> getTvSeriesGenres() {
        List<String> genres = new ArrayList<>();
        int[] ids = getGenres();
        for (int i=0; i<getGenres().length; i++) {
            TVShowGenres genre = TVShowGenres.getById(ids[i]);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int numOfBackdrops;
    public int lastLoadedBackdrop;

    public List<Backdrop> backdropList;

    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public String getBackdropUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_W1280+ backdropPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.id);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.firstAirDate);
        dest.writeStringList(this.originCountry);
        dest.writeIntArray(this.genreIds);
        dest.writeString(this.name);
        dest.writeInt(this.numOfBackdrops);
        dest.writeInt(this.lastLoadedBackdrop);
        dest.writeTypedList(this.backdropList);
        dest.writeInt(this.rating);
    }

    public TVShow() {
    }

    public TVShow(String posterPath, String backdropPath, float voteAverage, String overview, String firstAirDate, int[] genreIds, String name, int id) {
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.name = name;
        this.id = id;
    }

    protected TVShow(Parcel in) {
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.id = in.readInt();
        this.backdropPath = in.readString();
        this.voteAverage = in.readFloat();
        this.overview = in.readString();
        this.firstAirDate = in.readString();
        this.originCountry = in.createStringArrayList();
        this.genreIds = in.createIntArray();
        this.name = in.readString();
        this.numOfBackdrops = in.readInt();
        this.lastLoadedBackdrop = in.readInt();
        this.backdropList = in.createTypedArrayList(Backdrop.CREATOR);
        this.rating = in.readInt();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}