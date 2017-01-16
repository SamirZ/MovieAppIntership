package com.example.zsamir.movieappintership;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.ActorMovieAdapter;
import com.example.zsamir.movieappintership.Adapters.MovieAdapter;
import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;

import java.util.ArrayList;

public class ActorProfileActivity extends AppCompatActivity {

    Actor mActor;
    Cast cast;

    ArrayList<Movie> mMovies = new ArrayList<>();
    ActorMovieAdapter mMovieAdapter;
    ApiHandler apiHandler = ApiHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_profile);

        // Get actor id trough cast details
        if (getIntent().hasExtra("Actor")) {
            cast = getIntent().getParcelableExtra("Actor");
            mMovieAdapter = new ActorMovieAdapter(mMovies,cast);
            apiHandler.requestActor(cast.id, new ApiHandler.ActorDetailsListener() {
                @Override
                public void success(Actor response) {
                    mActor = response;
                    initializeActor();
                }
            });
        }


    }

    private void initializeActor() {

        ImageView mActorImage = (ImageView) findViewById(R.id.actor_details_image);
        if(cast.getPosterUrl()!=null)
        Glide.with(this).load(cast.getPosterUrl()).into(mActorImage);

        TextView mActorName = (TextView) findViewById(R.id.actor_details_name);
        if(mActor.name!=null)
            mActorName.setText(mActor.name);

        TextView mActorDateOfBirth = (TextView) findViewById(R.id.actor_details_date_of_birth_2);
        if(mActor.birthday!=null && mActor.placeOfBirth!=null)
        mActorDateOfBirth.setText(getDate(mActor.birthday)+", "+mActor.placeOfBirth);

        TextView mActorWebsite = (TextView) findViewById(R.id.actor_details_website_2);
        if(mActor.homepage!=null)
        mActorWebsite.setText(mActor.homepage);

        TextView mActorBio = (TextView) findViewById(R.id.actor_details_biography_2);
        if(mActor.biography!=null)
        mActorBio.setText(mActor.biography);

        apiHandler.requestMovieWithActor(mActor.id, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                mMovies.addAll(response.getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.actor_movies_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    public String getDate(String date) {
        String[] s = date.split("-");
        return s[2]+" "+getMonth(Integer.parseInt(s[1]))+ " "+ s[0]; /// TO DO
    }

    private String getMonth(int i) {
        switch (i){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "Wrong Month Format";
    }
}
