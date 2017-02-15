package com.example.zsamir.movieappintership.LoginModules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("status_message")
    @Expose
    public String statusMessage;

}