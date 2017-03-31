package com.example.banerjee.bruincave_new;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
            Log.d("Service", "Running");
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
                    Log.d("Service:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray postArray = jsonResponse.getJSONArray("notifyInfo");
                        for (int i = 0; i < postArray.length(); i++) {
                            JSONObject usersObject = postArray.getJSONObject(i);

                            String fromUserInfo = usersObject.getString("fromuser");
                            String bodyInfo = usersObject.getString("message");
                            String profilepic = usersObject.getString("frompic");
                            createNotification(fromUserInfo, bodyInfo, profilepic);
                            Log.d("Service", "InsideResponse");
                        }

                        Log.d("Service", "InsideIf");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetNotify getNotify = new GetNotify(username, postidListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getNotify);

        Log.d("Service", "InsideFunction");

    }

    public void createNotification(String contentTitle, String contentText, String icon){

        Intent intent = new Intent(this, NotificationService.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(home_layout.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.likedpaw)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();

        Random r = new Random();
        int Low = 0;
        int High = 1000000;
        int Result = r.nextInt(High-Low) + Low;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Result, notification);

    }


}
