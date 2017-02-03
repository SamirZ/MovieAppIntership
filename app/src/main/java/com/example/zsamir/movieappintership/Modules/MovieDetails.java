package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetails implements Parcelable
{
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

    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    public BelongsToCollection belongsToCollection;
    @SerializedName("budget")
    @Expose
    public int budget;
    @SerializedName("genres")
    @Expose
    public List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("production_companies")
    @Expose
    public List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    public List<ProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("revenue")
    @Expose
    public int revenue;
    @SerializedName("runtime")
    @Expose
    public int runtime;
    @SerializedName("spoken_languages")
    @Expose
    public List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tagline")
    @Expose
    public String tagline;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;

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
        return s[0];
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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


    public final static Parcelable.Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {

        public MovieDetails createFromParcel(Parcel in) {
            MovieDetails instance = new MovieDetails();
            instance.adult = ((boolean) in.readValue((boolean.class.getClassLoader())));
            instance.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
            instance.belongsToCollection = ((BelongsToCollection) in.readValue((BelongsToCollection.class.getClassLoader())));
            instance.budget = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.genres, (Genre.class.getClassLoader()));
            instance.homepage = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.imdbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
            instance.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.overview = ((String) in.readValue((String.class.getClassLoader())));
            instance.popularity = ((float) in.readValue((float.class.getClassLoader())));
            instance.posterPath = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.productionCompanies, (ProductionCompany.class.getClassLoader()));
            in.readList(instance.productionCountries, (ProductionCountry.class.getClassLoader()));
            instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.revenue = ((int) in.readValue((int.class.getClassLoader())));
            instance.runtime = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.spokenLanguages, (SpokenLanguage.class.getClassLoader()));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.tagline = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.video = ((boolean) in.readValue((boolean.class.getClassLoader())));
            instance.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
            instance.voteCount = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public MovieDetails[] newArray(int size) {
            return (new MovieDetails[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeValue(backdropPath);
        dest.writeValue(belongsToCollection);
        dest.writeValue(budget);
        dest.writeList(genres);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(imdbId);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalTitle);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        dest.writeList(productionCompanies);
        dest.writeList(productionCountries);
        dest.writeValue(releaseDate);
        dest.writeValue(revenue);
        dest.writeValue(runtime);
        dest.writeList(spokenLanguages);
        dest.writeValue(status);
        dest.writeValue(tagline);
        dest.writeValue(title);
        dest.writeValue(video);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
    }

    public int describeContents() {
        return 0;
    }

}