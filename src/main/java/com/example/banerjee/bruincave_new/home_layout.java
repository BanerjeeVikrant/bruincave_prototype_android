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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);

        final String MY_PREFS_NAME = "userinfo";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String username = prefs.getString("username", null);
        if (username == null) {
            Intent loginIntent = new Intent(home_layout.this, Login_Activity.class);
            home_layout.this.startActivity(loginIntent);
        } else {
            TabHost tabHost = (TabHost) findViewById(R.id.tabHost2);

            // Define Tabhost
            tabHost.setup();

            TabHost.TabSpec tabSpec = tabHost.newTabSpec("home");
            tabSpec.setContent(R.id.home);
            tabSpec.setIndicator("", getResources().getDrawable(R.drawable.homegrey, null));
            tabHost.addTab(tabSpec);

            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("crush");
            tabSpec2.setContent(R.id.crush);
            tabSpec2.setIndicator("", getResources().getDrawable(R.drawable.anonymouslogowhite, null));
            tabHost.addTab(tabSpec2);

            TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("notifications");
            tabSpec3.setContent(R.id.notifications);
            tabSpec3.setIndicator("", getResources().getDrawable(R.drawable.notificationbellgrey, null));
            tabHost.addTab(tabSpec3);

            TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("messages");
            tabSpec4.setContent(R.id.messages);
            tabSpec4.setIndicator("", getResources().getDrawable(R.drawable.messagegrey, null));
            tabHost.addTab(tabSpec4);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            bringPosts(0,username, username);
            bringCrush(0);
            bringNotifications(0,username);
            bringUsersMsg(0,username, "");


            ScrollView homeScroll = (ScrollView) findViewById(R.id.homeScrollbar);
            LinearLayout homeScrollbar = (LinearLayout) findViewById(R.id.homeScroll);
            ScrollView crushScroll = (ScrollView) findViewById(R.id.crushScrollbar);
            LinearLayout crushScrollbar = (LinearLayout) findViewById(R.id.crushScroll);
            ScrollView notificationsScroll = (ScrollView) findViewById(R.id.notificationsScrollbar);
            LinearLayout notificationsScrollbar = (LinearLayout) findViewById(R.id.notificationsScroll);

            if(homeScrollbar.getMeasuredHeight() >= homeScroll.getScrollY() + homeScroll.getHeight()) {

            }
            else {

            }
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(home_layout.this);
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
        RequestQueue queue = Volley.newRequestQueue(home_layout.this);
        queue.add(getPost);

    }
    public void bringCrush(int o){
        Response.Listener<String> crushListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String crush) {
                try {
                    JSONObject crushResponse = new JSONObject(crush);
                    if (crushResponse != null) {
                        JSONArray crushArray = crushResponse.getJSONArray("crush");
                        LinearLayout crushLayout = (LinearLayout) findViewById(R.id.crushScroll);

                        for (int i = 0; i < crush.length(); i++) {
                            JSONObject crushObject = crushArray.getJSONObject(i);
                            Log.d("crush-id:", "" + crushObject.getInt("id"));

                            //int dimen = (int) getResources().getDimension(R.dimen.feed_item_padding_top_bottom);
                            int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                            LinearLayout homePost = new LinearLayout(getApplicationContext());
                            homePost.setBackgroundResource(R.color.white);
                            homePost.setOrientation(LinearLayout.VERTICAL);
                            homePost.setPaddingRelative(0, 0, 0, 0);
                            LinearLayout.LayoutParams layout_141 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layout_141.setMargins(0, marginTop, 0, 0);
                            homePost.setLayoutParams(layout_141);
                            crushLayout.addView(homePost);

                            LinearLayout informations = new LinearLayout(getApplicationContext());
                            informations.setOrientation(LinearLayout.HORIZONTAL);
                            informations.setPadding(0, 0, 15, 0);
                            LinearLayout.LayoutParams layout_501 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            informations.setLayoutParams(layout_501);
                            homePost.addView(informations);

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
                            name.setText("Anonymous");
                            name.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            name_wrapper.addView(name);

                            TextView timestamp = new TextView(getApplicationContext());
                            timestamp.setTextColor(getResources().getColor(R.color.timestamp));
                            timestamp.setPadding(20, 5, 0, 0);
                            timestamp.setTextSize(textSize);
                            timestamp.setText(crushObject.getString("time_added"));
                            timestamp.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            name_wrapper.addView(timestamp);

                            TextView txtStatusMsg = new TextView(getApplicationContext());
                            txtStatusMsg.setPadding(50, 10, 20, 50);
                            txtStatusMsg.setTextSize(17);
                            txtStatusMsg.setText(crushObject.getString("body"));
                            txtStatusMsg.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            homePost.addView(txtStatusMsg);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(home_layout.this);
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
        GetCrush getCrush = new GetCrush(o, crushListener);
        RequestQueue queue1 = Volley.newRequestQueue(home_layout.this);
        queue1.add(getCrush);
    }

    public void bringNotifications(int o, String username){
        Response.Listener<String> notificationsListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String notifications) {
                try {
                    JSONObject notificationsResponse = new JSONObject(notifications);
                    Log.d("notifications:", notifications);
                    if (notificationsResponse != null) {
                        JSONArray notificationsArray = notificationsResponse.getJSONArray("notifications");
                        LinearLayout notificationsLayout = (LinearLayout) findViewById(R.id.notificationsScroll);

                        for (int i = 0; i < notifications.length(); i++) {
                            JSONObject notificationsObject = notificationsArray.getJSONObject(i);
                            Log.d("notifications-id:", "" + notificationsObject.getInt("id"));

                            int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                            LinearLayout homePost = new LinearLayout(getApplicationContext());
                            homePost.setBackgroundResource(R.color.white);
                            homePost.setOrientation(LinearLayout.VERTICAL);
                            homePost.setPaddingRelative(0, 0, 0, 0);
                            LinearLayout.LayoutParams layout_141 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layout_141.setMargins(0, marginTop, 0, 0);
                            homePost.setLayoutParams(layout_141);
                            notificationsLayout.addView(homePost);

                            LinearLayout informations = new LinearLayout(getApplicationContext());
                            informations.setOrientation(LinearLayout.HORIZONTAL);
                            informations.setPadding(0, 0, 15, 0);
                            LinearLayout.LayoutParams layout_501 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            informations.setLayoutParams(layout_501);
                            homePost.addView(informations);

                            String text = notificationsObject.getString("fromPic");
                            ImageView imageView1 = new ImageView(getApplicationContext());
                            imageView1.setAdjustViewBounds(true);
                            int imagePicHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                            int imagePicWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imagePicHeight, imagePicWidth);
                            lp.setMargins(40, 40, 15, 40);
                            imageView1.setLayoutParams(lp);
                            //new ImageLinkLoad(text, imageView1).execute();
                            informations.addView(imageView1);


                            TextView name = new TextView(getApplicationContext());
                            name.setText(notificationsObject.getString("fromFirst"));
                            name.setTextSize(16);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            params.setMargins(15, 70, 15, 30);
                            name.setLayoutParams(params);
                            informations.addView(name);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetNotifications getNotifications = new GetNotifications(o, username, notificationsListener);
        RequestQueue queue2 = Volley.newRequestQueue(home_layout.this);
        queue2.add(getNotifications);
    }

    public void bringUsersMsg(int o, String username, String str){
        Response.Listener<String> usersListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String users) {
                try {
                    JSONObject usersResponse = new JSONObject(users);
                    Log.d("users:", users);
                    if (usersResponse != null) {
                        JSONArray usersArray = usersResponse.getJSONArray("usersMsg");
                        LinearLayout usersLayout = (LinearLayout) findViewById(R.id.userswrapper);

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject usersObject = usersArray.getJSONObject(i);
                            Log.d("users-id:", "" + usersObject.getInt("id"));
                            int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                            LinearLayout homePost = new LinearLayout(getApplicationContext());
                            homePost.setBackgroundResource(R.color.white);

                            homePost.setOrientation(LinearLayout.VERTICAL);
                            homePost.setPaddingRelative(0, 0, 0, 0);
                            LinearLayout.LayoutParams layout_141 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layout_141.setMargins(0, marginTop, 0, 0);
                            homePost.setLayoutParams(layout_141);
                            usersLayout.addView(homePost);

                            LinearLayout informations = new LinearLayout(getApplicationContext());
                            informations.setOrientation(LinearLayout.HORIZONTAL);
                            informations.setPadding(0, 0, 15, 0);
                            LinearLayout.LayoutParams layout_501 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            informations.setLayoutParams(layout_501);
                            homePost.addView(informations);

                            String text = usersObject.getString("fromPic");
                            ImageView imageView1 = new ImageView(getApplicationContext());
                            imageView1.setAdjustViewBounds(true);
                            int imagePicHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                            int imagePicWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imagePicHeight, imagePicWidth);
                            lp.setMargins(40, 40, 15, 40);
                            imageView1.setLayoutParams(lp);
                            //new ImageLinkLoad(text, imageView1).execute();
                            informations.addView(imageView1);


                            TextView name = new TextView(getApplicationContext());
                            name.setText(usersObject.getString("name"));
                            name.setTextSize(16);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            params.setMargins(15, 70, 15, 30);
                            name.setLayoutParams(params);
                            informations.addView(name);

                            TextView txt = new TextView(getApplicationContext());
                            txt.setText(usersObject.getString("body"));
                            txt.setTextSize(16);
                            LinearLayout.LayoutParams txtP = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            txtP.setMargins(15, 70, 15, 30);
                            txt.setLayoutParams(txtP);
                            informations.addView(txt);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        BringUsersMsg bringUsersMsg = new BringUsersMsg(o, username, str, usersListener);
        RequestQueue queue3 = Volley.newRequestQueue(home_layout.this);
        queue3.add(bringUsersMsg);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent profileIntent = new Intent(home_layout.this, profile_layout.class);
            home_layout.this.startActivity(profileIntent);
        }
        else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(home_layout.this, settings_layout.class);
            home_layout.this.startActivity(settingsIntent);
        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
