package com.example.zsamir.movieappintership.Common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zsamir.movieappintership.AlertReceiver;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SettingsActivity extends BaseActivity {

    private Boolean movieNotifyOn = false;
    private Boolean tvNotifyOn = false;
    private int tvNID = 11;
    private int movieNID = 22;

    private TextView name;
    private TextView username;
    private SwitchCompat movieSwitch;
    private SwitchCompat tvSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("movieNotif")) {
            movieNotifyOn = getSharedPreferences("PREFERENCE", 0).getBoolean("movieNotif", false);
        }
        if(sharedPreferences.contains("tvNotif")){
            tvNotifyOn = getSharedPreferences("PREFERENCE", 0).getBoolean("tvNotif", false);
        }

        setTitle(getString(R.string.action_settings));

        setUpViews();

        setMovieSwitch();

        setTVShowSwitch();

        setUser();

    }

    private void setUser() {
        name.setText(MovieAppApplication.getUser().getName());
        username.setText(MovieAppApplication.getUser().getUsername());
    }

    private void setTVShowSwitch() {

        tvSwitch.setChecked(tvNotifyOn);

        tvSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvNotifyOn = true;
                return false;
            }
        });

        tvSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (tvNotifyOn) {
                    tvNotifyOn = false;
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent alertIntent = new Intent(SettingsActivity.this, AlertReceiver.class);

                    if (isChecked) {
                        // TV NOTIFICATION ON
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("tvNotif", true).apply();

                        Long alertTime = new GregorianCalendar().getTimeInMillis()+10*1000;

                        Calendar calendar = Calendar.getInstance();
                        //calendar.set(Calendar.HOUR_OF_DAY, 18);
                        //calendar.set(Calendar.MINUTE, 00);
                        //calendar.set(Calendar.SECOND, 00);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , alertTime, PendingIntent.getBroadcast(SettingsActivity.this, tvNID, alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT));
                    }
                    else {
                        // TV NOTIFICATION OFF
                        alarmManager.cancel(PendingIntent.getBroadcast(SettingsActivity.this, tvNID, alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT));
                        getSharedPreferences("PREFERENCE", 0).edit().remove("tvNotif").apply();
                    }
                }
            }
        });
    }

    private void setMovieSwitch() {

        movieSwitch.setChecked(movieNotifyOn);

        movieSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                movieNotifyOn = true;
                return false;
            }
        });


        movieSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (movieNotifyOn) {
                    movieNotifyOn = false;
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent alertIntent = new Intent(SettingsActivity.this, AlertReceiver.class);
                    if (isChecked) {

                        Long alertTime = new GregorianCalendar().getTimeInMillis()+10*1000;

                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("movieNotif", true).apply();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 18);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , alertTime, PendingIntent.getBroadcast(SettingsActivity.this, movieNID, alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT));
                    }
                    else {
                        // MOVIE NOTIFICATION OFF
                        alarmManager.cancel(PendingIntent.getBroadcast(SettingsActivity.this, tvNID, alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT));
                        getSharedPreferences("PREFERENCE", 0).edit().remove("movieNotif").apply();
                    }
                }
            }
        });
    }

    private void setUpViews() {
        name = (TextView) findViewById(R.id.account_name);
        username = (TextView) findViewById(R.id.account_username);
        movieSwitch = (SwitchCompat) findViewById(R.id.account_movies_notification_switch);
        tvSwitch = (SwitchCompat) findViewById(R.id.account_tv_notification_switch);
    }
}
