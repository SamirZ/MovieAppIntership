package com.example.zsamir.movieappintership.NewsFeed;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.zsamir.movieappintership.Adapters.NewsFeedAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

public class NewsFeedActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        recyclerView = (RecyclerView) findViewById(R.id.news_feed_recyclerView);

        ReadRss readRss = new ReadRss(this, recyclerView);

        if(isNetworkAvailable()){
            readRss.execute();
            checkFirstRun();
        } else{
            if(!checkFirstRun())
                readNewsFeedFromRealm();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer((DrawerLayout) findViewById(R.id.drawer_layout_news_feed), toolbar);

        setBottomNavigationBar((AHBottomNavigation) findViewById(R.id.bottom_navigation_news_feed),0);



        navigationView = (NavigationView) findViewById(R.id.nav_view_news_feed);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserLogin(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_news_feed);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_news_feed);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
        if(isFirstRun){
            showLoginDialog();
        }
        return isFirstRun;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
        }

    }

    private void readNewsFeedFromRealm() {

        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(
                                RealmUtils.getInstance().readNewsFeedItemsFromRealm());

        recyclerView.setLayoutManager(new LinearLayoutManager(NewsFeedActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(newsFeedAdapter);
    }
}