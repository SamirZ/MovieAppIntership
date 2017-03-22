package com.example.zsamir.movieappintership.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieReview extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("author")
    @Expose
    private String author = null;
    @SerializedName("content")
    @Expose
    private String content = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MovieReview() {
    }

    public MovieReview(MovieReview mr) {
        this.id = mr.id;
        this.author = mr.author;
        this.content = mr.content;
    }

}
