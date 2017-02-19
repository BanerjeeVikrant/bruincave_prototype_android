package com.example.banerjee.bruincave_new;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

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
    private int preLast = 0;
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
        offset = 0;
        return inflater.inflate(R.layout.home_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("XXX","HomeTab onStart");
        bringPosts(getArguments().getString("username"), getArguments().getString("profileuser"));
        ListView postListView = (ListView) getView().findViewById(R.id.listView);
        postListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLast!=lastItem) {

                        bringPosts(getArguments().getString("username"), getArguments().getString("profileuser"));

                        Log.d("info", offset + "");

                        preLast = lastItem;
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
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray home = jsonResponse.getJSONArray("home");
                        //LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeScroll);
                        ArrayList<Post> info = new ArrayList<Post>();

                        for (int i = 0; i < home.length(); i++) {
                            JSONObject post = home.getJSONObject(i);
                            Post newPost =  new Post();
                            newPost.userpic = post.getString("userpic");
                            newPost.name = post.getString("name");
                            newPost.time_added = post.getString("time_added");
                            newPost.body = post.getString("body");
                            newPost.picture_added = post.getString("picture_added");

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
                        if (offset == 0) {
                            postAdapter = new PostAdapter(getContext(), info);
                            postListView.setAdapter(postAdapter);
                        }
                        postAdapter.addAll(info);

                        offset = offset + 5;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPost getPost = new GetPost(offset, username, profileUser, 0, postListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPost);

    }
}
