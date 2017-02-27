package com.example.zsamir.movieappintership.Common;

import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Fragments.ListMoviesFragment;
import com.example.zsamir.movieappintership.Fragments.ListTVSeriesFragment;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.Widgets.NonSwipeableViewPager;

public class UserListsActivity extends BaseActivity {

    private NonSwipeableViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lists);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);

        setType();

    }

    private void setType() {
        if(MovieAppApplication.isUserLoggedIn()){

            if(getIntent().hasExtra("TYPE")){
                String type = getIntent().getStringExtra("TYPE");

                if(type.equalsIgnoreCase("FAVORITES")){
                    setTitle(getString(R.string.favourites_label));
                    MovieAppApplication.setType("FAVORITES");
                    mViewPager.setCurrentItem(0);
                }
                if(type.equalsIgnoreCase("WATCHLIST")){
                    setTitle(getString(R.string.watchlist_label));
                    MovieAppApplication.setType("WATCHLIST");
                    mViewPager.setCurrentItem(0);
                }
                if(type.equalsIgnoreCase("RATINGS")){
                    setTitle(getString(R.string.ratings_label));
                    MovieAppApplication.setType("RATINGS");
                    mViewPager.setCurrentItem(0);
                }
            }

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                return ListMoviesFragment.newInstance();
            } else{
                return ListTVSeriesFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MOVIES";
                case 1:
                    return "TV SHOWS";
            }
            return null;
        }
    }
}
