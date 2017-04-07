package com.example.zsamir.movieappintership.API;

import com.example.zsamir.movieappintership.BuildConfig;

/**
 * Created by zsami on 03-Apr-17.
 */

public class StripeApiHandler {

    private static StripeApiHandler sInstance;

    private static final String BASE_URL = "https://api.stripe.com";

    public StripeApiHandler() {

    }

    public static StripeApiHandler getInstance() {

        if (sInstance == null) {
            sInstance = new StripeApiHandler();
        }

        return sInstance;
    }
}
