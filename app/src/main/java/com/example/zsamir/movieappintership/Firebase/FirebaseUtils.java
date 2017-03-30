package com.example.zsamir.movieappintership.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUtils {

    FirebaseDatabase mRef = FirebaseDatabase.getInstance();

    public void doSomething(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("Movie").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addDummyMoviesToFirebase(){

        ArrayList<CinemaSeat> seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            seats.add(new CinemaSeat(i,true));
        }

        ArrayList<CinemaMovie> cinemaMovies = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CinemaMovie cMovie = new CinemaMovie(i,"Movie "+ String.valueOf(i),"1"+String.valueOf(i+1)+":00",seats);
            cinemaMovies.add(cMovie);
        }

        ArrayList<PlayDay> days = new ArrayList<>();
        days.add(new PlayDay("Monday",cinemaMovies));
        days.add(new PlayDay("Tuesday",cinemaMovies));
        days.add(new PlayDay("Wednesday",cinemaMovies));
        days.add(new PlayDay("Thursday",cinemaMovies));
        days.add(new PlayDay("Friday",cinemaMovies));
        days.add(new PlayDay("Saturday",cinemaMovies));
        days.add(new PlayDay("Sunday",cinemaMovies));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        for (int i=0;i<days.size();i++) {
            for (int j = 0; j < cinemaMovies.size(); j++) {
                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getName())).child("time").setValue(days.get(i).getMovies().get(j).getPlayTime());
                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getName())).child("id").setValue(days.get(i).getMovies().get(j).getId());

                for (int k = 0; k < seats.size(); k++) {
                    databaseReference.child("Days").child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                            .child(String.valueOf(seats.get(k).getId())).setValue(seats.get(k).isFree());
                }
            }
        }
    }

}
