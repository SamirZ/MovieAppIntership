package com.example.zsamir.movieappintership.Cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaMovie;
import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.Firebase.FirebaseUtils;
import com.example.zsamir.movieappintership.Firebase.PlayTime;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReservationActivity extends BaseActivity {

    private String day;
    private String time;
    private int playTimeId;
    private String date;

    private CinemaMovie cinemaMovie;
    private ArrayList<PlayTime> playTime = new ArrayList<>();
    private ArrayList<CinemaMovie> movies = new ArrayList<>();

    private Spinner movieSpinner;
    private Spinner dateSpinner;
    private Spinner peopleSpinner;

    private RecyclerView seatSelection;

    public static int selected = 0;
    public static int maxSelected = 1;
    private RelativeLayout checkout;
    private TextView totalPrice;

    private String ticketType = "Adult";

    private static ArrayList<String> seatIDs = new ArrayList<>();

    private static ArrayList<CinemaSeat> seatLocation = new ArrayList<>();

    public static ArrayList<String> getSeatIDs() {
        return seatIDs;
    }



    public static int getMaxSelected() {
        return maxSelected;
    }

    public static int getSelected() {
        return selected;
    }

    public static void setSelected(int selected) {
        ReservationActivity.selected = selected;
    }

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

        String price = getString(R.string.total) + " $" + String.valueOf(selected * 20);
        totalPrice.setText(price);

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
                selected = 0;
                setPlayTime();
                seatIDs.clear();
                seatLocation.clear();
                connectTimeToFirebase();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        // Tickets
        final ArrayList<String> tickets = new ArrayList<>();
        tickets.add(String.valueOf(1)+" Adult");
        tickets.add(String.valueOf(2)+" Adults");
        tickets.add(String.valueOf(3)+" Adults");
        tickets.add(String.valueOf(4)+" Adults");

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.selected_time_item, tickets);
        adapter1.setDropDownViewResource(R.layout.time_item);
        peopleSpinner.setAdapter(adapter1);
        peopleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maxSelected = Integer.parseInt(tickets.get(position).split(" ")[0]);
                ticketType = tickets.get(position).split(" ")[1];
                selected = 0;
                seatIDs.clear();
                seatLocation.clear();
                connectTimeToFirebase();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatIDs.size() == maxSelected) {
                    String ids = "";
                    for (String i : seatIDs) {
                        ids = ids.concat(i + ",");
                    }
                    ids = ids.substring(0, ids.length() - 1);

                    String s = MovieAppApplication.getUser().getName() + "~" +
                            cinemaMovie.getName() + " (" + cinemaMovie.getReleaseYear() + ")~" +
                            date + " - " + time + "~" +
                            String.valueOf(selected) +" "+ ticketType+"~" +
                            ids + "~" +
                            + selected * 20.00+"~"+
                            playTimeId+"~"+
                            day;
                    Intent i = new Intent(ReservationActivity.this, SummaryActivity.class);
                    i.putExtra("CONTENT", s);
                    startActivity(i);
                }
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
                selected = 0;
                seatIDs.clear();
                seatLocation.clear();
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
        checkout = (RelativeLayout) findViewById(R.id.checkoutLayout);

        totalPrice = (TextView)findViewById(R.id.total);
    }

    private void connectTimeToFirebase() {

        FirebaseUtils firebaseUtils = new FirebaseUtils();
        playTimeId = -1;

        for (PlayTime t : cinemaMovie.getPlayTimes()) {
            if (t.getTime().equals(time)) {
                playTimeId = cinemaMovie.getPlayTimes().indexOf(t);
            }
        }

        firebaseUtils.retrievePlayTime(this,day, cinemaMovie.getName(), playTimeId, playTime, seatSelection,totalPrice);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public static ArrayList<CinemaSeat> getSeatLocation() {
        return seatLocation;
    }
}
