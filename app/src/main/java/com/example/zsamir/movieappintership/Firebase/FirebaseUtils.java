package com.example.zsamir.movieappintership.Firebase;

import android.util.Log;

import com.example.zsamir.movieappintership.Adapters.CinemaMovieAdapter;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class FirebaseUtils {

    public ArrayList<CinemaMovie> retrieveMoviesFromFirebase(String title, final CinemaMovieAdapter cinemaMovieAdapter, final ArrayList<CinemaMovie> movies){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        if(databaseReference.child("Days").child(title)!=null)
            databaseReference.child("Days").child(title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    movies.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        CinemaMovie r = child.getValue(CinemaMovie.class);
                        Log.d("MOVIE",r.toString());
                        if(!movies.contains(r))
                            movies.add(r);
                    }
                    cinemaMovieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        return movies;
    }

    public void addDummyMoviesToFirebase(){

        ArrayList<CinemaSeat> seats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            seats.add(new CinemaSeat(i,true));
        }

        RealmList<Movie> movies = RealmUtils.getInstance().readRealmAccount().getFavMovies();

        ArrayList<CinemaMovie> cinemaMovies = new ArrayList<>();
        for (int i = 0,j = 12; i < movies.size(); i++,j+=2) {

            List<String> movieGenres = movies.get(i).getMovieGenres();
            String allGenres = "";
            for (String s:movieGenres) {
                allGenres += s+"/";
            }
            if(allGenres.length()>1) {
                allGenres = allGenres.substring(0, allGenres.length() - 1);
            }
            cinemaMovies.add(
                    new CinemaMovie(
                        movies.get(i).getId(),
                        movies.get(i).getTitle(),
                        String.valueOf(j)+":00",
                        allGenres,
                        movies.get(i).getVoteAverage(),
                        movies.get(i).getOverview(),
                        movies.get(i).getBackdropPath(),
                        movies.get(i).getOrgReleaseDate(),
                        seats));
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
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("time")
                        .setValue(days.get(i).getMovies().get(j).getTime());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("name")
                        .setValue(days.get(i).getMovies().get(j).getName());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("rating")
                        .setValue(days.get(i).getMovies().get(j).getRating());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("overview")
                        .setValue(days.get(i).getMovies().get(j).getOverview());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("id")
                        .setValue(days.get(i).getMovies().get(j).getId());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getName()))
                        .child("genres")
                        .setValue(days.get(i).getMovies().get(j).getGenres());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("backdropPath")
                        .setValue(days.get(i).getMovies().get(j).getBackdropPath());

                databaseReference.child("Days")
                        .child(days.get(i).getName())
                        .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                        .child("releaseDate")
                        .setValue(days.get(i).getMovies().get(j).getReleaseDate());


                for (int k = 0; k < seats.size(); k++) {

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                            .child("seats")
                            .child(String.valueOf(seats.get(k).getId()))
                            .child("id")
                            .setValue(seats.get(k).getId());

                    databaseReference.child("Days")
                            .child(days.get(i).getName())
                            .child(String.valueOf(days.get(i).getMovies().get(j).getTime()))
                            .child("seats")
                            .child(String.valueOf(seats.get(k).getId()))
                            .child("free")
                            .setValue(seats.get(k).isFree());
                }
            }
        }
    }

}
