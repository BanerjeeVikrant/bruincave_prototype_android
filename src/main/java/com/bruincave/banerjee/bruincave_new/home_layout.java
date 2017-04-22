package com.bruincave.banerjee.bruincave_new;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.example.banerjee.bruincave_new.R;
import com.bruincave.banerjee.bruincave_new.HomeTab;
import com.bruincave.banerjee.bruincave_new.CrushTab;
import com.bruincave.banerjee.bruincave_new.NotificationsTab;
import com.bruincave.banerjee.bruincave_new.MessagesTab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.SubMenu;
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
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class home_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {

    final String MY_PREFS_NAME = "userinfo";
    private FloatingActionButton fab;
    private FloatingActionButton fabText;
    private FloatingActionButton fabPhoto;
    private FloatingActionButton fabCamera;
    private int offset = 0;
    private int offset_crush = 0;
    private int offset_notifications = 0;
    private int offset_msgusers = 0;
    private String username;
    private String profileuser = "";
    private int current_tab = 0;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.homegrey,
            R.drawable.anonymouslogowhite,
            R.drawable.notificationbellgrey,
            R.drawable.messagegrey
    };
    private int[] tabClickedIcons = {
            R.drawable.homeblue,
            R.drawable.anonymouslogoblue,
            R.drawable.notificationbellblue,
            R.drawable.messageblue
    };
    private Typeface fontPTSerif, fontBitter;
    private Resources r;

    private int current_grade;
    private String current_grade_name;

    TextView headingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar);
        setContentView(R.layout.activity_home_layout);

        r = getResources();


        Log.d("AAA", "onCreate");
//fix
        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);

        username = prefs.getString("username", null);

        startService(new Intent(this, NotificationService.class));

        if (username == null) {
            Intent loginIntent = new Intent(home_layout.this, login_layout.class);
            home_layout.this.startActivity(loginIntent);
        } else {

            headingText = (TextView) findViewById(R.id.headingText);
            headingText.setTypeface(fontBitter);

            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            Response.Listener<String> navListener = new Response.Listener<String>() {
                private String[] info;
                @Override
                public void onResponse(String users) {
                    try {
                        JSONObject usersResponse = new JSONObject(users);
                        if (usersResponse != null) {
                            JSONArray usersArray = usersResponse.getJSONArray("userdata");

                            for (int i = 0; i < usersArray.length(); i++) {
                                JSONObject usersObject = usersArray.getJSONObject(i);
                                Log.d("users-id:", "" + usersObject.getInt("id"));

                                View hView =  navigationView.getHeaderView(0);
                                TextView logedInName = (TextView) hView.findViewById(R.id.logedInName);
                                com.github.siyamed.shapeimageview.CircularImageView logedInProImage = (com.github.siyamed.shapeimageview.CircularImageView) hView.findViewById(R.id.logedInProImage);
                                TextView logedInUsername = (TextView) hView.findViewById(R.id.logedInUsername);
                                Button changeProPic = (Button) hView.findViewById(R.id.changepropic);

                                logedInName.setText(usersObject.getString("firstname")+" "+usersObject.getString("lastname"));
                                logedInUsername.setText("@"+username);
                                logedInName.setTypeface(fontPTSerif);
                                logedInUsername.setTypeface(fontPTSerif);

                                changeProPic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent proPicUpload = new Intent(home_layout.this, uploadProfilePic.class);
                                        home_layout.this.startActivity(proPicUpload);
                                    }
                                });

                                current_grade = usersObject.getInt("grade");
                                if(current_grade == 9){
                                    current_grade_name = "Freshmen";
                                }
                                else if(current_grade == 10){
                                    current_grade_name = "Sophomore";
                                }
                                else if(current_grade == 11){
                                    current_grade_name = "Junior";
                                }
                                else if(current_grade == 12){
                                    current_grade_name = "Senior";
                                }

                                new ImageLinkLoad(usersObject.getString("userpic"), logedInProImage).execute();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            BringUserData bringUserData = new BringUserData(username, username, navListener);
            RequestQueue queue3 = Volley.newRequestQueue(this);
            queue3.add(bringUserData);

            fab = (FloatingActionButton) findViewById(R.id.fabPost);
            fabText = (FloatingActionButton) findViewById(R.id.fabTextPost);
            fabPhoto = (FloatingActionButton) findViewById(R.id.fabPhotoPost);
            fabCamera = (FloatingActionButton) findViewById(R.id.fabCameraPost);

            fabText.setVisibility(View.GONE);
            fabPhoto.setVisibility(View.GONE);
            fabCamera.setVisibility(View.GONE);

            fabText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openHomePost = new Intent(home_layout.this, postHome_intent.class);
                    home_layout.this.startActivity(openHomePost);
                    //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }
            });

            fabPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openHomeCameraPost = new Intent(home_layout.this, postHomeCamera_intent.class);
                    home_layout.this.startActivity(openHomeCameraPost);
                }
            });

            fabCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openCameraPost = new Intent(home_layout.this, postCamera_intent.class);
                    home_layout.this.startActivity(openCameraPost);
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (current_tab) {
                        case 0:
                            if (fabText.getVisibility() == View.VISIBLE) {
                                fabText.setVisibility(View.GONE);
                                fabPhoto.setVisibility(View.GONE);
                                fabCamera.setVisibility(View.GONE);
                            } else {
                                fabText.setVisibility(View.VISIBLE);
                                fabPhoto.setVisibility(View.VISIBLE);
                                fabCamera.setVisibility(View.VISIBLE);
                            }

                            break;
                        case 1:
                            Intent openAnonPost = new Intent(home_layout.this, postAnon_intent.class);
                            home_layout.this.startActivity(openAnonPost);
                            //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

                            break;
                        case 2:
                            Log.d("Error", "Error");
                            break;
                        case 3:

                            break;
                        default:
                            Log.d("Error", "Error");
                            break;


                    }
                }
            });

            ImageButton search = (ImageButton) findViewById(R.id.search);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent searchIntent = new Intent(home_layout.this, SearchActivity.class);
                    home_layout.this.startActivity(searchIntent);
                }
            });



            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);


            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
            tabLayout.getTabAt(0).setIcon(tabClickedIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);

            viewPager.addOnPageChangeListener(new MyPageScrollListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener());


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            //Fix the navigation Bar
            /*
            Menu m = navigationView.getMenu();
            SubMenu branhamTrendsMenu = m.addSubMenu("Branham Trends");
            branhamTrendsMenu.add(0, 10, Menu.NONE, "Get Last 5 Packets");
            branhamTrendsMenu.add(0, 15, Menu.NONE, "Get Last 10 Packets");
            branhamTrendsMenu.add(0, 20, Menu.NONE, "Get Last 20 Packets");

            MenuItem mi = m.getItem(m.size()-1);
            mi.setTitle(mi.getTitle());

            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case 10:
                            Toast.makeText(home_layout.this, "Now "+item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        case 15:
                            Toast.makeText(home_layout.this, "Now = "+item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        case 20:
                            Toast.makeText(home_layout.this, "Now == "+item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                }
            });

            */


        }


    }

    private int preLast = 0;

    @Override
    public void onScrollStateChanged(AbsListView view, int state) {

    }

    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            if (preLast != lastItem) {
                /*
                if(current_tab == 0) {
                    bringPosts(username, profileuser);
                } else if(current_tab == 1) {
                    bringCrush();
                } else if(current_tab == 2) {
                    bringNotifications(username);
                }
*/
                preLast = lastItem;
            }
        }
    }


    private void setupTabIcons() {
/*
        postListView = (ListView) LayoutInflater.from(this).inflate(R.layout.home_tab, null);
        crushListView = (ListView) LayoutInflater.from(this).inflate(R.layout.crush_tab, null);
        notificationsListView = (ListView) LayoutInflater.from(this).inflate(R.layout.notifications_tab, null);
        usersMsgListView = (ListView) LayoutInflater.from(this).inflate(R.layout.messages_tab, null);
*/


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle homeArgs = new Bundle();
        homeArgs.putString("username", username);
        homeArgs.putString("profileuser", profileuser);
        HomeTab home = new HomeTab();
        home.setArguments(homeArgs);
        adapter.addFrag(home, "ONE");

        CrushTab crush = new CrushTab();
        adapter.addFrag(crush, "TWO");

        Bundle ntArgs = new Bundle();
        ntArgs.putString("username", username);
        NotificationsTab nt = new NotificationsTab();
        nt.setArguments(ntArgs);
        adapter.addFrag(nt, "THREE");

        Bundle msgArgs = new Bundle();
        msgArgs.putString("username", username);
        MessagesTab msg = new MessagesTab();
        msg.setArguments(msgArgs);
        adapter.addFrag(msg, "FOUR");
        Log.d("XXX", "Adapter.getcount() =" + adapter.getCount());

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    ;

    /*
     * This is for scrolling y-axis.
     */

    private class MyPageScrollListener implements ViewPager.OnPageChangeListener {
        private TabLayout mTabLayout;

        public MyPageScrollListener(TabLayout tabLayout) {
            this.mTabLayout = tabLayout;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("positionoffsetpixel", positionOffsetPixels + "");
            Log.d("positionoffset", positionOffset + "");
        }

        @Override
        public void onPageSelected(int position) {
            if (mTabLayout != null) {
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            if (viewPager.getCurrentItem() != position) {
                viewPager.setCurrentItem(position, true);

            }
            Log.d("position", position + "");
            switch (position) {
                case 0:
                    tabLayout.getTabAt(0).setIcon(tabClickedIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabIcons[3]);

                    fab.setVisibility(View.VISIBLE);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.postColorEdit)));
                    fab.setImageResource(R.drawable.edit);

                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
                    int wpx = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            75,
                            r.getDisplayMetrics()
                    );
                    int px = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            25,
                            r.getDisplayMetrics()
                    );

                    params.setMargins(px, px, px, wpx); //substitute parameters for left, top, right, bottom
                    fab.setLayoutParams(params);

                    headingText.setText(current_grade_name);

                    break;
                case 1:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabClickedIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabIcons[3]);

                    fab.setVisibility(View.VISIBLE);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.anonColorEdit)));
                    fab.setImageResource(R.drawable.anonymouslogo);

                    CoordinatorLayout.LayoutParams paramsd = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
                    int pxd = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            25,
                            r.getDisplayMetrics()
                    );

                    paramsd.setMargins(pxd, pxd, pxd, pxd); //substitute parameters for left, top, right, bottom
                    fab.setLayoutParams(paramsd);

                    fabText.setVisibility(View.GONE);
                    fabPhoto.setVisibility(View.GONE);
                    fabCamera.setVisibility(View.GONE);

                    headingText.setText("Anonymous");

                    break;
                case 2:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabClickedIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabIcons[3]);

                    fab.setVisibility(View.INVISIBLE);

                    fabText.setVisibility(View.GONE);
                    fabPhoto.setVisibility(View.GONE);
                    fabCamera.setVisibility(View.GONE);

                    headingText.setText("Notifications");
                    break;
                case 3:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabClickedIcons[3]);

                    fab.setVisibility(View.INVISIBLE);

                    fabText.setVisibility(View.GONE);
                    fabPhoto.setVisibility(View.GONE);
                    fabCamera.setVisibility(View.GONE);

                    headingText.setText("Messages");
                    break;
                default:
                    Log.d("Error", "Error");
                    break;


            }
            current_tab = position;
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
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
        if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("username", null);
            editor.apply();

            Intent loginIntent = new Intent(home_layout.this, login_layout.class);
            home_layout.this.startActivity(loginIntent);
            return true;
        } else if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(home_layout.this, settings_layout.class);
            home_layout.this.startActivity(settingsIntent);
            return true;
        }else if(id == R.id.action_feedback){
            Intent feedbackIntent = new Intent(home_layout.this, feedback_intent.class);
            home_layout.this.startActivity(feedbackIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*
        int id = item.getItemId();

        if (id == R.id.nav_settings) {

            Intent settingsIntent = new Intent(home_layout.this, settings_layout.class);
            home_layout.this.startActivity(settingsIntent);

        } else if (id == R.id.nav_faq) {


        } else if (id == R.id.nav_feedback) {
            Intent feedbackIntent = new Intent(home_layout.this, feedback_intent.class);
            home_layout.this.startActivity(feedbackIntent);
        } else if (id == R.id.nav_report) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
