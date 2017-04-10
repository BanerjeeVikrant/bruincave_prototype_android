package com.bruincave.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class following_intent extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_intent);

        final String MY_PREFS_NAME = "userinfo";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        username = prefs.getString("username", null);
        if (username == null) {
            Intent loginIntent = new Intent(following_intent.this, login_layout.class);
            following_intent.this.startActivity(loginIntent);
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            ImageButton back_following = (ImageButton) findViewById(R.id.backProfileFollowing);
            back_following.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(following_intent.this, profile_layout.class);
                    following_intent.this.startActivity(profileIntent);
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            });
        }
        bringFollowing(username, "");

        final EditText searchFollowingEdit = (EditText) findViewById(R.id.searchFollowing);

        searchFollowingEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String str = searchFollowingEdit.getText().toString();

                bringFollowing(username, str);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void bringFollowing(String username, String str) {
        final following_intent parent_this = this;
        Response.Listener<String> usersListener = new Response.Listener<String>() {
            private String[] info;

            @Override
            public void onResponse(String users) {
                try {
                    JSONObject usersResponse = new JSONObject(users);
                    Log.d("users:", users);
                    if (usersResponse != null) {
                        JSONArray usersArray = usersResponse.getJSONArray("following");
                        //LinearLayout usersLayout = (LinearLayout) findViewById(R.id.userswrapper);
                        ListView usersMsgListView = (ListView) findViewById(R.id.followingIntent);
                        ArrayList<BringFollowing> info = new ArrayList<BringFollowing>();

                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject usersObject = usersArray.getJSONObject(i);
                            Log.d("users-id:", "" + usersObject.getInt("id"));

                            BringFollowing bringFollowing = new BringFollowing();
                            bringFollowing.followingPic = usersObject.getString("profilepic");
                            bringFollowing.followingName = usersObject.getString("name");
                            bringFollowing.followingLastOnline = usersObject.getString("time");

                            info.add(bringFollowing);


                        }
                        BringFollowingAdapter bringFollowingAdapter = new BringFollowingAdapter(parent_this, info);
                        usersMsgListView.setAdapter(bringFollowingAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetFollowing getFollowing = new GetFollowing(str, username, usersListener);
        RequestQueue queue3 = Volley.newRequestQueue(following_intent.this);
        queue3.add(getFollowing);

    }
}
