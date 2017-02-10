package com.example.zsamir.movieappintership.Common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Thread splashThread = new Thread(){

            @Override
            public void run(){
                try {
                    sleep(1500);
                    Intent i = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        splashThread.start();
    }
}
