package com.example.zsamir.movieappintership.Common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.AlertReceivers.MovieAlertReceiver;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.AlertReceivers.TVAlertReceiver;
import com.example.zsamir.movieappintership.R;

import java.util.Calendar;


public class SettingsActivity extends BaseActivity {

    private Boolean movieNotifyOn = false;
    private Boolean tvNotifyOn = false;

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

                    Calendar calendar = Calendar.getInstance();

                    //calendar.set(Calendar.HOUR_OF_DAY,0);

                    //calendar.set(Calendar.MINUTE,3);

                    //calendar.set(Calendar.SECOND,10);

                    Intent intent = new Intent(getApplicationContext(),TVAlertReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    if (isChecked) {
                        // TV NOTIFICATION ON
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("tvNotif", true).apply();
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                    }
                    else {
                        // TV NOTIFICATION OFF
                        getSharedPreferences("PREFERENCE", 0).edit().remove("tvNotif").apply();
                        alarmManager.cancel(pendingIntent);
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

                    Calendar calendar = Calendar.getInstance();

                    int i = calendar.get(Calendar.MINUTE);
                    i++;
                    if(i>60)
                        i=0;
                    calendar.set(Calendar.MINUTE,i);

                    //calendar.set(Calendar.HOUR_OF_DAY,0);

                    //calendar.set(Calendar.MINUTE,3);

                    //calendar.set(Calendar.SECOND,30);

                    Intent intent = new Intent(getApplicationContext(),MovieAlertReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),10,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    if (isChecked) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("movieNotif", true).apply();

                    }
                    else {
                        // MOVIE NOTIFICATION OFF
                        getSharedPreferences("PREFERENCE", 0).edit().remove("movieNotif").apply();
                        alarmManager.cancel(pendingIntent);
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
