package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TabHost;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

    private int preLast = 0;
    private int preLastAll = 0;
    private int preLastAllSop = 0;
    private int preLastAllJun = 0;
    private int preLastAllSen = 0;

    private boolean createTab = true;

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

        return inflater.inflate(R.layout.home_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("XXX","HomeTab onStart");
        if(createTab == true) {
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

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

                public void onTabChanged(String tabId) {
                    switch (tabHost.getCurrentTab()) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

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
        }

        bringPostsAll(getArguments().getString("username"), "");
        bringPostsAllSop(getArguments().getString("username"), "");
        bringPosts(getArguments().getString("username"), "");
        bringPostsAllJun(getArguments().getString("username"), "");
        bringPostsAllSen(getArguments().getString("username"), "");

        ListView postAllListView = (ListView) getView().findViewById(R.id.listView);
        ListView postAllListViewSop = (ListView) getView().findViewById(R.id.listViewSop);
        ListView postAllListViewJun = (ListView) getView().findViewById(R.id.listViewJun);
        ListView postAllListViewSen = (ListView) getView().findViewById(R.id.listViewSen);
        ListView postListView = (ListView) getView().findViewById(R.id.listView2);

        postListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLast!=lastItem) {

                        bringPosts(getArguments().getString("username"), "");

                        Log.d("ZZZ", offset + "");

                        preLast = lastItem;
                    }
                }
            }
        });

        postAllListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAll!=lastItem) {

                        bringPostsAll(getArguments().getString("username"), "");

                        Log.d("ZZZAll", offset + "");

                        preLastAll = lastItem;
                    }
                }
            }
        });

        postAllListViewSop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllSop!=lastItem) {

                        bringPostsAllSop(getArguments().getString("username"), "");

                        Log.d("ZZZAll", offset + "");

                        preLastAllSop = lastItem;
                    }
                }
            }
        });

        postAllListViewJun.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllJun!=lastItem) {

                        bringPostsAllJun(getArguments().getString("username"), "");

                        Log.d("ZZZAll", offset + "");

                        preLastAllJun = lastItem;
                    }
                }
            }
        });

        postAllListViewSen.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLastAllSen!=lastItem) {

                        bringPostsAllSen(getArguments().getString("username"), "");

                        Log.d("ZZZAll", offset + "");

                        preLastAllSen = lastItem;
                    }
                }
            }
        });

    }

    PostAdapter postAdapter = null;
    public void bringPosts(String username, String profileUser) {
        Log.d("username", username);
        Log.d("profileusername", profileUser);
        Response.Listener<String> postListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSPXXX:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
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
                            ListView postListView = (ListView) getView().findViewById(R.id.listView2);



                        if(postListView.getAdapter()==null)
                            if (offset == 0) {
                                postAdapter = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapter);
                            }else {
                                postAdapter.addAll(info);
                            }
                        else{
                            if (offset == 0) {
                                postAdapter = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapter);
                            }else {
                                postAdapter.addAll(info);
                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        offset = offset + 5;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offset, username, profileUser, 0, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);

    }

    PostAdapter postAdapterAll = null;
    public void bringPostsAll(String username, String profileUser) {
        Log.d("username", username);
        Log.d("profileusername", profileUser);
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
                        ListView postListView = (ListView) getView().findViewById(R.id.listView);

                        if(postListView.getAdapter()==null)
                            if (offsetAll == 0) {
                                postAdapterAll = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAll);
                            }else {
                                postAdapterAll.addAll(info);
                            }
                        else{
                            if (offsetAll == 0) {
                                postAdapterAll = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAll);
                            }else {
                                postAdapterAll.addAll(info);
                            }
                            postAdapterAll.notifyDataSetChanged();
                        }

                        offsetAll = offsetAll + 5;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offsetAll, username, profileUser, 0, 1, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);

    }

    PostAdapter postAdapterAllSop = null;
    public void bringPostsAllSop(String username, String profileUser) {
        Log.d("username", username);
        Log.d("profileusername", profileUser);
        Log.d("debug", "called sop");
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
                        ListView postListView = (ListView) getView().findViewById(R.id.listViewSop);

                        if(postListView.getAdapter()==null)
                            if (offsetAllSop == 0) {
                                postAdapterAllSop = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllSop);
                            }else {
                                postAdapterAllSop.addAll(info);
                            }
                        else{
                            if (offsetAllSop == 0) {
                                postAdapterAllSop = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllSop);
                            }else {
                                postAdapterAllSop.addAll(info);
                            }
                            postAdapterAllSop.notifyDataSetChanged();
                        }

                        offsetAllSop = offsetAllSop + 5;
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
        Log.d("username", username);
        Log.d("profileusername", profileUser);
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
                        ListView postListView = (ListView) getView().findViewById(R.id.listViewJun);

                        if(postListView.getAdapter()==null)
                            if (offsetAllJun == 0) {
                                postAdapterAllJun = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllJun);
                            }else {
                                postAdapterAllJun.addAll(info);
                            }
                        else{
                            if (offsetAllJun == 0) {
                                postAdapterAllJun = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllJun);
                            }else {
                                postAdapterAllJun.addAll(info);
                            }
                            postAdapterAllJun.notifyDataSetChanged();
                        }

                        offsetAllJun = offsetAllJun + 5;
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
        Log.d("username", username);
        Log.d("profileusername", profileUser);
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
                        ListView postListView = (ListView) getView().findViewById(R.id.listViewSen);

                        if(postListView.getAdapter()==null)
                            if (offsetAllSen == 0) {
                                postAdapterAllSen = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllSen);
                            }else {
                                postAdapterAllSen.addAll(info);
                            }
                        else{
                            if (offsetAllSen == 0) {
                                postAdapterAllSen = new PostAdapter(getContext(), info);
                                postListView.setAdapter(postAdapterAllSen);
                            }else {
                                postAdapterAllSen.addAll(info);
                            }
                            postAdapterAllSen.notifyDataSetChanged();
                        }

                        offsetAllSen = offsetAllSen + 5;
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
