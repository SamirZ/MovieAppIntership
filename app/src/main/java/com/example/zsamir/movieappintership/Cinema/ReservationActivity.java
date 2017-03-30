package com.example.zsamir.movieappintership.Cinema;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Firebase.FirebaseUtils;
import com.example.zsamir.movieappintership.Firebase.PlayTime;
import com.example.zsamir.movieappintership.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {

    private String day;
    private String time;
    private String date;
    private CinemaMovie cinemaMovie;
    ArrayList<PlayTime> playTime = new ArrayList<>();
    ArrayList<CinemaMovie> movies = new ArrayList<>();

    private Spinner movieSpinner;
    private Spinner dateSpinner;
    private Spinner peopleSpinner;

    private RecyclerView seatSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        if (getIntent().hasExtra("TIME")) {
            String[] split = getIntent().getStringExtra("TIME").split("/");
            time = split[0];
            day = split[1];
        }

        if (getIntent().hasExtra("MOVIE")) {
            cinemaMovie = getIntent().getParcelableExtra("MOVIE");
            setTitle(cinemaMovie.getName());
        }

        setUpViews();

        connectTimeToFirebase();

        setUpData();
        setPlayTime();


    }

    private void setUpData() {

        // Movie

        final ArrayList<String> movieNames = new ArrayList<>();
        final FirebaseUtils utils = new FirebaseUtils();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.selected_time_item, movieNames);

        utils.retrieveMovies(day,movies);
        utils.retrieveMovieNames(day,adapter,movieNames);

        adapter.setDropDownViewResource(R.layout.time_item);
        movieSpinner.setAdapter(adapter);
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cinemaMovie = movies.get(position);
                time = cinemaMovie.getPlayTimes().get(0).getTime();
                setPlayTime();
                connectTimeToFirebase();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        // People
        final ArrayList<String> numOfPeople = new ArrayList<>();
        numOfPeople.add(String.valueOf(1));
        numOfPeople.add(String.valueOf(2));
        numOfPeople.add(String.valueOf(3));
        numOfPeople.add(String.valueOf(4));

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.selected_time_item, numOfPeople);
        adapter1.setDropDownViewResource(R.layout.time_item);
        peopleSpinner.setAdapter(adapter1);
        peopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setPlayTime() {
        // Date
        final ArrayList<String> dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        getDayFromCalendar(calendar);
        dates.clear();
        for (PlayTime t:cinemaMovie.getPlayTimes()) {
            dates.add(date+" - "+t.getTime());
        }
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.selected_time_item, dates);
        adapter2.setDropDownViewResource(R.layout.time_item);
        dateSpinner.setAdapter(adapter2);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = dates.get(position).split(" - ")[1];
                connectTimeToFirebase();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDayFromCalendar(Calendar calendar) {
        if(day.equalsIgnoreCase("Monday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Tuesday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Wednesday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Thursday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Friday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Saturday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else if(day.equalsIgnoreCase("Sunday")){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = dateFormat.format(calendar.getTime());
        }
    }

    private void setUpViews() {
        movieSpinner = (Spinner) findViewById(R.id.movie_picker);
        dateSpinner = (Spinner) findViewById(R.id.date_picker);
        peopleSpinner = (Spinner) findViewById(R.id.num_picker);
        seatSelection = (RecyclerView) findViewById(R.id.seat_selection_recyclerView);
    }

    private void connectTimeToFirebase() {

        FirebaseUtils firebaseUtils = new FirebaseUtils();
        int id = -1;

        for (PlayTime t : cinemaMovie.getPlayTimes()) {
            if (t.getTime().equals(time)) {
                id = cinemaMovie.getPlayTimes().indexOf(t);
            }
        }

        firebaseUtils.retrievePlayTime(this,day, cinemaMovie.getName(), id, playTime, seatSelection);

    }
}
