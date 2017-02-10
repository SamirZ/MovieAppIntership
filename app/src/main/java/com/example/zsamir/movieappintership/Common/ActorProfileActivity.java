package com.example.zsamir.movieappintership.Common;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.ActorMovieAdapter;
import com.example.zsamir.movieappintership.Adapters.MovieAdapter;
import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.EpisodeCast;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.Widgets.ExpandableTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class ActorProfileActivity extends AppCompatActivity {

    Actor mActor;
    Cast cast;
    EpisodeCast episodeCast;
    private boolean clicked = false;
    ArrayList<Movie> mMovies = new ArrayList<>();
    ActorMovieAdapter mMovieAdapter;
    ApiHandler apiHandler = ApiHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_profile);
        setFinishOnTouchOutside(true);

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

        if (getIntent().hasExtra("Actor1")) {
            episodeCast = getIntent().getParcelableExtra("Actor1");
            mMovieAdapter = new ActorMovieAdapter(mMovies,episodeCast.toCast());
            apiHandler.requestActor(episodeCast.id, new ApiHandler.ActorDetailsListener() {
                @Override
                public void success(Actor response) {
                    mActor = response;
                    initializeActor();
                }
            });
        }


    }

    private void initializeActor() {

        if(cast==null)
            cast = episodeCast.toCast();

        ImageView mActorImage = (ImageView) findViewById(R.id.actor_details_image);
        if(cast.getPosterUrl()!=null)
        Glide.with(this).load(cast.getImageUrl()).into(mActorImage);

        TextView mActorName = (TextView) findViewById(R.id.actor_details_name);
        if(mActor.getName()!=null)
            mActorName.setText(mActor.getName());
        else
            mActorName.setVisibility(View.GONE);

        TextView mActorDateOfBirth = (TextView) findViewById(R.id.actor_details_date_of_birth_2);
        LinearLayout mDateOfBirth = (LinearLayout)findViewById(R.id.actor_details_date_of_birth);
        if(mActor.getBirthday()!=null && mActor.getPlaceOfBirth()!=null) {
            if(getDate(mActor.getBirthday()).length()>1)
                mActorDateOfBirth.setText(getDate(mActor.getBirthday())+", ");
            mActorDateOfBirth.append(mActor.getPlaceOfBirth());
        }else {
            mDateOfBirth.setVisibility(View.GONE);
        }

        TextView mActorWebsite = (TextView) findViewById(R.id.actor_details_website_2);
        LinearLayout mWebsite = (LinearLayout)findViewById(R.id.actor_details_website);
        if(mActor.getHomepage()!=null && mActor.getHomepage().length()>3){
            mActorWebsite.setText(mActor.getHomepage());
            mActorWebsite.setTextColor(ContextCompat.getColor(this, R.color.colorActorWebsite));
        }else {
            mWebsite.setVisibility(View.GONE);
        }

        final TextView more = (TextView) findViewById(R.id.see_full_bio);
        TextView mActorBioLabel = (TextView) findViewById(R.id.actor_details_biography_1);
        final ExpandableTextView mActorBio = (ExpandableTextView) findViewById(R.id.actor_details_biography_2);
        mActorBio.setTrimLength(335);
        if(mActor.getBiography()!=null){
            if(mActor.getBiography().length()>0){
                mActorBio.setText(mActor.getBiography());
                if(mActorBio.getOriginalTextSize()>335){
                    more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mActorBio.trim = !mActorBio.trim;
                            mActorBio.setText();
                            //text.requestFocusFromTouch();
                            if(!clicked) {
                                more.setText(R.string.hide_text);
                                clicked = true;
                            }else{
                                more.setText(R.string.see_full_bio);
                                clicked = false;
                            }
                        }
                    });
                }else{
                    more.setEnabled(false);
                    more.setTextColor(ContextCompat.getColor(this, R.color.colorAccentPressed));
                }
            }else{
                mActorBioLabel.setVisibility(View.GONE);
                mActorBio.setVisibility(View.GONE);
                more.setVisibility(View.GONE);
            }
        }else{
            mActorBioLabel.setVisibility(View.GONE);
            mActorBio.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
        }
        apiHandler.requestMovieWithActor(mActor.getId(), new ApiHandler.MovieListListener() {
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
        if(!date.equals("")) {
            String[] s = date.split("-");
            if(s.length>2) {
                if(s[2].startsWith("0")){
                    String s1 = s[2].substring(1);
                    return s1 + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0];
                }
                return s[2] + " " + getMonth(Integer.parseInt(s[1])) + " " + s[0]; /// TO DO
            }
        }
        return "";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actor_details, menu);
        return true;
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
}
