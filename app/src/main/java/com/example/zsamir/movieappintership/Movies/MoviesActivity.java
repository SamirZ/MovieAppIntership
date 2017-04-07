package com.example.zsamir.movieappintership.Movies;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.zsamir.movieappintership.Adapters.MovieSectionsPagerAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

import io.realm.Realm;

public class MoviesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if(isNetworkAvailable())
            RealmUtils.getInstance().deleteAllMovies();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setBottomNavigationBar((AHBottomNavigation) findViewById(R.id.bottom_navigation_movies),1);

        setUpDrawer((DrawerLayout) findViewById(R.id.drawer_layout),toolbar);

        MovieSectionsPagerAdapter mSectionsPagerAdapter = new MovieSectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.movie_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserLogin(navigationView);
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
                if(isNetworkAvailable()) {
                    Intent i = new Intent(this, SearchActivity.class);
                    startActivity(i);
                }else{
                    showNoDataDialog();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent i = new Intent(this,LoginActivity.class);
            startActivityForResult(i,1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        });
    }

    @Override
    public void onNetworkAvailable() {
        super.onNetworkAvailable();
    }
}