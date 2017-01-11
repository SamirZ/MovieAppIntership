package com.example.zsamir.movieappintership.Modules;


import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvSeries implements Parcelable
{

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
    private ArrayList originCountry = null;
    @SerializedName("genre_ids")
    @Expose
    int[] genreIds = new int[0];
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


    protected TvSeries(Parcel in) {
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.id = in.readInt();
        this.backdropPath = in.readString();
        this.voteAverage = in.readFloat();
        this.overview = in.readString();
        this.firstAirDate = in.readString();
        this.originCountry = in.readArrayList(String.class.getClassLoader());
        this.genreIds = in.createIntArray();
        this.originalLanguage = in.readString();
        this.voteCount = in.readInt();
        this.name = in.readString();
        this.originalName = in.readString();
    }

    public static final Parcelable.Creator<TvSeries> CREATOR = new Parcelable.Creator<TvSeries>() {
        public TvSeries createFromParcel(Parcel source) {
            return new TvSeries(source);
        }

        public TvSeries[] newArray(int size) {
            return new TvSeries[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(ArrayList<String> originCountry) {
        this.originCountry = originCountry;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(posterPath);
        dest.writeValue(popularity);
        dest.writeValue(id);
        dest.writeValue(backdropPath);
        dest.writeValue(voteAverage);
        dest.writeValue(overview);
        dest.writeValue(firstAirDate);
        dest.writeList(originCountry);
        dest.writeIntArray(genreIds);
        dest.writeValue(originalLanguage);
        dest.writeValue(voteCount);
        dest.writeValue(name);
        dest.writeValue(originalName);
    }

    public int describeContents() {
        return 0;
    }

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

    // recommended for most phones:
    static final String SIZE_DEFAULT = POSTER_SIZE_W185;

    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public List<String> getTvSeriesGenres() {
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

}

