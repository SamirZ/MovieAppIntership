package com.example.zsamir.movieappintership.Cinema;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Adapters.CinemaMovieAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Firebase.FirebaseUtils;
import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class CinemaActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer((DrawerLayout) findViewById(R.id.drawer_layout_cinema),toolbar);

        checkUserLogin((NavigationView)findViewById(R.id.nav_view));

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container_cinema);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.cinema_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cinema);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class CinemaFragment extends Fragment {

        private CinemaMovieAdapter cinemaMovieAdapter;
        ArrayList<CinemaMovie> movies = new ArrayList<>();

        public CinemaFragment() {
        }

        public static CinemaFragment newInstance(int sectionNumber) {
            CinemaFragment fragment = new CinemaFragment();
            Bundle args = new Bundle();
            String title;
            switch (sectionNumber){
                case 0:
                    title = "Today";
                    break;
                case 1:
                    title = "Friday";
                    break;
                case 2:
                    title = "Saturday";
                    break;
                case 3:
                    title = "Sunday";
                    break;
                case 4:
                    title = "Monday";
                    break;
                case 5:
                    title = "Tuesday";
                    break;
                case 6:
                    title = "Wednesday";
                    break;
                case 7:
                    title = "Thursday";
                    break;
                default:
                    title = "Today";
                    break;
            }
            args.putString("TITLE",title);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_cinema, container, false);

            RecyclerView mRecyclerView = (RecyclerView)rootView.findViewById(R.id.cinema_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());


            cinemaMovieAdapter = new CinemaMovieAdapter(this,movies);
            movies.clear();
            getMovies(getArguments().getString("TITLE"),movies);
            cinemaMovieAdapter.notifyDataSetChanged();
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(cinemaMovieAdapter);


            return rootView;
        }

        @SuppressLint("SwitchIntDef")
        private void getMovies(String title, ArrayList<CinemaMovie> cinemaMovies) {
            FirebaseUtils firebaseUtils = new FirebaseUtils();

            if(title.equalsIgnoreCase("today")){
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                switch (day) {
                    case Calendar.MONDAY:
                        title = "Monday";
                        break;
                    case Calendar.TUESDAY:
                        title = "Tuesday";
                        break;
                    case Calendar.WEDNESDAY:
                        title = "Wednesday";
                        break;
                    case Calendar.THURSDAY:
                        title = "Thursday";
                        break;
                    case Calendar.FRIDAY:
                        title = "Friday";
                        break;
                    case Calendar.SATURDAY:
                        title = "Saturday";
                        break;
                    case Calendar.SUNDAY:
                        title = "Sunday";
                        break;
                }
            }

            firebaseUtils.retrieveMovies(title, cinemaMovieAdapter,cinemaMovies);
            cinemaMovieAdapter.notifyDataSetChanged();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CinemaFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODAY";
                case 1:
                    return "FRIDAY";
                case 2:
                    return "SATURDAY";
                case 3:
                    return "SUNDAY";
                case 4:
                    return "MONDAY";
                case 5:
                    return "TUESDAY";
                case 6:
                    return "WEDNESDAY";
                case 7:
                    return "THURSDAY";
            }
            return null;
        }
    }
}
