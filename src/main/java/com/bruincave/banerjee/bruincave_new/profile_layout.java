package com.bruincave.banerjee.bruincave_new;

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
import android.widget.ImageButton;
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
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class profile_layout extends AppCompatActivity {

    private int offset = 0;
    private String profileuser = "ssdf";
    private int preLast = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_layout);

        final String MY_PREFS_NAME = "userinfo";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        final String username = prefs.getString("username", null);
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

            ImageButton backButton = (ImageButton) findViewById(R.id.backProfile);


            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent homeIntent = new Intent(profile_layout.this, home_layout.class);
                    profile_layout.this.startActivity(homeIntent);
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            });
            /* ERROR*********************************************************

            Button followersBtn = (Button) findViewById(R.id.followers);
            Button followingBtn = (Button) findViewById(R.id.following);

            followersBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent followerIntent = new Intent(profile_layout.this, followers_intent.class);
                    profile_layout.this.startActivity(followerIntent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }
            });

            followingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent followingIntent = new Intent(profile_layout.this, following_intent.class);
                    profile_layout.this.startActivity(followingIntent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }
            });

            ****************************************************************/


            bringPosts(username, profileuser);
            ListView postListView = (ListView) findViewById(R.id.profileListView);
            postListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if(lastItem == totalItemCount) {
                        if(preLast!=lastItem) {

                            bringPosts(username, profileuser);

                            Log.d("info", offset + "");

                            preLast = lastItem;
                        }
                    }
                }
            });


        }
    }

    PostAdapter postAdapter = null;
    public void bringPosts(final String username, String profileUser) {
        Log.d("username", username);
        Log.d("profileusername", profileUser);
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Post newPost =  new Post();
                            newPost.id = post.getInt("id");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = username;
                            newPost.moreThanThreeLiker = post.getInt("moreThanThreeLiker");
                            newPost.moreThanThreeComments = post.getInt("moreThanThreeComments");
                            newPost.likesCount = post.getInt("likesCount");
                            newPost.likedby = post.getString("likedby");


                            JSONArray comments = post.getJSONArray("comments");
                            newPost.comments = new Comment[comments.length()];
                            for (int cn = 0; cn < comments.length(); cn++) {
                                JSONObject comment = comments.getJSONObject(cn);
                                newPost.comments[cn] = new Comment();
                                newPost.comments[cn].body = comment.getString("body");
                                newPost.comments[cn].from = comment.getString("from");
                            }
                            info.add(newPost);
                            if(i == home.length() - 1){
                                info.add(newPost);
                            }
                        }
                        /*
                        ListView postListView = (ListView) findViewById(R.id.profileListView);

                        if (offset == 0) {
                            postAdapter = new PostAdapter(getApplicationContext(), info);
                            //postAdapter.setForProfile();
                            postListView.setAdapter(postAdapter);
                        }else {
                            postAdapter.addAll(info);
                        }

                        offset = offset + 5;
                        */
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offset, username, profileUser, 0, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getPost);

    }
}
