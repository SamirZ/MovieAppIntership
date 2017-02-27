package com.example.zsamir.movieappintership.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShowDetails extends ImageFormat implements Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("created_by")
    @Expose
    private List<CreatedBy> createdBy = null;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("in_production")
    @Expose
    private Boolean inProduction;
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;
    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdropPath);
        dest.writeTypedList(this.createdBy);
        dest.writeString(this.firstAirDate);
        dest.writeTypedList(this.genres);
        dest.writeValue(this.id);
        dest.writeValue(this.inProduction);
        dest.writeString(this.lastAirDate);
        dest.writeString(this.name);
        dest.writeValue(this.numberOfEpisodes);
        dest.writeValue(this.numberOfSeasons);
        dest.writeStringList(this.originCountry);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalName);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeTypedList(this.seasons);
        dest.writeValue(this.voteAverage);
    }

    private TVShowDetails(Parcel in) {
        this.backdropPath = in.readString();
        this.createdBy = in.createTypedArrayList(CreatedBy.CREATOR);
        this.firstAirDate = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.inProduction = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.lastAirDate = in.readString();
        this.name = in.readString();
        this.numberOfEpisodes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.numberOfSeasons = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originCountry = in.createStringArrayList();
        this.originalLanguage = in.readString();
        this.originalName = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.seasons = in.createTypedArrayList(Season.CREATOR);
        this.voteAverage = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Parcelable.Creator<TVShowDetails> CREATOR = new Parcelable.Creator<TVShowDetails>() {
        @Override
        public TVShowDetails createFromParcel(Parcel source) {
            return new TVShowDetails(source);
        }

        @Override
        public TVShowDetails[] newArray(int size) {
            return new TVShowDetails[size];
        }
    };

    public String getPosterUrl() {
        return BASE_IMG_URL + POSTER_SIZE_W185 + posterPath;
    }

    public String getBackdropUrl() {
        return BASE_IMG_URL + BACKDROP_SIZE_W1280+ backdropPath;
    }

}
