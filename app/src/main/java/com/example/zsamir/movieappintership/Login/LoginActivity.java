package com.example.zsamir.movieappintership.Login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.example.zsamir.movieappintership.Common.MovieAppApplication;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.R;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private Account account;

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
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
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

        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){

                    ApiHandler.getInstance().validateToken(username, password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {

                            if(response==null){
                                Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                            }

                            if(response!=null){

                                ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                    @Override
                                    public void success(Session response) {
                                        if(response!=null){

                                            ApiHandler.getInstance().requestAccount(response.getSessionId(), new ApiHandler.AccountListener() {
                                                @Override
                                                public void success(Account response) {
                                                    if(response!=null) {
                                                        account = response;
                                                        // HAVE USER NOW STORE HIM/HER
                                                        MovieAppApplication.getInstance().setUser(response);
                                                        Intent returnIntent = new Intent();
                                                        returnIntent.putExtra("ACCOUNT",account);
                                                        setResult(RESULT_OK,returnIntent);
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
}
