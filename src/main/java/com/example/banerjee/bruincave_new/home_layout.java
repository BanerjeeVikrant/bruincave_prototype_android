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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class home_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int offset = 0;

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

            bringPosts(offset,username, username);
            bringCrush(0);
            bringNotifications(0,username);
            bringUsersMsg(0,username, "");

            /*
            ListView yourListView = (ListView) findViewById(R.id.listView);
            String failed = "failed";


            Log.d("", yourListView.getAdapter().getCount() + "");

            if (yourListView.getLastVisiblePosition() == yourListView.getAdapter().getCount() - 1 && yourListView.getChildAt(yourListView.getChildCount() - 1).getBottom() <= yourListView.getHeight()) {
                //It is scrolled all the way down here

            }

            */

            //final ScrollView homeScroll = (ScrollView) findViewById(R.id.homeScrollbar);
            //final LinearLayout homeScrollbar = (LinearLayout) findViewById(R.id.homeScroll);
            //ScrollView crushScroll = (ScrollView) findViewById(R.id.crushScrollbar);
            //LinearLayout crushScrollbar = (LinearLayout) findViewById(R.id.crushScroll);
            //ScrollView notificationsScroll = (ScrollView) findViewById(R.id.notificationsScrollbar);
            //LinearLayout notificationsScrollbar = (LinearLayout) findViewById(R.id.notificationsScroll);



        }
    }

    public void bringPosts(int o, String username, String profileUser) {
        final home_layout parent_this = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                int last_id = 0;
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ListView postListView = (ListView) findViewById(R.id.listView);
                        ArrayList<Post> info = new ArrayList<Post>();

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            if(i == 0){
                                last_id = post.getInt("id");
                                offset = last_id;
                            }

                            Post newPost =  new Post();
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.picture_added = post.getString("picture_added");

                            info.add(newPost);
                        }
                        PostAdapter postAdapter = new PostAdapter(parent_this, info);
                        postListView.setAdapter(postAdapter);
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
        final home_layout parent_this = this;
        Response.Listener<String> crushListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String crush) {
                int last_id_crush = 0;
                try {
                    Log.d("RSP:", crush);
                    JSONObject crushResponse = new JSONObject(crush);
                    if (crushResponse != null) {
                        JSONArray crushArray = crushResponse.getJSONArray("crush");
                        //LinearLayout crushLayout = (LinearLayout) findViewById(R.id.crushScroll);
                        ListView crushListView = (ListView) findViewById(R.id.crushlistView);
                        ArrayList<Crush> info = new ArrayList<Crush>();

                        for (int i = 0; i < crushArray.length(); i++) {
                            JSONObject crushObject = crushArray.getJSONObject(i);

                            if(i == 0){
                                last_id_crush = crushObject.getInt("id");
                                offset = last_id_crush;
                            }

                            Crush newCrush =  new Crush();
                            newCrush.time_added = crushObject.getString("time_added");
                            newCrush.body = crushObject.getString("body");

                            info.add(newCrush);
                        }
                        CrushAdapter crushAdapter = new CrushAdapter(parent_this, info);
                        crushListView.setAdapter(crushAdapter);
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
        final home_layout parent_this = this;
        Response.Listener<String> notificationsListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String notifications) {
                int last_id_notifications = 0;
                try {
                    JSONObject notificationsResponse = new JSONObject(notifications);
                    Log.d("notifications:", notifications);
                    if (notificationsResponse != null) {
                        JSONArray notificationsArray = notificationsResponse.getJSONArray("notifications");
                        //LinearLayout notificationsLayout = (LinearLayout) findViewById(R.id.notificationsScroll);
                        ListView notificationsListView = (ListView) findViewById(R.id.notificationslistView);
                        ArrayList<Notifications> info = new ArrayList<Notifications>();

                        for (int i = 0; i < notificationsArray.length(); i++) {
                            JSONObject notificationsObject = notificationsArray.getJSONObject(i);
                            Log.d("notifications-id:", "" + notificationsObject.getInt("id"));

                            if(i == 0){
                                last_id_notifications = notificationsObject.getInt("id");
                                offset = last_id_notifications;
                            }

                            Notifications newNotifications =  new Notifications();
                            newNotifications.userpic = notificationsObject.getString("fromPic");
                            newNotifications.userFirst = notificationsObject.getString("fromFirst");

                            info.add(newNotifications);


                        }
                        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(parent_this, info);
                        notificationsListView.setAdapter(notificationsAdapter);
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
