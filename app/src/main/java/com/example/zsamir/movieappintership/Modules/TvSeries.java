package com.example.zsamir.movieappintership.Modules;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvSeries implements Parcelable {

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
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original_name")
    @Expose
    private String originalName;


    public String getBackdropPath() {
        return backdropPath;
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

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public int[] getGenres() {
        return genreIds;
    }

    public List<String> getTvSeriesGenres() {
        List<String> genres = new ArrayList<>();
        int[] ids = getGenres();
        for (int i=0; i<getGenres().length; i++) {
            TVSeriesGenres genre = TVSeriesGenres.getById(ids[i]);
            if (genre != null) {
                genres.add(genre.getTitle());
            }
        }
        return genres;
    }

    public void setGenres(int[] genres) {
        this.genreIds = genres;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int numOfBackdrops;
    public int lastLoadedBackdrop;

    public List<Backdrop> backdropList;

    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    // Poster image sizes
    private static final String POSTER_SIZE_W92 = "w92";
    private static final String POSTER_SIZE_W154 = "w154";
    private static final String POSTER_SIZE_W185 = "w185";
    private static final String POSTER_SIZE_W342 = "w342";
    private static final String POSTER_SIZE_W500 = "w500";
    private static final String POSTER_W780 = "w780";
    private static final String POSTER_SIZE_ORIGINAL = "original";

    // Backdrop image sizes
    private static final String BACKDROP_SIZE_W300 = "w300";
    private static final String BACKDROP_SIZE_W780 = "w780";
    private static final String BACKDROP_SIZE_W1280 = "w1280";
    private static final String BACKDROP_SIZE_ORIGINAL = "original";
    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public String getBackdropUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_W1280+ backdropPath;
    }

    public String getBackdropSizeOriginalUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_ORIGINAL+ backdropPath;
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
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.voteCount);
        dest.writeString(this.name);
        dest.writeString(this.originalName);
        dest.writeInt(this.numOfBackdrops);
        dest.writeInt(this.lastLoadedBackdrop);
        dest.writeTypedList(this.backdropList);
    }

    public TvSeries() {
    }

    public TvSeries(String posterPath, String backdropPath, float voteAverage, String overview, String firstAirDate, int[] genreIds, String name, int id) {
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.name = name;
        this.id = id;
    }

    protected TvSeries(Parcel in) {
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.id = in.readInt();
        this.backdropPath = in.readString();
        this.voteAverage = in.readFloat();
        this.overview = in.readString();
        this.firstAirDate = in.readString();
        this.originCountry = in.createStringArrayList();
        this.genreIds = in.createIntArray();
        this.originalLanguage = in.readString();
        this.voteCount = in.readInt();
        this.name = in.readString();
        this.originalName = in.readString();
        this.numOfBackdrops = in.readInt();
        this.lastLoadedBackdrop = in.readInt();
        this.backdropList = in.createTypedArrayList(Backdrop.CREATOR);
    }

    public static final Creator<TvSeries> CREATOR = new Creator<TvSeries>() {
        @Override
        public TvSeries createFromParcel(Parcel source) {
            return new TvSeries(source);
        }

        @Override
        public TvSeries[] newArray(int size) {
            return new TvSeries[size];
        }
    };
}