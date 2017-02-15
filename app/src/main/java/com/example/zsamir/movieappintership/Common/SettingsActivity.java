package com.example.zsamir.movieappintership.Common;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;

public class SettingsActivity extends BaseActivity {

    private Boolean movieNotifyOn = false;
    private Boolean tvNotifyOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(getString(R.string.action_settings));

        final TextView name = (TextView) findViewById(R.id.account_name);
        final TextView username = (TextView) findViewById(R.id.account_username);
        SwitchCompat movieSwitch = (SwitchCompat) findViewById(R.id.account_movies_notification_switch);
        SwitchCompat tvSwitch = (SwitchCompat) findViewById(R.id.account_tv_notification_switch);

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
                    if (isChecked) {
                        // MOVIE NOTIFICATION ON
                    }
                    else {
                        // MOVIE NOTIFICATION OFF
                    }
                }
            }
        });

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
                    if (isChecked) {
                        // TV NOTIFICATION ON
                    }
                    else {
                        // TV NOTIFICATION OFF
                    }
                }
            }
        });

        name.setText(MovieAppApplication.getUser().getName());
        username.setText(MovieAppApplication.getUser().getUsername());

    }
}
