package com.example.zsamir.movieappintership;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.zsamir.movieappintership.Login.LoginActivity;
import com.example.zsamir.movieappintership.NewsFeed.NewsFeedActivity;

public class BaseActivity extends AppCompatActivity{

    public void showLoginDialog(){
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this, R.style.MyDialogTheme)
                .setTitle(getString(R.string.login_to))
                .setMessage(getString(R.string.login_question) + "\n" + "\n" + "\n" + "\n" + "\n")
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with login
                        Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                        startActivityForResult(i, 1);

                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // set pref to no
                    }
                })
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.colorAccent));

    }

}
