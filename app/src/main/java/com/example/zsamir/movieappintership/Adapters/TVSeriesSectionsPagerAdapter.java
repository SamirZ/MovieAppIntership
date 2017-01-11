package com.example.zsamir.movieappintership.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zsamir.movieappintership.Fragments.AiringTodayTVSeriesFragment;
import com.example.zsamir.movieappintership.Fragments.HighestRatedTVSeriesFragment;
import com.example.zsamir.movieappintership.Fragments.PopularTVSeriesFragment;

public class TVSeriesSectionsPagerAdapter extends FragmentPagerAdapter {

    public TVSeriesSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return PopularTVSeriesFragment.getInstance();
        else if(position == 1)
            return HighestRatedTVSeriesFragment.getInstance();
        else if(position == 2)
            return AiringTodayTVSeriesFragment.getInstance();
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
                return "Highest-Rated";
            case 2:
                return "Airing Today";
        }
        return null;
    }
}
