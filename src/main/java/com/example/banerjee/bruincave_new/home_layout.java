package com.example.banerjee.bruincave_new;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.example.banerjee.bruincave_new.R;
import com.example.banerjee.bruincave_new.HomeTab;
import com.example.banerjee.bruincave_new.CrushTab;
import com.example.banerjee.bruincave_new.NotificationsTab;
import com.example.banerjee.bruincave_new.MessagesTab;

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
import android.widget.EditText;
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
import java.util.concurrent.TimeUnit;

public class home_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {

    final String MY_PREFS_NAME = "userinfo";
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
    /*
    private ListView postListView;
    private ListView crushListView;
    private ListView notificationsListView;
    private ListView usersMsgListView;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        username = prefs.getString("username", null);
        if (username == null) {
            Intent loginIntent = new Intent(home_layout.this, login_layout.class);
            home_layout.this.startActivity(loginIntent);
        } else {

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

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);





        }

    }


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
        Log.d("XXX","Adapter.getcount() ="+adapter.getCount());

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

    };

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
            if(mTabLayout != null) {
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
                    break;
                case 1:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabClickedIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                    break;
                case 2:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabClickedIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabIcons[3]);
                    break;
                case 3:
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                    tabLayout.getTabAt(2).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(3).setIcon(tabClickedIcons[3]);
                    break;
                default:
                    Log.d("Error","Error");
                    break;
            }


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
            Intent messagesIntent = new Intent(home_layout.this, messages_intent.class);
            home_layout.this.startActivity(messagesIntent);

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("username", null);
            editor.apply();

            Intent loginIntent = new Intent(home_layout.this, login_layout.class);
            home_layout.this.startActivity(loginIntent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
