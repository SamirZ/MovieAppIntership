package com.example.zsamir.movieappintership.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zsamir.movieappintership.Fragments.HighestRatedMoviesFragment;
import com.example.zsamir.movieappintership.Fragments.PopularMoviesFragment;

/**
 * Created by zsami on 06-Jan-17.
 */

public class MovieSectionsPagerAdapter extends FragmentPagerAdapter {

    public MovieSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)
            return PopularMoviesFragment.getInstance();
        else if(position == 1)
            return HighestRatedMoviesFragment.getInstance();
        return  null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Most Popular";
            case 1:
                return "Highest-Rated";
            case 2:
                return "Airing Today";
        }
        return null;
    }
}
