package com.example.zsamir.movieappintership.Movies;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.zsamir.movieappintership.Adapters.MovieSectionsPagerAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Common.SettingsActivity;
import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesActivity;
import com.example.zsamir.movieappintership.Common.UserListsActivity;

public class MoviesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOGGED_IN = 1;
    private NavigationView navigationView;
    private View headerView;

    //private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBottomNavigationBar();

        MovieSectionsPagerAdapter mSectionsPagerAdapter = new MovieSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.movie_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

        checkUserLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MovieAppApplication.isUserLoggedIn()) {

            View header=navigationView.getHeaderView(0);
            TextView name = (TextView)header.findViewById(R.id.person_name);
            name.setText(MovieAppApplication.getUser().getName());
            navigationView.getMenu().clear(); //clear old inflated items.
            View headerView = navigationView.getHeaderView(0);
            headerView.findViewById(R.id.favorites).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.logout_nav).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.your_watchlist).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.your_ratings).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.settings_nav).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.more_nav).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.your_list).setVisibility(View.VISIBLE);

            animateButton(headerView.findViewById(R.id.logout_nav), headerView.findViewById(R.id.logout_nav_icon),
                    (TextView) headerView.findViewById(R.id.logout_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48)));

            animateButton(headerView.findViewById(R.id.settings_nav), headerView.findViewById(R.id.settings_nav_icon),
                    (TextView) headerView.findViewById(R.id.settings_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy)));

            animateButton(headerView.findViewById(R.id.your_watchlist), headerView.findViewById(R.id.your_watchlist_icon),
                    (TextView) headerView.findViewById(R.id.your_watchlist_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled)));

            animateButton(headerView.findViewById(R.id.favorites), headerView.findViewById(R.id.favourites_icon),
                    (TextView) headerView.findViewById(R.id.favourites_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled)));

            animateButton(headerView.findViewById(R.id.your_ratings), headerView.findViewById(R.id.your_ratings_icon),
                    (TextView) headerView.findViewById(R.id.your_ratings_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star)));

            setDefaultColors();
        }
    }

    private void checkUserLogin() {
        if(MovieAppApplication.isUserLoggedIn()){
            View header=navigationView.getHeaderView(0);
            TextView name = (TextView)header.findViewById(R.id.person_name);
            name.setText(MovieAppApplication.getUser().getName());
            navigationView.getMenu().clear(); //clear old inflated items.

            animateButton(headerView.findViewById(R.id.logout_nav), headerView.findViewById(R.id.logout_nav_icon),
                    (TextView) headerView.findViewById(R.id.logout_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48)));

            animateButton(headerView.findViewById(R.id.settings_nav), headerView.findViewById(R.id.settings_nav_icon),
                    (TextView) headerView.findViewById(R.id.settings_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy)));

            animateButton(headerView.findViewById(R.id.your_watchlist), headerView.findViewById(R.id.your_watchlist_icon),
                    (TextView) headerView.findViewById(R.id.your_watchlist_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled)));

            animateButton(headerView.findViewById(R.id.favorites), headerView.findViewById(R.id.favourites_icon),
                    (TextView) headerView.findViewById(R.id.favourites_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled)));

            animateButton(headerView.findViewById(R.id.your_ratings), headerView.findViewById(R.id.your_ratings_icon),
                    (TextView) headerView.findViewById(R.id.your_ratings_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star)));


            setDefaultColors();
        }else{
            View headerView =  navigationView.getHeaderView(0);
            headerView.findViewById(R.id.favorites).setVisibility(View.GONE);
            headerView.findViewById(R.id.logout_nav).setVisibility(View.GONE);
            headerView.findViewById(R.id.your_watchlist).setVisibility(View.GONE);
            headerView.findViewById(R.id.your_ratings).setVisibility(View.GONE);
            headerView.findViewById(R.id.settings_nav).setVisibility(View.GONE);
            headerView.findViewById(R.id.more_nav).setVisibility(View.GONE);
            headerView.findViewById(R.id.your_list).setVisibility(View.GONE);
        }
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

    private void setBottomNavigationBar() {

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_movies);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.news_feed_name, R.drawable.news_feed_icon, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.movies_label, R.drawable.movies_icon, R.color.colorMovieItemText);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tv_series_name, R.drawable.tv_shows_icon, R.color.colorMovieItemText);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#80212121"));
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
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Intent i = new Intent(this,SearchActivity.class);

                //Transfer login

                startActivity(i);
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
            startActivityForResult(i,LOGGED_IN);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGGED_IN) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                View header=navigationView.getHeaderView(0);
                TextView name = (TextView)header.findViewById(R.id.person_name);
                name.setText(MovieAppApplication.getUser().getName());
                navigationView.getMenu().clear(); //clear old inflated items.
                View headerView = navigationView.getHeaderView(0);
                headerView.findViewById(R.id.favorites).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.logout_nav).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.your_watchlist).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.your_ratings).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.settings_nav).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.more_nav).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.your_list).setVisibility(View.VISIBLE);

                animateButton(headerView.findViewById(R.id.logout_nav), headerView.findViewById(R.id.logout_nav_icon),
                        (TextView) headerView.findViewById(R.id.logout_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48)));

                animateButton(headerView.findViewById(R.id.settings_nav), headerView.findViewById(R.id.settings_nav_icon),
                        (TextView) headerView.findViewById(R.id.settings_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy)));

                animateButton(headerView.findViewById(R.id.your_watchlist), headerView.findViewById(R.id.your_watchlist_icon),
                        (TextView) headerView.findViewById(R.id.your_watchlist_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled)));

                animateButton(headerView.findViewById(R.id.favorites), headerView.findViewById(R.id.favourites_icon),
                        (TextView) headerView.findViewById(R.id.favourites_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled)));

                animateButton(headerView.findViewById(R.id.your_ratings), headerView.findViewById(R.id.your_ratings_icon),
                        (TextView) headerView.findViewById(R.id.your_ratings_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star)));

                setDefaultColors();
            }
        }
    }

    private void setDefaultColors() {

        View like = headerView.findViewById(R.id.favourites_icon);
        Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled));
        DrawableCompat.setTint(likeIcon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
        like.setBackground(likeIcon);

        View watchlist = headerView.findViewById(R.id.your_watchlist_icon);
        Drawable watchlistIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled));
        DrawableCompat.setTint(watchlistIcon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
        watchlist.setBackground(watchlistIcon);

        View ratings = headerView.findViewById(R.id.your_ratings_icon);
        Drawable ratingsIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star));
        DrawableCompat.setTint(ratingsIcon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
        ratings.setBackground(ratingsIcon);

        View settings = headerView.findViewById(R.id.settings_nav_icon);
        Drawable settingsIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy));
        DrawableCompat.setTint(settingsIcon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
        settings.setBackground(settingsIcon);

        View logout = headerView.findViewById(R.id.logout_nav_icon);
        Drawable logoutIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48));
        DrawableCompat.setTint(logoutIcon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
        logout.setBackground(logoutIcon);

    }

    public void animateButton(final View layout, final View iconRef, final TextView text, final Drawable icon){


        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN ) {

                    DrawableCompat.setTint(icon, ContextCompat.getColor(MoviesActivity.this,R.color.colorAccent));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(MoviesActivity.this,R.color.colorAccent));
                    layout.setBackgroundColor(Color.parseColor("#4a4b4c"));

                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_BUTTON_RELEASE) {

                    DrawableCompat.setTint(icon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
                    layout.setBackgroundColor(Color.parseColor("#27282a"));

                    if(layout.equals(headerView.findViewById(R.id.logout_nav))){
                        AlertDialog dialog = new AlertDialog.Builder(MoviesActivity.this,R.style.MyDialogTheme)
                                .setTitle(getString(R.string.logout))
                                .setMessage(getString(R.string.logout_question)+"\n"+"\n"+"\n"+"\n"+"\n")
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with logout
                                        View headerView =  navigationView.getHeaderView(0);
                                        headerView.findViewById(R.id.favorites).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.logout_nav).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.your_watchlist).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.your_ratings).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.settings_nav).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.more_nav).setVisibility(View.GONE);
                                        headerView.findViewById(R.id.your_list).setVisibility(View.GONE);
                                        TextView t = (TextView)headerView.findViewById(R.id.person_name);
                                        t.setText("");
                                        MovieAppApplication.setUser(null);
                                        navigationView.inflateMenu(R.menu.login_drawer);

                                        //Restart activity
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                recreate();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .show();
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(MoviesActivity.this,R.color.colorAccent));
                    }else if(layout.equals(headerView.findViewById(R.id.favorites))){

                        Intent i = new Intent(MoviesActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","FAVORITES");
                        startActivity(i);

                    }else if(layout.equals(headerView.findViewById(R.id.your_watchlist))){

                        Intent i = new Intent(MoviesActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","WATCHLIST");
                        startActivity(i);

                    }else if(layout.equals(headerView.findViewById(R.id.your_ratings))){

                        Intent i = new Intent(MoviesActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","RATINGS");
                        startActivity(i);

                    }else if(layout.equals(headerView.findViewById(R.id.settings_nav))){

                        Intent i = new Intent(MoviesActivity.this, SettingsActivity.class);
                        startActivity(i);

                    }

                    MoviesActivity.this.onBackPressed();
                }else if (event.getAction()== MotionEvent.ACTION_CANCEL){

                    DrawableCompat.setTint(icon, ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(MoviesActivity.this,R.color.colorMovieItemText));
                    layout.setBackgroundColor(Color.parseColor("#27282a"));
                }

                return false;
            }
        });
    }
}