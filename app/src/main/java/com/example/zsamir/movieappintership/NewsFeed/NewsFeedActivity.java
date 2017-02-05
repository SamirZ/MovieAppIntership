package com.example.zsamir.movieappintership.NewsFeed;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Movies.MoviesActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesActivity;

import io.fabric.sdk.android.Fabric;

public class NewsFeedActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Fabric.with(this, new Crashlytics());

        recyclerView = (RecyclerView) findViewById(R.id.news_feed_recyclerView);

        ReadRss readRss = new ReadRss(this, recyclerView);
        readRss.execute();


        setBottomNavigationBar();
    }

    private void setBottomNavigationBar() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_news_feed);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.news_feed_name, R.mipmap.news_feed_icon, R.color.colorMovieItemText);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.movies_label, R.mipmap.movies_icon, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tv_series_name, R.mipmap.tv_shows_icon, R.color.colorMovieItemText);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#212121"));
        bottomNavigation.setAccentColor(Color.parseColor("#F8CA00"));
        bottomNavigation.setInactiveColor(Color.parseColor("#898885"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // 0 news feed
                // 1 movies
                if(position==1) {
                    startMoviesActivity();
                }
                // 2 tv series
                if(position==2) {
                    startTVSeriesActivity();
                }
                return false;
            }
        });
    }


    private void startTVSeriesActivity() {
        Intent intent = new Intent(NewsFeedActivity.this,TVSeriesActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMoviesActivity() {
        Intent intent = new Intent(NewsFeedActivity.this,MoviesActivity.class);
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
