package com.example.zsamir.movieappintership.AlertReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TVAlertReceiver extends BroadcastReceiver{

    private List<TVShow> watchlist = new ArrayList<>();
    private List<TVShow> common = new ArrayList<>();
    private Account user;
    private Context c;
    private boolean tvNotif = false;
    private Intent tvIntent;
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        //EDIT NOTIFICATION
        sharedPreferences = context.getSharedPreferences("PREFERENCE", 0);
        if (sharedPreferences.contains("USER")) {
            final Gson gson = new Gson();
            tvIntent = new Intent(context, AiringEpisodesActivity.class);
            user = gson.fromJson(sharedPreferences.getString("USER", ""), Account.class);
            String pass = sharedPreferences.getString("PASSWORD","");
            c = context;

            if(sharedPreferences.contains("tvNotif"))
                tvNotif = sharedPreferences.getBoolean("tvNotif",false);

            requestToken(user.getUsername(),pass);
        }

    }

    public void createNotification(Context context, String msg, String msgText, String msgAlert, Integer notificationId){

        PendingIntent notificIntent = PendingIntent.getActivity(context, notificationId, tvIntent, 0);

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
                                            if(tvNotif){
                                                ApiHandler.getInstance().requestAiringTodayTvSeries(1, new ApiHandler.TvSeriesListListener() {
                                                    @Override
                                                    public void success(TVShowList response) {
                                                        Log.d("SUCCESS","ENTERED");
                                                        for (TVShow t: response.getTVShow()) {
                                                            watchlist.add(t);
                                                        }
                                                        if(response.getTotalPages()>1){
                                                            for(int i = response.getTotalPages(); i>= 2; i--){
                                                                ApiHandler.getInstance().requestAiringTodayTvSeries(i, new ApiHandler.TvSeriesListListener() {
                                                                    @Override
                                                                    public void success(TVShowList response) {
                                                                        for (TVShow t: response.getTVShow()) {
                                                                            watchlist.add(t);
                                                                        }
                                                                    }
                                                                });
                                                            }

                                                            ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
                                                                @Override
                                                                public void success(TVShowList response) {
                                                                    if(response!=null){

                                                                        for (TVShow t:response.getTVShow()) {
                                                                            for (TVShow tv:watchlist) {
                                                                                if(t.getId()==tv.getId()) {
                                                                                    if(!common.contains(t))
                                                                                        common.add(t);
                                                                                }
                                                                            }
                                                                        }

                                                                        //common.retainAll(watchlist);
                                                                        Gson gson = new Gson();
                                                                        TVShowList tvShowList = new TVShowList();
                                                                        tvShowList.setTVShow(common);
                                                                        String serializedObject = gson.toJson(tvShowList);
                                                                        sharedPreferences.edit().putString("AIRING", serializedObject).apply();
                                                                        createNotification(c, c.getString(R.string.app_name) , String.valueOf(common.size())+" "+c.getString(R.string.tv_notification_text), c.getString(R.string.app_name),10);
                                                                    }
                                                                }
                                                            });
                                                        }else{
                                                            ApiHandler.getInstance().requestAccountWatchlistTVSeries(user.getId(), user.getSessionId(), 1, new ApiHandler.TvSeriesListListener() {
                                                                @Override
                                                                public void success(TVShowList response) {
                                                                    if(response!=null){

                                                                        for (TVShow t:response.getTVShow()) {
                                                                            for (TVShow tv:watchlist) {
                                                                                if(t.getId()==tv.getId()) {
                                                                                    if(!common.contains(t))
                                                                                        common.add(t);
                                                                                }
                                                                            }
                                                                        }

                                                                        //common.retainAll(watchlist);
                                                                        Gson gson = new Gson();
                                                                        TVShowList tvShowList = new TVShowList();
                                                                        tvShowList.setTVShow(common);
                                                                        String serializedObject = gson.toJson(tvShowList);
                                                                        sharedPreferences.edit().putString("AIRING", serializedObject).apply();
                                                                        createNotification(c, c.getString(R.string.app_name) , String.valueOf(common.size())+" "+c.getString(R.string.tv_notification_text), c.getString(R.string.app_name),10);
                                                                    }
                                                                }
                                                            });
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
