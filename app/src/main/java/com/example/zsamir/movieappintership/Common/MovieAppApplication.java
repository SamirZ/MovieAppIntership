package com.example.zsamir.movieappintership.Common;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.LoginModules.Account;

import io.fabric.sdk.android.Fabric;

public class MovieAppApplication extends Application {

    public Account user;

    private static MovieAppApplication instance = null;


    public MovieAppApplication() {
    }

    public static MovieAppApplication getInstance() {
        if(instance == null) {
            instance = new MovieAppApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        // development mode, turn off

    }

    public boolean isUserLoggedIn(){
        return user!=null;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getUser() {
        return user;
    }
}
