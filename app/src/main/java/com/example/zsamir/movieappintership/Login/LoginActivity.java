package com.example.zsamir.movieappintership.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Common.AccountListsRequestHandler;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmInteger;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    private String username;
    private String password;
    private ProgressDialog progress;
    private EditText mUsername;
    private EditText mPassword;
    private TextView forgotDetails;
    private TextView createAccount;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();

        setForgotDetails();

        setCreateAccount();

        setOnLogin();

    }

    private void setOnLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                if(isNetworkAvailable()){
                    if(username.length()>0){
                        if(password.length()>0){
                            progress = new ProgressDialog(LoginActivity.this,ProgressDialog.THEME_HOLO_DARK);
                            progress.setMessage("Loading...");
                            progress.setCancelable(false);
                            progress.show();
                            sendRequest(progress,username,password);
                        }else {
                            Toast.makeText(LoginActivity.this, getString(R.string.password_warrning), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, getString(R.string.username_warrning), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, getString(R.string.connection_warrning), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCreateAccount() {
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.themoviedb.org/account/signup";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void setForgotDetails() {
        forgotDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.themoviedb.org/account/reset-password";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void setUpViews() {
        mUsername = (EditText)findViewById(R.id.username_login);
        mPassword = (EditText)findViewById(R.id.password_login);
        forgotDetails = (TextView)findViewById(R.id.forgot_details);
        createAccount = (TextView)findViewById(R.id.create_new_account);
        login = (Button)findViewById(R.id.login_button);
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


    private void sendRequest(final ProgressDialog progress,final String username,final String password) {

        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){

                    ApiHandler.getInstance().validateToken(username, password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {

                            if(response==null){
                                Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                                if(progress!=null)
                                    progress.dismiss();
                            }

                            if(response!=null){

                                ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                    @Override
                                    public void success(final Session response) {
                                        if(response!=null){

                                            ApiHandler.getInstance().requestAccount(response.getSessionId(), new ApiHandler.AccountListener() {
                                                String SessionId = response.getSessionId();

                                                @Override
                                                public void success(Account response) {
                                                    if(response!=null) {
                                                        MovieAppApplication.setUser(response);
                                                        MovieAppApplication.getUser().setSessionId(SessionId);
                                                        // HAVE USER NOW STORE HIM/HER


                                                        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", 0);
                                                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                                                        final Gson gson = new Gson();
                                                        String serializedObject = gson.toJson(MovieAppApplication.getUser());
                                                        sharedPreferencesEditor.putString("USER", serializedObject);
                                                        sharedPreferencesEditor.putString("PASSWORD", password);
                                                        sharedPreferencesEditor.apply();

                                                        RealmUtils.getInstance().createRealmAccount();

                                                        RealmUtils.getInstance().deleteAllMovies();
                                                        RealmUtils.getInstance().deleteAllTVShows();

                                                        AccountListsRequestHandler.getInstance().requestFavoriteMovies();
                                                        AccountListsRequestHandler.getInstance().requestFavoriteTVSeries();
                                                        AccountListsRequestHandler.getInstance().requestWatchlistMovies();
                                                        AccountListsRequestHandler.getInstance().requestWatchlistTVSeries();
                                                        AccountListsRequestHandler.getInstance().requestRatedMovies();
                                                        AccountListsRequestHandler.getInstance().requestRatedTVSeries();

                                                        Intent returnIntent = new Intent();
                                                        setResult(RESULT_OK,returnIntent);

                                                        //Wait for requests to finish
                                                        Thread splashThread = new Thread(){

                                                            @Override
                                                            public void run(){
                                                                try {
                                                                    sleep(2000);
                                                                    if(progress!=null)
                                                                        progress.dismiss();
                                                                    finish();
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                        };
                                                        splashThread.start();

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

}
