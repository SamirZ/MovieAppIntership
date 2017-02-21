package com.example.zsamir.movieappintership.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zsamir.movieappintership.Fragments.HighestRatedMoviesFragment;
import com.example.zsamir.movieappintership.Fragments.LatestMoviesFragment;
import com.example.zsamir.movieappintership.Fragments.PopularMoviesFragment;

public class MovieSectionsPagerAdapter extends FragmentPagerAdapter {

    public MovieSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)
            return PopularMoviesFragment.getInstance();
        else if(position == 1)
            return LatestMoviesFragment.getInstance();
        else if(position == 2)
            return HighestRatedMoviesFragment.getInstance();
        return  null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Most Popular";
            case 1:
                return "Latest";
            case 2:
                return "Highest-Rated";
        }
        return null;
    }
}
