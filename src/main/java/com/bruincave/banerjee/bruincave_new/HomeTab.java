package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

/**
 * Created by Vikrant Banerjee on 2/8/2017.
 */
public class HomeTab extends Fragment{
    private int offset = 0;
    private int offsetAll = 0;
    private int offsetAllSop = 0;
    private int offsetAllJun = 0;
    private int offsetAllSen = 0;

    public boolean isLoadingAll = false;
    public boolean isLoadingSop = false;
    public boolean isLoadingJun = false;
    public boolean isLoadingSen = false;
    public boolean isLoading = false;

    private int preLast = 0;
    private int preLastAll = 0;
    private int preLastAllSop = 0;
    private int preLastAllJun = 0;
    private int preLastAllSen = 0;

    private boolean createTab = true;
    private View footerView;

    private TextView headingText;

    private int current_grade;
    private String current_grade_name;
    private String username;

    public HomeTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("XXX","HomeTab onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("XXX","HomeTab onCreateView");

        createTab = true;

        offset = 0;
        offsetAll = 0;
        offsetAllSop = 0;
        offsetAllJun = 0;
        offsetAllSen = 0;
        View view = inflater.inflate(R.layout.home_tab, container, false);
        /*
        RecyclerView rView =  (RecyclerView) view.findViewById(R.id.listView);
        rView.setAdapter(new PostAdapter(getContext(),null));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);

        rView =  (RecyclerView) view.findViewById(R.id.listView2);
        rView.setAdapter(new PostAdapter(getContext(),null));
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);

        rView =  (RecyclerView) view.findViewById(R.id.listViewSop);
        rView.setAdapter(new PostAdapter(getContext(),null));
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);

        rView =  (RecyclerView) view.findViewById(R.id.listViewJun);
        rView.setAdapter(new PostAdapter(getContext(),null));
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);

        rView =  (RecyclerView) view.findViewById(R.id.listViewSen);
        rView.setAdapter(new PostAdapter(getContext(),null));
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);
        */

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("XXX","HomeTab onStart");

        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        //RecyclerView postAllListView = (RecyclerView) getView().findViewById(R.id.listView);
        //RecyclerView postAllListViewSop = (RecyclerView) getView().findViewById(R.id.listViewSop);
        //RecyclerView postAllListViewJun = (RecyclerView) getView().findViewById(R.id.listViewJun);
        //RecyclerView postAllListViewSen = (RecyclerView) getView().findViewById(R.id.listViewSen);
        //RecyclerView postListView = (RecyclerView) getView().findViewById(R.id.listView2);


        if(createTab) {

/*
            postListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                private int currentVisibleItemCount;
                private int currentScrollState;
                private int currentFirstVisibleItem;
                private int totalItem;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // TODO Auto-generated method stub
                    this.currentScrollState = scrollState;
                    this.isScrollCompleted();
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub
                    this.currentFirstVisibleItem = firstVisibleItem;
                    this.currentVisibleItemCount = visibleItemCount;
                    this.totalItem = totalItemCount;

                    Log.d("ScrollLog", "scrollStateChanged");
                    toolbar.setVisibility(View.GONE);

                }

                private void isScrollCompleted() {
                    if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                         && this.currentScrollState == SCROLL_STATE_IDLE) {
                     toolbar.setVisibility(View.VISIBLE);
                     Log.d("ScrollLog", "scrolling");

                    }
                }
            });

            */


            SharedPreferences prefs = this.getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            username = prefs.getString("username", null);

            final TabHost tabHost = (TabHost) getView().findViewById(R.id.tabHost);
            tabHost.setup();

            final TabHost.TabSpec tab1 = tabHost.newTabSpec("Freshmen Tab");
            final TabHost.TabSpec tab2 = tabHost.newTabSpec("Sophomore Tab");
            final TabHost.TabSpec tab3 = tabHost.newTabSpec("Junior Tab");
            final TabHost.TabSpec tab4 = tabHost.newTabSpec("Senior Tab");
            final TabHost.TabSpec tab5 = tabHost.newTabSpec("Fav Tab");

            tab1.setContent(R.id.postFromAll);
            tab2.setContent(R.id.postFromAllSop);
            tab3.setContent(R.id.postFromAllJun);
            tab4.setContent(R.id.postFromAllSen);
            tab5.setContent(R.id.postFromFav);

            tab1.setIndicator("", getResources().getDrawable(R.drawable.freopenorclose, null));
            tab2.setIndicator("", getResources().getDrawable(R.drawable.sopopenorclose, null));
            tab3.setIndicator("", getResources().getDrawable(R.drawable.junopenorclose, null));
            tab4.setIndicator("", getResources().getDrawable(R.drawable.senopenorclose, null));
            tab5.setIndicator("", getResources().getDrawable(R.drawable.favopenorclose, null));

            createTab = false;

            headingText = (TextView) getActivity().findViewById(R.id.headingText);

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


                                current_grade = usersObject.getInt("grade");
                                if(current_grade == 9){
                                    current_grade_name = "Freshmen";
                                    tabHost.setCurrentTab(0);
                                }
                                else if(current_grade == 10){
                                    current_grade_name = "Sophomore";
                                    tabHost.setCurrentTab(1);
                                }
                                else if(current_grade == 11){
                                    current_grade_name = "Junior";
                                    tabHost.setCurrentTab(2);
                                }
                                else if(current_grade == 12){
                                    current_grade_name = "Senior";
                                    tabHost.setCurrentTab(3);
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            BringUserData bringUserData = new BringUserData(username, username, navListener);
            RequestQueue queue3 = Volley.newRequestQueue(getContext());
            queue3.add(bringUserData);

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

                public void onTabChanged(String tabId) {
                switch (tabHost.getCurrentTab()) {
                    case 0:
                        headingText.setText("Freshmen");
                        Log.d("Nowit", "thischangingit");
                        break;
                    case 1:
                        headingText.setText("Sophomore");
                        break;
                    case 2:
                        headingText.setText("Junior");
                        break;
                    case 3:
                        headingText.setText("Senior");
                        break;
                    case 4:
                        headingText.setText("Favorites");
                        break;
                    default:

                        break;
                }

                }
            });

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);
            tabHost.addTab(tab3);
            tabHost.addTab(tab4);
            tabHost.addTab(tab5);


         //   footerView =  ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
/*
            postAllListView.addFooterView(footerView);
            postAllListViewSop.addFooterView(footerView);
            postAllListViewJun.addFooterView(footerView);
            postAllListViewSen.addFooterView(footerView);
            postListView.addFooterView(footerView);
            */
        }
        if(getView() != null) {
            bringPostsAll(getArguments().getString("username"), "");
            bringPostsAllSop(getArguments().getString("username"), "");
            bringPosts(getArguments().getString("username"), "from if null");
            bringPostsAllJun(getArguments().getString("username"), "");
            bringPostsAllSen(getArguments().getString("username"), "");
        }

/*
        postListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLast!=lastItem) {

                        if(totalItemCount != 0) {
                            bringPosts(getArguments().getString("username"), "from scroll");
                        }

                        Log.d("ZZZ", offset + "");

                        preLast = lastItem;
                    }
                }
            }
        });
*/
        /*
        postAllListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAll!=lastItem) {
                        if(totalItemCount != 0) {
                            bringPostsAll(getArguments().getString("username"), "");
                        }

                        Log.d("ZZZAll", offset + "");

                        preLastAll = lastItem;
                    }
                }
            }
        });
*/
        /*
        postAllListViewSop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllSop!=lastItem) {
                        if(totalItemCount != 0) {
                            bringPostsAllSop(getArguments().getString("username"), "");
                        }

                        Log.d("ZZZAll", offset + "");

                        preLastAllSop = lastItem;
                    }
                }
            }
        });
        */
/*
        postAllListViewJun.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllJun!=lastItem) {
                        if(totalItemCount != 0) {
                            bringPostsAllJun(getArguments().getString("username"), "");
                        }

                        Log.d("ZZZAll", offset + "");

                        preLastAllJun = lastItem;
                    }
                }
            }
        });
        */
/*
        postAllListViewSen.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScroll(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllSen!=lastItem) {
                        if(totalItemCount != 0) {
                            bringPostsAllSen(getArguments().getString("username"), "");
                        }

                        Log.d("ZZZAll", offset + "");

                        preLastAllSen = lastItem;
                    }
                }
            }
        });
*/
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    PostAdapter postAdapter = null;
    public void bringPosts(String username, String profileUser) {
        isLoading = true;
        final String currUserName = username;
        final View currView = getView();
        final HomeTab homeTab = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSPXXX:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        ArrayList<Post> info = new ArrayList<Post>();

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Post newPost =  new Post();
                            newPost.userid = post.getInt("userid");
                            newPost.id = post.getInt("id");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = getArguments().getString("username");
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
                            //bringComments(post.getInt("id"));
                        }
                        //RecyclerView postListView = (RecyclerView) currView.findViewById(R.id.listView2);
                        LinearLayout containerFav = (LinearLayout) currView.findViewById(R.id.containerFav);
                        if (offset == 0) {
                            RecyclerView postFav = new RecyclerView(getContext());
                            containerFav.addView(postFav);
                            postAdapter = new PostAdapter(getContext(), info);
                            postFav.setAdapter(postAdapter);
                            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            postFav.setLayoutManager(llm);
                            postFav.addOnScrollListener(new RecyclerView.OnScrollListener(){
                                int pastVisiblesItems, visibleItemCount, totalItemCount;

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    if(dy > 0) //check for scroll down
                                    {
                                        visibleItemCount = llm.getChildCount();
                                        totalItemCount = llm.getItemCount();
                                        pastVisiblesItems = llm.findFirstVisibleItemPosition();
                                        Log.d("blahFre", offset + ":" + pastVisiblesItems + "+" + visibleItemCount + ">=" + totalItemCount);

                                        if (!homeTab.isLoading)
                                        {
                                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                            {
                                                homeTab.bringPostsAll(getArguments().getString("username"), "");

                                                Log.d("blahFre", offset + ": loading now");
                                                //Do pagination.. i.e. fetch new data
                                            }
                                        }
                                    }
                                }
                            });

                            Log.d("ADPTR",  "Fav after setAdapter n setLayoutManager");
                        } else {
                            RecyclerView postFav = (RecyclerView) containerFav.getChildAt(0);
                            postAdapter = (PostAdapter)postFav.getAdapter();
                            postAdapter.addAll(info);
                            postAdapter.notifyDataSetChanged();
                        }
                        offset = offset + 5;
                        homeTab.isLoading = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ADPTR", "catch for Fav");
                }
            }
        };
        Log.d("ADPTR", "Finish Fav bringPosts()");
        GetPost getPost = new GetPost(offset, username, profileUser, 0, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);
    }

    PostAdapter postAdapterAll = null;
    public void bringPostsAll(String username, String profileUser) {
        isLoadingAll = true;
        final View currView = getView();
        final HomeTab homeTab = this;
        int offsetNow;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSPXXXAll:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();
                        Log.d("Debug", "inside json");


                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Log.d("Debug", "inside loop"+i);
                            Post newPost =  new Post();
                            newPost.id = post.getInt("id");
                            newPost.userid = post.getInt("userid");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = getArguments().getString("username");
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
                            //bringComments(post.getInt("id"));
                        }
                        LinearLayout containerFrs = (LinearLayout) currView.findViewById(R.id.containerFrs);

                        if (offsetAll == 0) {
                            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            RecyclerView postFrs = new RecyclerView(getContext());
                            postFrs.addOnScrollListener(new RecyclerView.OnScrollListener(){
                                int pastVisiblesItems, visibleItemCount, totalItemCount;

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    if(dy > 0) //check for scroll down
                                    {
                                        visibleItemCount = llm.getChildCount();
                                        totalItemCount = llm.getItemCount();
                                        pastVisiblesItems = llm.findFirstVisibleItemPosition();
                                        Log.d("blahFre", offsetAll + ":" + pastVisiblesItems + "+" + visibleItemCount + ">=" + totalItemCount);

                                        if (!homeTab.isLoadingAll)
                                        {
                                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                            {
                                                homeTab.bringPostsAll(getArguments().getString("username"), "");

                                                Log.d("blahFre", offsetAll + ": loading now");
                                                //Do pagination.. i.e. fetch new data
                                            }
                                        }
                                    }
                                }
                            });
                            containerFrs.addView(postFrs);
                            postAdapter = new PostAdapter(getContext(), info);
                            postFrs.setAdapter(postAdapter);
                            postFrs.setLayoutManager(llm);
                            Log.d("ADPTR",  "Frs after setAdapter n setLayoutManager :"+postAdapter.getItemCount());
                        } else {
                            RecyclerView postFrs = (RecyclerView) containerFrs.getChildAt(0);
                            postAdapter = (PostAdapter) postFrs.getAdapter();
                            postAdapter.addAll(info);
                            postAdapter.notifyDataSetChanged();
                            Log.d("ADPTR",  "Frs addmore info: "+postAdapter.getItemCount());
                        }
                        offsetAll = offsetAll + 5;
                        homeTab.isLoadingAll = false;}

                } catch (JSONException e) {
                    e.printStackTrace();
//                    RecyclerView postListView = (RecyclerView) currView.findViewById(R.id.listView);
//                    postListView.removeFooterView(footerView);
                }
            }
        };
        GetPost getPost = new GetPost(offsetAll, username, profileUser, 0, 1, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);


    }

    PostAdapter postAdapterAllSop = null;
    public void bringPostsAllSop(final String username, String profileUser) {
        isLoadingSop = true;
        final String currUserName = username;
        final View currView = getView();
        final HomeTab homeTab = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("disapear", response);
                    Log.d("RSPXXXAll:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();

                        Log.d("Debug", "inside json");

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Log.d("Debug", "inside loop"+i);
                            Post newPost =  new Post();
                            newPost.id = post.getInt("id");
                            newPost.userid = post.getInt("userid");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = getArguments().getString("username");
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
                            //bringComments(post.getInt("id"));
                        }
                        LinearLayout containerSop = (LinearLayout) currView.findViewById(R.id.containerSop);

                        if (offsetAllSop == 0) {
                            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            RecyclerView postSop = new RecyclerView(getContext());
                            postSop.addOnScrollListener(new RecyclerView.OnScrollListener(){
                                int pastVisiblesItems, visibleItemCount, totalItemCount;

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    if(dy > 0) //check for scroll down
                                    {
                                        visibleItemCount = llm.getChildCount();
                                        totalItemCount = llm.getItemCount();
                                        pastVisiblesItems = llm.findFirstVisibleItemPosition();
                                        Log.d("blahFre", offsetAll + ":" + pastVisiblesItems + "+" + visibleItemCount + ">=" + totalItemCount);

                                        if (!homeTab.isLoadingSop)
                                        {
                                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                            {
                                                homeTab.bringPostsAll(getArguments().getString("username"), "");

                                                Log.d("blahFre", offsetAll + ": loading now");
                                                //Do pagination.. i.e. fetch new data
                                            }
                                        }
                                    }
                                }
                            });
                            postSop.setLayoutManager(llm);
                            containerSop.addView(postSop);
                            postAdapter = new PostAdapter(getContext(), info);
                            postSop.setAdapter(postAdapter);
                            Log.d("ADPTR",  "Sop after setAdapter n setLayoutManager");
                        } else {
                            RecyclerView postSop = (RecyclerView) containerSop.getChildAt(0);
                            postAdapter = (PostAdapter) postSop.getAdapter();
                            postAdapter.addAll(info);
                            postAdapter.notifyDataSetChanged();
                        }
                        offsetAllSop = offsetAllSop + 5;
                        homeTab.isLoadingSop = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offsetAllSop, username, profileUser, 0, 2, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);
    }

    PostAdapter postAdapterAllJun = null;
    public void bringPostsAllJun(String username, String profileUser) {
        isLoadingJun = true;
        final String currUserName = username;
        final View currView = getView();
        final HomeTab homeTab = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSPXXXAll:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();

                        Log.d("Debug", "inside json");

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Log.d("Debug", "inside loop"+i);
                            Post newPost =  new Post();
                            newPost.id = post.getInt("id");
                            newPost.userid = post.getInt("userid");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = getArguments().getString("username");
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
                            //bringComments(post.getInt("id"));
                        }
                        if (getView() !=  null) {
                            LinearLayout containerJun = (LinearLayout) currView.findViewById(R.id.containerJun);

                            if (offsetAllJun == 0) {
                                RecyclerView postJun = new RecyclerView(getContext());
                                containerJun.addView(postJun);
                                postAdapter = new PostAdapter(getContext(), info);
                                postJun.setAdapter(postAdapter);
                                final LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                postJun.setLayoutManager(llm);
                                postJun.addOnScrollListener(new RecyclerView.OnScrollListener(){
                                    int pastVisiblesItems, visibleItemCount, totalItemCount;

                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        if(dy > 0) //check for scroll down
                                        {
                                            visibleItemCount = llm.getChildCount();
                                            totalItemCount = llm.getItemCount();
                                            pastVisiblesItems = llm.findFirstVisibleItemPosition();
                                            Log.d("blahFre", offsetAll + ":" + pastVisiblesItems + "+" + visibleItemCount + ">=" + totalItemCount);

                                            if (!homeTab.isLoadingJun)
                                            {
                                                if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                                {
                                                    homeTab.bringPostsAll(getArguments().getString("username"), "");

                                                    Log.d("blahFre", offsetAll + ": loading now");
                                                    //Do pagination.. i.e. fetch new data
                                                }
                                            }
                                        }
                                    }
                                });
                                Log.d("ADPTR",  "Jun after setAdapter n setLayoutManager");
                            } else {
                                RecyclerView postJun = (RecyclerView) containerJun.getChildAt(0);
                                postAdapter = (PostAdapter) postJun.getAdapter();
                                postAdapter.addAll(info);
                                postAdapter.notifyDataSetChanged();
                            }

                            offsetAllJun = offsetAllJun + 5;
                            homeTab.isLoadingJun = false;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offsetAllJun, username, profileUser, 0, 3, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);
    }

    PostAdapter postAdapterAllSen = null;
    public void bringPostsAllSen(String username, String profileUser) {
        isLoadingSen = true;
        final String currUserName = username;
        final View currView = getView();
        final HomeTab homeTab = this;
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSPXXXAll:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();

                        Log.d("Debug", "inside json");

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Log.d("Debug", "inside loop"+i);
                            Post newPost =  new Post();
                            newPost.id = post.getInt("id");
                            newPost.userid = post.getInt("userid");
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.likedByMe = post.getInt("likedByMe");
                            newPost.picture_added = post.getString("picture_added");
                            newPost.username = getArguments().getString("username");
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
                            //bringComments(post.getInt("id"));
                        }
                        LinearLayout containerSen = (LinearLayout) currView.findViewById(R.id.containerSen);

                        if (offsetAllSen == 0) {
                            RecyclerView postSen = new RecyclerView(getContext());
                            containerSen.addView(postSen);
                            postAdapter = new PostAdapter(getContext(), info);
                            postSen.setAdapter(postAdapter);
                            final LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            postSen.setLayoutManager(llm);
                            postSen.addOnScrollListener(new RecyclerView.OnScrollListener(){
                                int pastVisiblesItems, visibleItemCount, totalItemCount;

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    if(dy > 0) //check for scroll down
                                    {
                                        visibleItemCount = llm.getChildCount();
                                        totalItemCount = llm.getItemCount();
                                        pastVisiblesItems = llm.findFirstVisibleItemPosition();
                                        Log.d("blahFre", offsetAll + ":" + pastVisiblesItems + "+" + visibleItemCount + ">=" + totalItemCount);

                                        if (!homeTab.isLoadingSen)
                                        {
                                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                            {
                                                homeTab.bringPostsAll(getArguments().getString("username"), "");

                                                Log.d("blahFre", offsetAll + ": loading now");
                                                //Do pagination.. i.e. fetch new data
                                            }
                                        }
                                    }
                                }
                            });
                            Log.d("ADPTR",  "Sen after setAdapter n setLayoutManager");
                        } else {
                            RecyclerView postSen = (RecyclerView) containerSen.getChildAt(0);
                            postAdapter = (PostAdapter) postSen.getAdapter();
                            postAdapter.addAll(info);
                            postAdapter.notifyDataSetChanged();
                        }

                        offsetAllSen = offsetAllSen + 5;
                        homeTab.isLoadingSen = false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offsetAllSen, username, profileUser, 0, 4, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);

    }
}
