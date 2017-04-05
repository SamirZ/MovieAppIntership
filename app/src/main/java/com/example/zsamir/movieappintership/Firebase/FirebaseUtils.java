package com.example.zsamir.movieappintership.Firebase;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.Adapters.CinemaMovieAdapter;
import com.example.zsamir.movieappintership.Adapters.SeatAdapter;
import com.example.zsamir.movieappintership.Cinema.ReservationActivity;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class FirebaseUtils {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    public void retrieveMovies(String title, final CinemaMovieAdapter cinemaMovieAdapter, final ArrayList<CinemaMovie> movies){

        if(databaseReference.child("Days").child(title)!=null)
            databaseReference.child("Days").child(title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    movies.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        if(child.hasChildren()) {
                            CinemaMovie r = child.getValue(CinemaMovie.class);
                            Log.d("MOVIE", r.toString());
                            if (!movies.contains(r))
                                movies.add(r);
                        }
                    }
                    if(cinemaMovieAdapter!=null)
                        cinemaMovieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    public void retrieveMovies(String title, final ArrayList<CinemaMovie> movies){

        if(databaseReference.child("Days").child(title)!=null)
            databaseReference.child("Days").child(title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    movies.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        if(child.hasChildren()) {
                            CinemaMovie r = child.getValue(CinemaMovie.class);
                            Log.d("MOVIE", r.toString());
                            if (!movies.contains(r))
                                movies.add(r);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    public void retrieveMovieNames(String title, final ArrayAdapter<String> adapter, final ArrayList<String> movies){

        if(databaseReference.child("Days").child(title)!=null)
            databaseReference.child("Days").child(title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    movies.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        if(child.hasChildren()) {
                            CinemaMovie r = child.getValue(CinemaMovie.class);
                            if (!movies.contains(r.getName()))
                                movies.add(r.getName());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    public void retrievePlayTime(final Context c , String day, String movieTitle, int id, final ArrayList<PlayTime> playTimes, final RecyclerView seatSelection, final TextView total){

        if(databaseReference.child("Days").child(day)!=null){
            databaseReference
                    .child("Days")
                    .child(day)
                    .child(movieTitle)
                    .child("playTimes")
                    .child(String.valueOf(id)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PlayTime t = dataSnapshot.getValue(PlayTime.class);
                    playTimes.add(t);

                    if(t!=null) {
                        ReservationActivity.selected = 0;
                        SeatAdapter seatAdapter = new SeatAdapter(t.getSeats(),c,total);

                        ReservationActivity.getSeatLocation().addAll(t.getSeats());

                        seatSelection.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        GridLayoutManager layoutManager = new GridLayoutManager(c, 11);
                        seatSelection.setLayoutManager(layoutManager);
                        seatSelection.setAdapter(seatAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    private void addToList(ArrayList<CinemaSeat> seats,String letter){
        for (int i = 1; i <= 11; i++) {

            String id = "";
            if(i<2){
                id = letter + String.valueOf(i);
            }else if(i<8){
                id = letter + String.valueOf(i-1);
            }else {
                id = letter + String.valueOf(i-2);
            }

            seats.add(new CinemaSeat(id,true));
        }
    }

    // Testing adding movies
    public void addDummyMoviesToFirebase(){

        ArrayList<CinemaSeat> seats = new ArrayList<>();

        for (int i = 0; i < 11; i++) {

            String id = "";
            if(i<2){
                id = "J" + String.valueOf(i);
            }else if(i<8){
                id = "J" + String.valueOf(i-1);
            }else {
                id = "J" + String.valueOf(i-2);
            }

            seats.add(new CinemaSeat(id,true));
        }
        addToList(seats,"I");
        addToList(seats,"H");
        addToList(seats,"G");
        addToList(seats,"F");
        addToList(seats,"E");
        addToList(seats,"D");
        addToList(seats,"C");
        addToList(seats,"B");
        addToList(seats,"A");


        for (int i = 2,j=8; i < seats.size(); i+=11,j+=11) {
            seats.get(i).setId("");
            seats.get(j).setId("");
        }

        seats.get(0).setId("");
        seats.get(10).setId("");

        for (CinemaSeat s:seats) {
            Log.d("SEAT",s.getId());
        }

        ArrayList<PlayTime> playTimes = new ArrayList<>();
        playTimes.add(new PlayTime("18:00",seats));
        playTimes.add(new PlayTime("20:00",seats));

        ArrayList<Movie> movies = RealmUtils.getInstance().readPopularMoviesFromRealm();

        ArrayList<CinemaMovie> cinemaMovies = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {

                String allGenres = "";

                if(movies.get(i).getTitle().equals("Arrival")){
                    allGenres = "Drama/Science-Fiction";
                }
                else if(movies.get(i).getTitle().equalsIgnoreCase("Hell or High Water")){
                    allGenres = "Western/Crime/Drama";
                }
                else if(movies.get(i).getTitle().equalsIgnoreCase("Zootopia")){
                    allGenres = "Animation/Adventure/Family/Comedy";
                }
                cinemaMovies.add(
                        new CinemaMovie(
                                movies.get(i).getId(),
                                movies.get(i).getTitle(),
                                allGenres,
                                movies.get(i).getVoteAverage(),
                                movies.get(i).getOverview(),
                                movies.get(i).getBackdropPath(),
                                movies.get(i).getOrgReleaseDate(),
                                playTimes));

            }

            ArrayList<PlayDay> days = new ArrayList<>();
            days.add(new PlayDay("3/4/2017","Monday",cinemaMovies));
            days.add(new PlayDay("4/4/2017","Tuesday",cinemaMovies));
            days.add(new PlayDay("5/4/2017","Wednesday",cinemaMovies));
            days.add(new PlayDay("6/4/2017","Thursday",cinemaMovies));
            days.add(new PlayDay("7/4/2017","Friday",cinemaMovies));
            days.add(new PlayDay("8/4/2017","Saturday",cinemaMovies));
            days.add(new PlayDay("9/4/2017","Sunday",cinemaMovies));

            for (int i=0;i<days.size();i++) {

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child("date")
                        .setValue(days.get(i).getDate());

                for (int j = 0; j < cinemaMovies.size(); j++) {

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("name")
                            .setValue(days.get(i).getMovies().get(j).getName());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("rating")
                            .setValue(days.get(i).getMovies().get(j).getRating());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("overview")
                            .setValue(days.get(i).getMovies().get(j).getOverview());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("id")
                            .setValue(days.get(i).getMovies().get(j).getId());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("genres")
                            .setValue(days.get(i).getMovies().get(j).getGenres());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("backdropPath")
                            .setValue(days.get(i).getMovies().get(j).getBackdropPath());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child("releaseDate")
                            .setValue(days.get(i).getMovies().get(j).getReleaseDate());

                    for (int l = 0; l < playTimes.size(); l++) {

                        databaseReference.child("Days")
                                .child(days.get(i).getName())
                                .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                                .child("playTimes")
                                .child(String.valueOf(l))
                                .child("time")
                                .setValue(playTimes.get(l).getTime());


                        for (int k = 0; k < seats.size(); k++) {

                            databaseReference.child("Days")
                                    .child(days.get(i).getName())
                                    .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                                    .child("playTimes")
                                    .child(String.valueOf(l))
                                    .child("seats")
                                    .child(String.valueOf(k))
                                    .child("id")
                                    .setValue(seats.get(k).getId());

                            databaseReference.child("Days")
                                    .child(days.get(i).getName())
                                    .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                                    .child("playTimes")
                                    .child(String.valueOf(l))
                                    .child("seats")
                                    .child(String.valueOf(k))
                                    .child("free")
                                    .setValue(seats.get(k).isFree());

                        }
                    }
                }
        }
    }

    public void occupySeats(String day, String movie, int time,int seatNum) {

        databaseReference.child("Days")
                .child(day)
                .child(movie)
                .child("playTimes")
                .child(String.valueOf(time))
                .child("seats")
                .child(String.valueOf(seatNum))
                .child("free")
                .setValue(false);
    }
}
