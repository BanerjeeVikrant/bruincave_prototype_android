package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Typeface fontPTSerif;

    ViewGroup contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = (ViewGroup)inflater.inflate(R.layout.content_post_intent,null);
        fontPTSerif = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/PTSerif.ttf");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(contentView,params);

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

                        final int id_info = post.getInt("id");
                        String userpic_info = post.getString("userpic");
                        String username_info = post.getString("username");
                        String name_info = post.getString("name");
                        String time_added_info = post.getString("time_added");
                        String body_info = post.getString("body");
                        int likedByMe_info = post.getInt("likedByMe");
                        String picture_added_info = post.getString("picture_added");
                        int likesCount_info = post.getInt("likesCount");
                        String likedby_info = post.getString("likedby");

                        com.github.siyamed.shapeimageview.CircularImageView profile_pic = (com.github.siyamed.shapeimageview.CircularImageView) contentView.findViewById(R.id.profilepic);
                        TextView name = (TextView) contentView.findViewById(R.id.name);
                        TextView usernameTV = (TextView) contentView.findViewById(R.id.username);
                        TextView txtStatusMsg = (TextView) contentView.findViewById(R.id.txtStatusMsg);
                        TextView timestamp = (TextView) contentView.findViewById(R.id.timestamp);
                        TextView likeBodyText = (TextView) contentView.findViewById(R.id.likeBodyText);
                        name.setTypeface(fontPTSerif);
                        usernameTV.setTypeface(fontPTSerif);
                        txtStatusMsg.setTypeface(fontPTSerif);
                        timestamp.setTypeface(fontPTSerif);
                        likeBodyText.setTypeface(fontPTSerif);
                        ImageView picture_added = (ImageView) contentView.findViewById(R.id.picture_added);
                        String commentString = "";
                        final EditText commentET = (EditText) findViewById(R.id.commentEditText);
                        commentET.setTypeface(fontPTSerif);

                        commentET.setOnKeyListener(new View.OnKeyListener() {
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                // If the event is a key-down event on the "enter" button
                                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                    // Perform action on key press
                                    String commentString = commentET.getText().toString().trim();
                                    Response.Listener<String> comListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("Message RSP:", response);
                                                JSONObject jsonResponse = new JSONObject(response);

                                                if (jsonResponse != null) {

                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(post_intent.this);
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
                                    SendComment sendComment = new SendComment(username, id_info, commentString, comListener);
                                    RequestQueue queue = Volley.newRequestQueue(post_intent.this);
                                    queue.add(sendComment);

                                    commentET.setText("");
                                    return true;
                                }
                                return false;
                            }
                        });

                        name.setText(name_info);
                        usernameTV.setText("@"+username_info);
                        txtStatusMsg.setText(body_info);
                        timestamp.setText(time_added_info);
                        likeBodyText.setText(likedby_info);
                        new ImageLinkLoad(userpic_info, profile_pic).execute();

                        if(picture_added_info.equals("http://www.bruincave.com/m/")) {
                            picture_added.setImageResource(0);
                            Log.d("source", "0");
                        }else{
                            new ImageLinkLoad(picture_added_info, picture_added).execute();
                            Log.d("source", picture_added_info);
                        }


                        JSONArray comments = post.getJSONArray("comments");
                        ViewGroup scrollView =  (ViewGroup)contentView.getChildAt(1);
                        ViewGroup commentsView = (ViewGroup)scrollView.getChildAt(0);
                        for (int cn = 0; cn < comments.length(); cn++) {
                            final JSONObject comment = comments.getJSONObject(cn);

                            String commentBody = comment.getString("body");
                            String commentFrom = comment.getString("from");
                            String commentPic = comment.getString("pic");
                            String commentUsername = comment.getString("username");

                            View commentView = inflater.inflate(R.layout.comment_layout_for_one, null);
                            TextView comment_body = (TextView) commentView.findViewById(R.id.commentBodyOne);
                            TextView name_body = (TextView) commentView.findViewById(R.id.commentNameOne);
                            TextView username_body = (TextView) commentView.findViewById(R.id.commentUsernameOne);
                            com.github.siyamed.shapeimageview.CircularImageView pic_body = (com.github.siyamed.shapeimageview.CircularImageView) commentView.findViewById(R.id.commentProfilePicOne);

                            comment_body.setText(commentBody);
                            name_body.setText(commentFrom);
                            username_body.setText("@"+commentUsername);
                            new ImageLinkLoad(commentPic, pic_body).execute();

                            commentsView.addView(commentView);
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
