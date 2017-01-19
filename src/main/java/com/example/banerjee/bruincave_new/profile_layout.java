package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profile_layout extends AppCompatActivity {

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
            Intent loginIntent = new Intent(profile_layout.this, Login_Activity.class);
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

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataObject = dataArray.getJSONObject(i);

                                String bannerpicLink = dataObject.getString("bannerpic");
                                String profilepicLink = dataObject.getString("userpic");
                                String userHeadingName = dataObject.getString("name");

                                ImageView bannerpic = (ImageView) findViewById(R.id.bannerimg);
                                ImageView profilepic = (ImageView) findViewById(R.id.profilepic);
                                TextView userHeading = (TextView) findViewById(R.id.userHeading) ;
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

            bringPosts(0, username, username);

        }
    }
    public void bringPosts(int o, String username, String profileUser) {

        Response.Listener<String> postListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                int last_id = 0;
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            last_id = post.getInt("id");

                            //int dimen = (int) getResources().getDimension(R.dimen.feed_item_padding_top_bottom);
                            int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                            LinearLayout homePost = new LinearLayout(getApplicationContext());
                            homePost.setBackgroundResource(R.color.white);
                            homePost.setOrientation(LinearLayout.VERTICAL);
                            homePost.setPaddingRelative(0, 0, 0, 0);
                            LinearLayout.LayoutParams layout_141 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layout_141.setMargins(0, marginTop, 0, 0);
                            homePost.setLayoutParams(layout_141);
                            homeLayout.addView(homePost);

                            LinearLayout informations = new LinearLayout(getApplicationContext());
                            informations.setOrientation(LinearLayout.HORIZONTAL);
                            informations.setPadding(0, 0, 15, 0);
                            LinearLayout.LayoutParams layout_501 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            informations.setLayoutParams(layout_501);
                            homePost.addView(informations);

                            String userpic = post.getString("userpic");
                            Log.d("link", userpic);
                            int profilePicHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                            int profilePicWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                            ImageView profilePic = new ImageView(getApplicationContext());
                            profilePic.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(profilePicHeight, profilePicWidth);
                            profilePic.setMaxHeight(profilePicHeight);
                            profilePic.setMaxWidth(profilePicWidth);
                            lp.setMargins(30, 30, 10, 0);
                            profilePic.setLayoutParams(lp);
                            new ImageLinkLoad(userpic, profilePic).execute();
                            informations.addView(profilePic);

                            int paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                            LinearLayout name_wrapper = new LinearLayout(getApplicationContext());
                            name_wrapper.setOrientation(LinearLayout.VERTICAL);
                            name_wrapper.setPadding(0, 0, paddingLeft, 0);
                            LinearLayout.LayoutParams layout_5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            name_wrapper.setLayoutParams(layout_5);
                            informations.addView(name_wrapper);

                            int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
                            TextView name = new TextView(getApplicationContext());
                            name.setPadding(20, 60, 0, 0);
                            name.setTextSize(textSize);
                            name.setText(post.getString("name"));
                            name.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            name_wrapper.addView(name);

                            TextView timestamp = new TextView(getApplicationContext());
                            timestamp.setTextColor(getResources().getColor(R.color.timestamp));
                            timestamp.setPadding(20, 5, 0, 0);
                            timestamp.setTextSize(textSize);
                            timestamp.setText(post.getString("time_added"));
                            timestamp.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            name_wrapper.addView(timestamp);

                            TextView txtStatusMsg = new TextView(getApplicationContext());
                            txtStatusMsg.setPadding(50, 10, 20, 50);
                            txtStatusMsg.setTextSize(17);
                            txtStatusMsg.setText(post.getString("body"));
                            txtStatusMsg.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            homePost.addView(txtStatusMsg);

                            String text = post.getString("picture_added");
                            Log.d("link", text);
                            ImageView imageView1 = new ImageView(getApplicationContext());
                            imageView1.setAdjustViewBounds(true);
                            imageView1.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            new ImageLinkLoad(text, imageView1).execute();
                            homeLayout.addView(imageView1);


                        }

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
        GetPost getPost = new GetPost(o, username, profileUser, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(profile_layout.this);
        queue.add(getPost);

    }
}
