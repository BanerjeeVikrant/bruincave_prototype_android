package com.example.banerjee.bruincave_new;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant Banerjee on 2/8/2017.
 */
public class NotificationsTab extends Fragment{
    private int offset_notifications = 0;
    public NotificationsTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        offset_notifications = 0;
        return inflater.inflate(R.layout.notifications_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        bringNotifications(getArguments().getString("username"));
    }
    public void bringNotifications(String username){
        //final home_layout parent_this = this;
        Response.Listener<String> notificationsListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String notifications) {
                try {
                    JSONObject notificationsResponse = new JSONObject(notifications);
                    Log.d("notifications:", notifications);
                    if (notificationsResponse != null) {
                        JSONArray notificationsArray = notificationsResponse.getJSONArray("notifications");

                        ArrayList<Notifications> info = new ArrayList<Notifications>();

                        for (int i = 0; i < notificationsArray.length(); i++) {
                            JSONObject notificationsObject = notificationsArray.getJSONObject(i);
                            Log.d("notifications-id:", "" + notificationsObject.getInt("id"));

                            Notifications newNotifications =  new Notifications();
                            newNotifications.userpic = notificationsObject.getString("fromPic");
                            newNotifications.userFirst = notificationsObject.getString("fromFirst");
                            newNotifications.body = notificationsObject.getString("body");
                            newNotifications.time_added = notificationsObject.getString("time_added");

                            info.add(newNotifications);


                        }
                        ListView notificationsListView = (ListView) getView().findViewById(R.id.notiListView);
                        if (offset_notifications == 0) {
                            NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getContext(),info);
                            notificationsListView.setAdapter(notificationsAdapter);
                        } else {
                            NotificationsAdapter notificationsAdapter  =  (NotificationsAdapter)notificationsListView.getAdapter();
                            notificationsAdapter.addAll(info);
                        }
                        offset_notifications = offset_notifications + 5;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        GetNotifications getNotifications = new GetNotifications(offset_notifications, username, notificationsListener);
        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        queue2.add(getNotifications);
    }
}
