package com.example.zsamir.movieappintership.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TVSeries;
import com.example.zsamir.movieappintership.Modules.TVSeriesList;
import com.example.zsamir.movieappintership.R;

public class LoginActivity extends BaseActivity {

    private String username;
    private String password;
    private Account account;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mUsername = (EditText)findViewById(R.id.username_login);
        final EditText mPassword = (EditText)findViewById(R.id.password_login);
        TextView forgotDetails = (TextView)findViewById(R.id.forgot_details);
        TextView createAccount = (TextView)findViewById(R.id.create_new_account);

        Button login = (Button)findViewById(R.id.login_button);

        forgotDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.themoviedb.org/account/reset-password";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.themoviedb.org/account/signup";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                if(username.length()>0){
                    if(password.length()>0){
                        sendRequest();
                    }else {
                        Toast.makeText(LoginActivity.this, "Enter your password!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Enter your username!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void sendRequest() {

        progress = new ProgressDialog(this,ProgressDialog.THEME_HOLO_DARK);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){

                    ApiHandler.getInstance().validateToken(username, password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {

                            if(response==null){
                                Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }

                            if(response!=null){

                                ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                    @Override
                                    public void success(final Session response) {
                                        if(response!=null){

                                            ApiHandler.getInstance().requestAccount(response.getSessionId(), new ApiHandler.AccountListener() {
                                                final String SessionId = response.getSessionId();
                                                @Override
                                                public void success(Account response) {
                                                    if(response!=null) {
                                                        account = response;
                                                        account.setSessionId(SessionId);
                                                        // HAVE USER NOW STORE HIM/HER
                                                        requestFavoriteMovies();
                                                        requestFavoriteTVSeries();
                                                        requestWatchlistMovies();
                                                        requestWatchlistTVSeries();

                                                        MovieAppApplication.setUser(account);
                                                        Intent returnIntent = new Intent();
                                                        returnIntent.putExtra("ACCOUNT",account);
                                                        setResult(RESULT_OK,returnIntent);
                                                        progress.dismiss();
                                                        finish();

                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }



    private void requestFavoriteMovies(){
        ApiHandler.getInstance().requestAccountFavoriteMovies(account.getId(), account.getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteMovies(account.getId(), account.getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToFavoriteMoviesList(t.getId());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void requestFavoriteTVSeries(){
        ApiHandler.getInstance().requestAccountFavoriteTVSeries(account.getId(), account.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVSeriesList response) {
                for (TVSeries t: response.getTVSeries()) {
                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountFavoriteTVSeries(account.getId(), account.getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVSeriesList response) {
                                for (TVSeries t: response.getTVSeries()) {
                                    MovieAppApplication.getUser().addToFavoriteTVSeriesList(t.getId());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void requestWatchlistMovies(){
        ApiHandler.getInstance().requestAccountWatchlistMovies(account.getId(), account.getSessionId(), 1, new ApiHandler.MovieListListener() {
            @Override
            public void success(MovieList response) {
                for (Movie t: response.getMovies()) {
                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistMovies(account.getId(), account.getSessionId(), i, new ApiHandler.MovieListListener() {
                            @Override
                            public void success(MovieList response) {
                                for (Movie t: response.getMovies()) {
                                    MovieAppApplication.getUser().addToWatchlistMoviesList(t.getId());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void requestWatchlistTVSeries(){
        ApiHandler.getInstance().requestAccountWatchlistTVSeries(account.getId(), account.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
            @Override
            public void success(TVSeriesList response) {
                for (TVSeries t: response.getTVSeries()) {
                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                }
                if(response.getTotalPages()>1){
                    for(int i = response.getTotalPages(); i>= 2; i--){
                        ApiHandler.getInstance().requestAccountWatchlistTVSeries(account.getId(), account.getSessionId(), i, new ApiHandler.TvSeriesListListener() {
                            @Override
                            public void success(TVSeriesList response) {
                                for (TVSeries t: response.getTVSeries()) {
                                    MovieAppApplication.getUser().addToWatchlistTVSeriesList(t.getId());
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
