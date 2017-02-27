package com.example.zsamir.movieappintership;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.zsamir.movieappintership.Common.SettingsActivity;
import com.example.zsamir.movieappintership.Common.UserListsActivity;
import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.Movies.MoviesActivity;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesActivity;

public class BaseActivity extends AppCompatActivity{

    private NavigationView navigationView;

    public void setUpDrawer(DrawerLayout drawer,Toolbar toolbar){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void showLoginDialog(){
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this, R.style.MyDialogTheme)
                .setTitle(getString(R.string.login_to))
                .setMessage(getString(R.string.login_question) + "\n" + "\n" + "\n" + "\n" + "\n")
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with login
                        Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                        startActivityForResult(i, 1);

                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // set pref to no
                    }
                })
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.colorAccent));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.colorText));

        //Delete user from shared preferences
    }

    public void checkUserLogin(NavigationView navigationView) {
        this.navigationView = navigationView;

        if(MovieAppApplication.isUserLoggedIn()){
            TextView name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.person_name);
            name.setText(MovieAppApplication.getUser().getName());
            navigationView.getMenu().clear();
            showButtons();
            animateAllButtons();
            setDefaultColors();

        }else{
            hideButtons();
        }
    }

    private void showButtons() {
        navigationView.getHeaderView(0).findViewById(R.id.favorites).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.logout_nav).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.your_watchlist).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.your_ratings).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.settings_nav).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.more_nav).setVisibility(View.VISIBLE);
        navigationView.getHeaderView(0).findViewById(R.id.your_list).setVisibility(View.VISIBLE);
    }

    private void hideButtons() {
        navigationView.getHeaderView(0).findViewById(R.id.favorites).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.logout_nav).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.your_watchlist).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.your_ratings).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.settings_nav).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.more_nav).setVisibility(View.GONE);
        navigationView.getHeaderView(0).findViewById(R.id.your_list).setVisibility(View.GONE);
    }

    private void animateAllButtons() {
        animateButton(navigationView.getHeaderView(0).findViewById(R.id.logout_nav), navigationView.getHeaderView(0).findViewById(R.id.logout_nav_icon),
                (TextView) navigationView.getHeaderView(0).findViewById(R.id.logout_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48)));

        animateButton(navigationView.getHeaderView(0).findViewById(R.id.settings_nav), navigationView.getHeaderView(0).findViewById(R.id.settings_nav_icon),
                (TextView) navigationView.getHeaderView(0).findViewById(R.id.settings_nav_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy)));

        animateButton(navigationView.getHeaderView(0).findViewById(R.id.your_watchlist), navigationView.getHeaderView(0).findViewById(R.id.your_watchlist_icon),
                (TextView) navigationView.getHeaderView(0).findViewById(R.id.your_watchlist_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled)));

        animateButton(navigationView.getHeaderView(0).findViewById(R.id.favorites), navigationView.getHeaderView(0).findViewById(R.id.favourites_icon),
                (TextView) navigationView.getHeaderView(0).findViewById(R.id.favourites_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled)));

        animateButton(navigationView.getHeaderView(0).findViewById(R.id.your_ratings), navigationView.getHeaderView(0).findViewById(R.id.your_ratings_icon),
                (TextView) navigationView.getHeaderView(0).findViewById(R.id.your_ratings_text), DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star)));

    }

    private void setDefaultColors() {

        View like = navigationView.getHeaderView(0).findViewById(R.id.favourites_icon);
        View watchlist = navigationView.getHeaderView(0).findViewById(R.id.your_watchlist_icon);
        View ratings = navigationView.getHeaderView(0).findViewById(R.id.your_ratings_icon);
        View settings = navigationView.getHeaderView(0).findViewById(R.id.settings_nav_icon);
        View logout = navigationView.getHeaderView(0).findViewById(R.id.logout_nav_icon);

        Drawable likeIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.like_filled));
        DrawableCompat.setTint(likeIcon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));

        Drawable watchlistIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.bookmark_filled));
        DrawableCompat.setTint(watchlistIcon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));

        Drawable ratingsIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.star));
        DrawableCompat.setTint(ratingsIcon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));

        Drawable settingsIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.group_12_copy));
        DrawableCompat.setTint(settingsIcon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));

        Drawable logoutIcon = DrawableCompat.wrap(ContextCompat.getDrawable(this,R.drawable.redo_48));
        DrawableCompat.setTint(logoutIcon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));

        like.setBackground(likeIcon);
        watchlist.setBackground(watchlistIcon);
        ratings.setBackground(ratingsIcon);
        settings.setBackground(settingsIcon);
        logout.setBackground(logoutIcon);

    }

    public void animateButton(final View layout, final View iconRef, final TextView text, final Drawable icon){


        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN ) {

                    DrawableCompat.setTint(icon, ContextCompat.getColor(BaseActivity.this,R.color.colorAccent));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(BaseActivity.this,R.color.colorAccent));
                    layout.setBackgroundColor(Color.parseColor("#4a4b4c"));

                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_BUTTON_RELEASE) {

                    DrawableCompat.setTint(icon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));
                    layout.setBackgroundColor(Color.parseColor("#27282a"));

                    if(layout.equals(navigationView.getHeaderView(0).findViewById(R.id.logout_nav))){
                        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this,R.style.MyDialogTheme)
                                .setTitle(getString(R.string.logout))
                                .setMessage(getString(R.string.logout_question)+"\n"+"\n"+"\n"+"\n"+"\n")
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with logout
                                        hideButtons();
                                        TextView t = (TextView)navigationView.getHeaderView(0).findViewById(R.id.person_name);
                                        t.setText("");
                                        MovieAppApplication.setUser(null);
                                        navigationView.inflateMenu(R.menu.login_drawer);
                                        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
                                        sharedPreferences.edit().remove("USER").apply();
                                        sharedPreferences.edit().remove("PASSWORD").apply();

                                        if(sharedPreferences.contains("movieNotif"))
                                            sharedPreferences.edit().remove("movieNotif").apply();

                                        if(sharedPreferences.contains("tvNotif"))
                                            sharedPreferences.edit().remove("tvNotif").apply();

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
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(BaseActivity.this,R.color.colorText));
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(BaseActivity.this,R.color.colorAccent));
                    }else if(layout.equals(navigationView.getHeaderView(0).findViewById(R.id.favorites))){

                        Intent i = new Intent(BaseActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","FAVORITES");
                        startActivity(i);

                    }else if(layout.equals(navigationView.getHeaderView(0).findViewById(R.id.your_watchlist))){

                        Intent i = new Intent(BaseActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","WATCHLIST");
                        startActivity(i);

                    }else if(layout.equals(navigationView.getHeaderView(0).findViewById(R.id.your_ratings))){

                        Intent i = new Intent(BaseActivity.this, UserListsActivity.class);
                        i.putExtra("TYPE","RATINGS");
                        startActivity(i);

                    }
                    else if(layout.equals(navigationView.getHeaderView(0).findViewById(R.id.settings_nav))){

                        Intent i = new Intent(BaseActivity.this, SettingsActivity.class);
                        startActivity(i);

                    }

                    BaseActivity.this.onBackPressed();
                }else if (event.getAction()== MotionEvent.ACTION_CANCEL){

                    DrawableCompat.setTint(icon, ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));
                    iconRef.setBackground(icon);
                    text.setTextColor(ContextCompat.getColor(BaseActivity.this,R.color.colorMovieItemText));
                    layout.setBackgroundColor(Color.parseColor("#27282a"));
                }

                return false;
            }
        });
    }

    public void startNewsFeedActivity() {
        Intent intent = new Intent(this,NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }

    public void startMoviesActivity() {
        Intent intent = new Intent(this,MoviesActivity.class);
        startActivity(intent);
        finish();
    }

    public void startTVSeriesActivity() {
        Intent intent = new Intent(this,TVSeriesActivity.class);
        startActivity(intent);
        finish();
    }

    public void setBottomNavigationBar(AHBottomNavigation bottomNavigation,final int p) {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.news_feed_name, R.drawable.news_feed_icon, R.color.colorMovieItemText);
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

        bottomNavigation.setCurrentItem(p);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // 0 news feed
                if(position==0 && position!=p) {
                    startNewsFeedActivity();
                }
                // 1 movies
                if(position==1 && position!=p) {
                    startMoviesActivity();
                }
                // 2 tv series
                if(position==2 && position!=p)
                    startTVSeriesActivity();
                return false;
            }
        });
    }

}
