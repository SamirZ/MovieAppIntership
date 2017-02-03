package com.example.zsamir.movieappintership.TVSeries;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.Adapters.TVSeriesSectionsPagerAdapter;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Movies.MoviesActivity;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;

import io.fabric.sdk.android.Fabric;

public class TVSeriesActivity extends AppCompatActivity {

    private TVSeriesSectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries);
        Fabric.with(this, new Crashlytics());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBottomNavigationBar();

        mSectionsPagerAdapter = new TVSeriesSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tv_series_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // Set up the ViewPager with the sections adapterr

    }

    private void setBottomNavigationBar() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_tv_series);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.news_feed_name, R.mipmap.news_feed_icon, R.color.colorMovieItemText);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.movies_label, R.mipmap.movies_icon, R.color.colorMovieItemText);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tv_series_name, R.mipmap.tv_shows_icon, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#212121"));
        bottomNavigation.setAccentColor(Color.parseColor("#F8CA00"));
        bottomNavigation.setInactiveColor(Color.parseColor("#898885"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // 0 news feed
                if(position==0) {
                    startNewsFeedActivity();
                }
                // 1 movies
                if(position==1) {
                    startMoviesActivity();
                }
                // 2 tv series
                return false;
            }
        });
    }


    private void startNewsFeedActivity() {
        Intent intent = new Intent(TVSeriesActivity.this,NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMoviesActivity() {
        Intent intent = new Intent(TVSeriesActivity.this,MoviesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Intent i = new Intent(this,SearchActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
