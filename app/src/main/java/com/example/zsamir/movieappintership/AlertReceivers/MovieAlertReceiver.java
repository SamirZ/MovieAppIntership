package com.example.zsamir.movieappintership.AlertReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Common.SplashActivity;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

public class MovieAlertReceiver extends BroadcastReceiver{

    private Context c;
    private boolean movieNotif = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        //EDIT NOTIFICATION
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            final Gson gson = new Gson();
            Account user = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
            String pass = sharedPreferences.getString("PASSWORD","");
            c = context;

            if(sharedPreferences.contains("movieNotif"))
                movieNotif = sharedPreferences.getBoolean("movieNotif",false);

            requestToken(user.getUsername(),pass);
        }

    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert, Integer notificationId){

        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        PendingIntent notificIntent = PendingIntent.getActivity(context, notificationId,
                new Intent(context, SplashActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);

        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notificationId, mBuilder.build());

    }

    private void requestToken(final String username, final String password){

        ApiHandler.getInstance().requestToken(new ApiHandler.TokenListener() {
            @Override
            public void success(Token response) {
                if(response!=null){
                    ApiHandler.getInstance().validateToken(username, password, response.getRequestToken(), new ApiHandler.TokenListener() {
                        @Override
                        public void success(Token response) {
                            if(response!=null){
                                ApiHandler.getInstance().requestSession(response.getRequestToken(), new ApiHandler.SessionListener() {
                                    @Override
                                    public void success(Session response) {
                                        if(response!=null){
                                            if(movieNotif){
                                                ApiHandler.getInstance().requestUpcomingMovies(1, new ApiHandler.MovieListListener() {
                                                    @Override
                                                    public void success(MovieList response) {
                                                        for (int i = 0; i < response.getMovies().size(); i++) {
                                                            createNotification(c, response.getMovies().get(i).getTitle(), c.getString(R.string.movie_notification_text), c.getString(R.string.app_name),i);
                                                        }
                                                    }
                                                });
                                            }
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
