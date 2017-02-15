package com.example.zsamir.movieappintership;

import android.app.Application;

import com.example.zsamir.movieappintership.LoginModules.Account;


public class MovieAppApplication extends Application {

    private static Account user;
    private static String type;

    public static String getType() {
        return type;
    }

    public static void setType(String type){
        MovieAppApplication.type = type;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Fabric.with(this, new Crashlytics());
        // development mode, turn off

    }

    public static boolean isUserLoggedIn(){
        return user!=null;
    }

    public static void setUser(Account user) {
        MovieAppApplication.user = user;
    }

    public static Account getUser() {
        return user;
    }

}
