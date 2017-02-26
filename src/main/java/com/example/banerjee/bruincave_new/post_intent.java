package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class post_intent extends AppCompatActivity {
    private int offset = 0;
    private int preLast = 0;
    private String username;
    private LayoutInflater inflater = null;
    final String MY_PREFS_NAME = "userinfo";
    private int postid;

    ViewGroup contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater)getBaseContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup scrollView = (ViewGroup)inflater.inflate(R.layout.content_post_intent,null);
        contentView =  (ViewGroup)scrollView.getChildAt(0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(scrollView,params);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("username", null);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                postid= 0;
            } else {
                postid= extras.getInt("postId");
                Log.d("Now", postid + "");
            }
        } else {
            postid= (Integer) savedInstanceState.getSerializable("profileUserId");
        }


        bringPost(postid);

    }
    public void bringPost(int postid) {
        final String user_name = username;
        Response.Listener<String> postidListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray postArray = jsonResponse.getJSONArray("post");
                        JSONObject post = postArray.getJSONObject(0);

                        int id_info = post.getInt("id");
                        String userpic_info = post.getString("userpic");
                        String username_info = post.getString("username");
                        String name_info = post.getString("name");
                        String time_added_info = post.getString("time_added");
                        String body_info = post.getString("body");
                        int likedByMe_info = post.getInt("likedByMe");
                        String picture_added_info = post.getString("picture_added");
                        int likesCount_info = post.getInt("likesCount");
                        String likedby_info = post.getString("likedby");

                        ImageView profile_pic = (ImageView) contentView.findViewById(R.id.profilepic);
                        TextView name = (TextView) contentView.findViewById(R.id.name);
                        TextView usernameTV = (TextView) contentView.findViewById(R.id.username);
                        TextView txtStatusMsg = (TextView) contentView.findViewById(R.id.txtStatusMsg);
                        TextView timestamp = (TextView) contentView.findViewById(R.id.timestamp);
                        TextView likeBodyText = (TextView) contentView.findViewById(R.id.likeBodyText);

                        name.setText(name_info);
                        usernameTV.setText("@"+username_info);
                        txtStatusMsg.setText(body_info);
                        timestamp.setText(time_added_info);
                        likeBodyText.setText(likedby_info);
                        new ImageLinkLoad(userpic_info, profile_pic).execute();

                        JSONArray comments = post.getJSONArray("comments");
                        for (int cn = 0; cn < comments.length(); cn++) {
                            JSONObject comment = comments.getJSONObject(cn);

                            String commentBody = comment.getString("body");
                            String commentFrom = comment.getString("from");
                            String commentPic = comment.getString("pic");
                            String commentUsername = comment.getString("username");

                            View commentView = inflater.inflate(R.layout.comment_layout_for_one, null);
                            TextView comment_body = (TextView) commentView.findViewById(R.id.commentBodyOne);
                            TextView name_body = (TextView) commentView.findViewById(R.id.commentNameOne);
                            TextView username_body = (TextView) commentView.findViewById(R.id.commentUsernameOne);
                            ImageView pic_body = (ImageView) commentView.findViewById(R.id.commentProfilePicOne);

                            comment_body.setText(commentBody);
                            name_body.setText(commentFrom);
                            username_body.setText(commentUsername);
                            new ImageLinkLoad(commentPic, pic_body).execute();

                            contentView.addView(commentView);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPostBasedId getPostBasedId = new GetPostBasedId(postid, username, postidListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getPostBasedId);

    }

}
