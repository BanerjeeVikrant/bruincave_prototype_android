package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class profile_layout extends AppCompatActivity implements  AbsListView.OnScrollListener{

    private int offset = 0;
    private String username;
    private String profileuser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String MY_PREFS_NAME = "userinfo";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String username = prefs.getString("username", null);
        if (username == null) {
            Intent loginIntent = new Intent(profile_layout.this, login_layout.class);
            profile_layout.this.startActivity(loginIntent);
        } else {

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            Log.d("RSP:", "Hope this works");
            Response.Listener<String> usersdataListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String data) {
                    try {
                        Log.d("RSP:", data);
                        JSONObject usersResponse = new JSONObject(data);
                        if (usersResponse != null) {
                            JSONArray dataArray = usersResponse.getJSONArray("userdata");
                            RelativeLayout dataLayout = (RelativeLayout) findViewById(R.id.page);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);

                                String bannerpicLink = dataObject.getString("bannerpic");
                                String profilepicLink = dataObject.getString("userpic");
                                String userHeadingName = dataObject.getString("name");

                                ImageView bannerpic = (ImageView) findViewById(R.id.bannerimg);
                                ImageView profilepic = (ImageView) findViewById(R.id.profilepic);
                                TextView userHeading = (TextView) findViewById(R.id.userHeading);
                                Button controlBtn = (Button) findViewById(R.id.controlBtn);


                                new ImageLinkLoad(bannerpicLink, bannerpic).execute();
                                new ImageLinkLoad(profilepicLink, profilepic).execute();

                                userHeading.setText(userHeadingName);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            BringUserData bringUserData = new BringUserData(username, username, usersdataListener);
            RequestQueue queue3 = Volley.newRequestQueue(profile_layout.this);
            queue3.add(bringUserData);

            if(profileuser == null){
                profileuser = username;
            }

            Log.d("offset Last", offset + "");
            Log.d("username Last", username);
            Log.d("username Last", profileuser);

            bringPosts(username, profileuser);

            ListView yourListView = (ListView) findViewById(R.id.profilePostListView);
            yourListView.setOnScrollListener(this);

            Button followersButton = (Button) findViewById(R.id.followers);
            Button followingButton = (Button) findViewById(R.id.following);

            followersButton.setOnClickListener(followersButtonOnClick);
            followingButton.setOnClickListener(followingButtonOnClick);

        }
    }
    View.OnClickListener followersButtonOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent followersIntent = new Intent(profile_layout.this, followers_intent.class);
            profile_layout.this.startActivity(followersIntent);
        }
    };
    View.OnClickListener followingButtonOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent followingIntent = new Intent(profile_layout.this, following_intent.class);
            profile_layout.this.startActivity(followingIntent);
        }
    };
    private int preLast = 0;
    @Override
    public void onScrollStateChanged(AbsListView view, int state) {

    }

    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if(lastItem == totalItemCount)
        {
            if(preLast!=lastItem)
            {
                /*
                //to avoid multiple calls for last item
                Log.d("Reached Last", "Last");
                Log.d("offset Last", offset + "");
                Log.d("username Last", username);
                Log.d("username Last", profileuser);

                bringPosts(username, profileuser);
                preLast = lastItem;
                */
            }
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        DrawerLayout.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void bringPosts(String username, String profileUser) {
        final profile_layout parent_this = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        Log.d("hi", "");
                        ListView postListView = (ListView) findViewById(R.id.profilePostListView);
                        ArrayList<Post> info = new ArrayList<Post>();

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Post newPost =  new Post();
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.picture_added = post.getString("picture_added");

                            info.add(newPost);
                        }
                        if (offset == 0) {
                            PostAdapter postAdapter = new PostAdapter(parent_this, info);
                            postListView.setAdapter(postAdapter);
                            setListViewHeightBasedOnChildren(postListView);
                        } else {
                            PostAdapter postAdapter  =  (PostAdapter)postListView.getAdapter();
                            postAdapter.addAll(info);
                            setListViewHeightBasedOnChildren(postListView);
                        }
                        offset = offset + 5;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(profile_layout.this);
                        builder.setMessage("Loading Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offset, username, profileUser, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(profile_layout.this);
        queue.add(getPost);

    }

}
