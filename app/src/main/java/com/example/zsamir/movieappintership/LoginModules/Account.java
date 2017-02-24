package com.example.zsamir.movieappintership.LoginModules;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Account implements Parcelable {

    private List<Integer> favMovieList = new ArrayList<>();
    private List<Integer> favTVSeriesList = new ArrayList<>();

    private List<Integer> ratedMovieList = new ArrayList<>();
    private List<Integer> ratedTVSeriesList = new ArrayList<>();

    private List<Integer> watchlistMovieList = new ArrayList<>();
    private List<Integer> watchlistTVSeriesList = new ArrayList<>();

    private String sessionId;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("include_adult")
    @Expose
    private Boolean includeAdult;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(Boolean includeAdult) {
        this.includeAdult = includeAdult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getFavMovieList() {
        return favMovieList;
    }

    public void setFavMovieList(List<Integer> favMovieList) {
        this.favMovieList = favMovieList;
    }

    public List<Integer> getFavTVSeriesList() {
        return favTVSeriesList;
    }

    public void setFavTVSeriesList(List<Integer> favTVSeriesList) {
        this.favTVSeriesList = favTVSeriesList;
    }

    public List<Integer> getRatedMovieList() {
        return ratedMovieList;
    }

    public void setRatedMovieList(List<Integer> ratedMovieList) {
        this.ratedMovieList = ratedMovieList;
    }

    public List<Integer> getRatedTVSeriesList() {
        return ratedTVSeriesList;
    }

    public void setRatedTVSeriesList(List<Integer> ratedTVSeriesList) {
        this.ratedTVSeriesList = ratedTVSeriesList;
    }

    public List<Integer> getWatchlistMovieList() {
        return watchlistMovieList;
    }

    public void setWatchlistMovieList(List<Integer> watchlistMovieList) {
        this.watchlistMovieList = watchlistMovieList;
    }

    public List<Integer> getWatchlistTVSeriesList() {
        return watchlistTVSeriesList;
    }

    public void setWatchlistTVSeriesList(List<Integer> watchlistTVSeriesList) {
        this.watchlistTVSeriesList = watchlistTVSeriesList;
    }

    public void addToFavoriteMoviesList(Integer id){
        favMovieList.add(id);
    }

    public void addToFavoriteTVSeriesList(Integer id){
        favTVSeriesList.add(id);
    }

    public void addToWatchlistMoviesList(Integer id){
        watchlistMovieList.add(id);
    }

    public void addToWatchlistTVSeriesList(Integer id){
        watchlistTVSeriesList.add(id);
    }

    public void addToRatedMoviesList(Integer id){
        ratedMovieList.add(id);
    }

    public void addToRatedTVSeriesList(Integer id){
        ratedTVSeriesList.add(id);
    }

    public void removeFromFavoriteMovieList(Integer id){
        favMovieList.remove(id);
    }

    public void removeFromFavoriteTVSeriesList(Integer id){
        favTVSeriesList.remove(id);
    }

    public void removeFromWatchlistMovieList(Integer id){
        watchlistMovieList.remove(id);
    }

    public void removeFromWatchlistTVSeriesList(Integer id){
        watchlistTVSeriesList.remove(id);
    }

    public void removeFromRatedMovieList(Integer id){
        ratedMovieList.remove(id);
    }

    public void removeFromRatedTVSeriesList(Integer id){
        ratedTVSeriesList.remove(id);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.favMovieList);
        dest.writeList(this.favTVSeriesList);
        dest.writeList(this.ratedMovieList);
        dest.writeList(this.ratedTVSeriesList);
        dest.writeList(this.watchlistMovieList);
        dest.writeList(this.watchlistTVSeriesList);
        dest.writeString(this.sessionId);
        dest.writeValue(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.name);
        dest.writeValue(this.includeAdult);
        dest.writeString(this.username);
    }

    public Account() {
    }

    protected Account(Parcel in) {
        this.favMovieList = new ArrayList<Integer>();
        in.readList(this.favMovieList, Integer.class.getClassLoader());
        this.favTVSeriesList = new ArrayList<Integer>();
        in.readList(this.favTVSeriesList, Integer.class.getClassLoader());
        this.ratedMovieList = new ArrayList<Integer>();
        in.readList(this.ratedMovieList, Integer.class.getClassLoader());
        this.ratedTVSeriesList = new ArrayList<Integer>();
        in.readList(this.ratedTVSeriesList, Integer.class.getClassLoader());
        this.watchlistMovieList = new ArrayList<Integer>();
        in.readList(this.watchlistMovieList, Integer.class.getClassLoader());
        this.watchlistTVSeriesList = new ArrayList<Integer>();
        in.readList(this.watchlistTVSeriesList, Integer.class.getClassLoader());
        this.sessionId = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.name = in.readString();
        this.includeAdult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.username = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
