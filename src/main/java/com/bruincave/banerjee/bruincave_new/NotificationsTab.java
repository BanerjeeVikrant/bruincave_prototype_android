package com.bruincave.banerjee.bruincave_new;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant Banerjee on 2/8/2017.
 */
public class NotificationsTab extends Fragment{

    private int offset_notifications = 0;
    private int preLast = 0;
    private boolean createTab = true;
    private View footerView;

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
        createTab = true;
        offset_notifications = 0;
        return inflater.inflate(R.layout.notifications_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getView() != null) {
            bringNotifications(getArguments().getString("username"));
        }

        ListView notificationListView = (ListView) getView().findViewById(R.id.notiListView);

        if(createTab) {
            createTab = false;
            footerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
            notificationListView.addFooterView(footerView);
        }

        notificationListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLast!=lastItem) {
                        if (totalItemCount != 0) {
                            bringNotifications(getArguments().getString("username"));
                        }
                        Log.d("ZZZAll", offset_notifications + "");

                        preLast = lastItem;
                    }
                }
            }
        });
    }
    NotificationsAdapter notificationsAdapter = null;
    public void bringNotifications(String username){
        //final home_layout parent_this = this;
        final View currView = getView();
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
                            newNotifications.postid = notificationsObject.getInt("postid");
                            newNotifications.type = notificationsObject.getInt("type");
                            info.add(newNotifications);


                        }
                        ListView notificationsListView = (ListView) currView.findViewById(R.id.notiListView);
                        if(notificationsListView.getAdapter()==null)
                            if (offset_notifications == 0) {
                                notificationsAdapter = new NotificationsAdapter(getContext(), info);
                                notificationsListView.setAdapter(notificationsAdapter);
                            }else {
                                notificationsAdapter.addAll(info);
                            }
                        else{
                            if (offset_notifications == 0) {
                                notificationsAdapter = new NotificationsAdapter(getContext(), info);
                                notificationsListView.setAdapter(notificationsAdapter);
                            }else {
                                notificationsAdapter.addAll(info);
                            }
                            notificationsAdapter.notifyDataSetChanged();
                        }
                        offset_notifications = offset_notifications + 5;
                        Log.d("notificationoffset", offset_notifications + "");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ListView notificationsListView = (ListView) currView.findViewById(R.id.notiListView);

                    notificationsListView.removeFooterView(footerView);
                }

            }
        };
        GetNotifications getNotifications = new GetNotifications(offset_notifications, username, notificationsListener);
        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        queue2.add(getNotifications);
    }
}
