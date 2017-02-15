package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVSeriesDetails implements Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("created_by")
    @Expose
    private List<CreatedBy> createdBy = null;
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("in_production")
    @Expose
    private Boolean inProduction;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("networks")
    @Expose
    private List<Network> networks = null;
    @SerializedName("number_of_episodes")
    @Expose
    private Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private Integer numberOfSeasons;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Float popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<CreatedBy> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate(){
        String[] s = firstAirDate.split("-");
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
        if(firstAirDate!=null){
        String[] s = firstAirDate.split("-");
        if(s.length>0){
            return s[0];
        }else{
            return null;
        }
        }else return null;
    }

    public String getFinishYear() {
        if(!inProduction){
            String[] s = lastAirDate.split("-");
            if(s.length>0){
                return s[0];
            }else{
                return null;
            }

        }
        else
            return null;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getInProduction() {
        return inProduction;
    }

    public void setInProduction(Boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        String[] s = lastAirDate.split("-");
        if(s[2].startsWith("0")){
            String s1 = s[2].substring(1);
            return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
        }
        return s[2]+" "+ getMonth(Integer.parseInt(s[1]))+ " "+ s[0];
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
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

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdropPath);
        dest.writeTypedList(this.createdBy);
        dest.writeList(this.episodeRunTime);
        dest.writeString(this.firstAirDate);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeValue(this.id);
        dest.writeValue(this.inProduction);
        dest.writeStringList(this.languages);
        dest.writeString(this.lastAirDate);
        dest.writeString(this.name);
        dest.writeTypedList(this.networks);
        dest.writeValue(this.numberOfEpisodes);
        dest.writeValue(this.numberOfSeasons);
        dest.writeStringList(this.originCountry);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalName);
        dest.writeString(this.overview);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeTypedList(this.productionCompanies);
        dest.writeTypedList(this.seasons);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
    }

    public TVSeriesDetails() {
    }

    protected TVSeriesDetails(Parcel in) {
        this.backdropPath = in.readString();
        this.createdBy = in.createTypedArrayList(CreatedBy.CREATOR);
        this.episodeRunTime = new ArrayList<Integer>();
        in.readList(this.episodeRunTime, Integer.class.getClassLoader());
        this.firstAirDate = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.homepage = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.inProduction = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.languages = in.createStringArrayList();
        this.lastAirDate = in.readString();
        this.name = in.readString();
        this.networks = in.createTypedArrayList(Network.CREATOR);
        this.numberOfEpisodes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.numberOfSeasons = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originCountry = in.createStringArrayList();
        this.originalLanguage = in.readString();
        this.originalName = in.readString();
        this.overview = in.readString();
        this.popularity = (Float) in.readValue(Float.class.getClassLoader());
        this.posterPath = in.readString();
        this.productionCompanies = in.createTypedArrayList(ProductionCompany.CREATOR);
        this.seasons = in.createTypedArrayList(Season.CREATOR);
        this.status = in.readString();
        this.type = in.readString();
        this.voteAverage = (Float) in.readValue(Float.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TVSeriesDetails> CREATOR = new Parcelable.Creator<TVSeriesDetails>() {
        @Override
        public TVSeriesDetails createFromParcel(Parcel source) {
            return new TVSeriesDetails(source);
        }

        @Override
        public TVSeriesDetails[] newArray(int size) {
            return new TVSeriesDetails[size];
        }
    };

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
}
