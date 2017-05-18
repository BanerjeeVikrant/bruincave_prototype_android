package com.bruincave.banerjee.bruincave_new;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Vikrant Banerjee on 3/5/2017.
 */
public class NotificationService extends Service {

    private String username;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "Started");
        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);

        username = prefs.getString("username", null);

        mTimer = new Timer();
        mTimer.schedule(timerTask, 5000, 5 * 1000);
    }

    private Timer mTimer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            //Log.d("Service", "Running");
            doNotifications();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

        }catch (Exception e){
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void doNotifications(){
        Response.Listener<String> postidListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   // Log.d("Service:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray postArray = jsonResponse.getJSONArray("notifyInfo");
                        for (int i = 0; i < postArray.length(); i++) {
                            JSONObject usersObject = postArray.getJSONObject(i);

                            int type = usersObject.getInt("type");
                            String fromUserInfo = usersObject.getString("fullfromuser");
                            String profilepic = usersObject.getString("frompic");

                            if(type == 0) {
                                String bodyInfo = usersObject.getString("message");
                                int fromUserId = usersObject.getInt("fromid");
                                new generatePictureStyleNotification(getApplicationContext(), fromUserInfo, fromUserId, bodyInfo, profilepic).execute();
                            }
                            else if(type == 1){
                                String bodyInfo = fromUserInfo + " " + usersObject.getString("message");
                                int postinsertedId = usersObject.getInt("postinsertedid");
                                int fromUserId = usersObject.getInt("commentpostid");
                                new NotifyOnComment(getApplicationContext(), "bruincave", fromUserId, postinsertedId, bodyInfo, profilepic).execute();
                            }

                            Log.d("Service", "InsideResponse");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetNotify getNotify = new GetNotify(username, postidListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getNotify);

    }


}
