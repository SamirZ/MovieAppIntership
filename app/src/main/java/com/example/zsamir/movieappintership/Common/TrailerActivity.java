package com.example.zsamir.movieappintership.Common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Modules.Videos;
import com.example.zsamir.movieappintership.R;

public class TrailerActivity extends AppCompatActivity {

    String url = "https://www.youtube.com/watch?v=";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        webView = (WebView) findViewById(R.id.trailer_video);

        if(getIntent().hasExtra("MovieID")){
            String id = getIntent().getStringExtra("MovieID");
            ApiHandler.getInstance().requestMovieVideos(Integer.valueOf(id), new ApiHandler.VideosListener() {
                @Override
                public void success(Videos response) {
                    if(response!=null){
                        url = url + response.videos.get(0).key;
                        Log.d("URL",url);
                        webView.loadUrl(url);
                    }
                }
            });
        }

        if(getIntent().hasExtra("TVID")){
            String id = getIntent().getStringExtra("TVID");
            ApiHandler.getInstance().requestTVSeriesVideos(Integer.valueOf(id), new ApiHandler.VideosListener() {
                @Override
                public void success(Videos response) {
                    if(response!=null){
                        url = url + response.videos.get(0).key;
                        Log.d("URL",url);
                        webView.loadUrl(url);
                    }
                }
            });
        }



        //https://www.youtube.com/watch?v=CCZPUeY94MU
        //webView.loadUrl();

    }


}
