package com.example.zsamir.movieappintership.Common;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.BuildConfig;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.Video;
import com.example.zsamir.movieappintership.Modules.Videos;
import com.example.zsamir.movieappintership.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class TrailerActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {

    private String API_KEY = BuildConfig.YOUTUBE_KEY;
    private String VIDEO_ID = "";

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trailer);

        setUpVideo();
    }

    private void setUpVideo() {

        TextView name = (TextView) findViewById(R.id.video_name);
        TextView desc = (TextView) findViewById(R.id.video_description);


        if(getIntent().hasExtra("MovieID")){

            setTitle(getString(R.string.movies_label));
            Movie movie = getIntent().getParcelableExtra("MovieID");
            name.setText(getString(R.string.trailer));
            name.append(" "+movie.getTitle());
            desc.setText(movie.getOverview());

            ApiHandler.getInstance().requestMovieVideos(movie.getId(), new ApiHandler.VideosListener() {
                @Override
                public void success(Videos response) {
                    if(response!=null){
                        for (Video v:response.getVideos()) {
                            if(v.getSite().equalsIgnoreCase("YouTube") && v.getType().equalsIgnoreCase("Trailer")){

                                FragmentManager fragmentManager = getFragmentManager();
                                YouTubePlayerFragment mYoutubePlayerFragment = (YouTubePlayerFragment) fragmentManager.findFragmentById(R.id.video_container);
                                if(mYoutubePlayerFragment!=null) {
                                    mYoutubePlayerFragment.initialize(API_KEY, TrailerActivity.this);
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.video_container, mYoutubePlayerFragment);
                                    fragmentTransaction.commit();
                                }
                                VIDEO_ID = v.getKey();
                                break;
                            }
                        }
                    }
                }
            });
        }
        else if(getIntent().hasExtra("TVID")){

            setTitle(getString(R.string.tv_series_name));
            TVShow tv = getIntent().getParcelableExtra("TVID");
            name.setText(getString(R.string.trailer));
            name.append(" "+tv.getName());
            desc.setText(tv.getOverview());

            ApiHandler.getInstance().requestTVSeriesVideos(tv.getId(), new ApiHandler.VideosListener() {
                @Override
                public void success(Videos response) {
                    if(response!=null){
                        for (Video v:response.getVideos()) {
                            if(v.getSite().equalsIgnoreCase("YouTube") && v.getType().equalsIgnoreCase("Trailer")){

                                FragmentManager fragmentManager = getFragmentManager();
                                YouTubePlayerFragment mYoutubePlayerFragment = (YouTubePlayerFragment) fragmentManager.findFragmentById(R.id.video_container);
                                if(mYoutubePlayerFragment!=null) {
                                    mYoutubePlayerFragment.initialize(API_KEY, TrailerActivity.this);
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.video_container, mYoutubePlayerFragment);
                                    fragmentTransaction.commit();
                                }

                                VIDEO_ID = v.getKey();
                                break;
                            }
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if(!b){
            youTubePlayer.cueVideo(VIDEO_ID);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_season_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onNetworkUnavailable() {

    }
}
