package com.example.zsamir.movieappintership.Movies;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.Adapters.MovieSectionsPagerAdapter;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesActivity;

import io.fabric.sdk.android.Fabric;

public class MoviesActivity extends AppCompatActivity {

    private MovieSectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private AHBottomNavigation bottomNavigation;
    //private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Fabric.with(this, new Crashlytics());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBottomNavigationBar();

        mSectionsPagerAdapter = new MovieSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.movie_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setBottomNavigationBar() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_movies);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.news_feed_name, R.drawable.ic_menu_share, R.color.colorMovieItemText);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.movies_label, R.drawable.ic_menu_camera, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tv_series_name, R.drawable.ic_menu_slideshow, R.color.colorMovieItemText);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#212121"));
        bottomNavigation.setAccentColor(Color.parseColor("#F8CA00"));
        bottomNavigation.setInactiveColor(Color.parseColor("#898885"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // 0 news feed
                if(position==0) {
                    startNewsFeedActivity();
                }
                // 1 movies
                // 2 tv series
                if(position==2) {
                    startTVSeriesActivity();
                }
                return false;
            }
        });
    }


    private void startNewsFeedActivity() {
        Intent intent = new Intent(MoviesActivity.this,NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void startTVSeriesActivity() {
        Intent intent = new Intent(MoviesActivity.this,TVSeriesActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

}
