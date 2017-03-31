package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Vikrant Banerjee on 1/21/2017.
 */
public class PostAdapter extends ArrayAdapter<Post> {
    private Typeface fontPTSerif;
    public PostAdapter(Context context, List<Post> info) {
        super(context, R.layout.post_layout, info);
        fontPTSerif = Typeface.createFromAsset(context.getAssets(),"fonts/PTSerif.ttf");


    }
    private boolean forProfile =  false;
    public void setForProfile() { forProfile = true; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater postInflater = LayoutInflater.from(getContext());

        if (forProfile == true) {
            if (position == 0) {
                convertView = postInflater.inflate(R.layout.profile_top_part, parent, false);
                final TextView name = (TextView) convertView.findViewById(R.id.nameProfile);
                final TextView bio =  (TextView) convertView.findViewById(R.id.bioInfo);
                name.setTypeface(fontPTSerif);
                bio.setTypeface(fontPTSerif);
                final com.github.siyamed.shapeimageview.CircularImageView profilepic = (com.github.siyamed.shapeimageview.CircularImageView) convertView.findViewById(R.id.profilepic);
                final ImageView banner = (ImageView) convertView.findViewById(R.id.bannerpic);
                final RelativeLayout follow = (RelativeLayout) convertView.findViewById(R.id.followbtn);
                final com.github.siyamed.shapeimageview.StarImageView starImageView = (com.github.siyamed.shapeimageview.StarImageView) convertView.findViewById(R.id.starBtn);

                follow.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            //code area ...
                            Log.v("TAG", "Hello");

                            return true;
                        } else{
                            return false;
                        }
                    }
                });

                Response.Listener<String> userdataActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSP:", response);
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse != null) {
                                JSONArray usersObject = jsonResponse.getJSONArray("userdata");
                                JSONObject home = usersObject.getJSONObject(0);

                                String firstname_info = home.getString("firstname");
                                String lastname_info = home.getString("lastname");
                                String profilepic_info = home.getString("userpic");
                                String bannerpic_info = home.getString("bannerpic");
                                String bio_info = home.getString("bio");
                                int followcase = home.getInt("followcase");


                                name.setText(firstname_info+" "+lastname_info);
                                bio.setText(bio_info);
                                new ImageLinkLoad(profilepic_info, profilepic).execute();
                                new ImageLinkLoad(bannerpic_info, banner).execute();

                                if(followcase == 0){
                                    follow.setVisibility(View.GONE);
                                } else if (followcase == 1) {
                                    starImageView.setImageResource(R.drawable.yellowimage);
                                } else{
                                    new ImageLinkLoad(profilepic_info, starImageView).execute();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                BringUserData bringUserData = new BringUserData("ssdf", "banerjeevikrant",userdataActionListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(bringUserData);



                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("XXY", "CLICKED");
                    }
                });

                return convertView;
            } else {
                position--;
            }
        }
        if (convertView == null) {
            convertView = postInflater.inflate(R.layout.post_layout, parent, false);
        } else if (forProfile == true) {
            if (convertView.findViewById(R.id.timestamp) == null) { // must be top profile view, can't be reused.
                convertView = postInflater.inflate(R.layout.post_layout, parent, false);
            }
        }

        final RelativeLayout options = (RelativeLayout) convertView.findViewById(R.id.options);

        options.setVisibility(View.GONE);

        final Post post = getItem(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        nameView.setTypeface(fontPTSerif);
        TextView timestampView = (TextView) convertView.findViewById(R.id.timestamp);
        timestampView.setTypeface(fontPTSerif);
        final TextView likeBodyTextView = (TextView) convertView.findViewById(R.id.likeBodyText);
        likeBodyTextView.setTypeface(fontPTSerif);
        TextView txtStatusMsgView = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        txtStatusMsgView.setTypeface(fontPTSerif);
        final com.github.siyamed.shapeimageview.CircularImageView profileImageView = (com.github.siyamed.shapeimageview.CircularImageView) convertView.findViewById(R.id.profilepic);
        ImageView pictureAddedView = (ImageView) convertView.findViewById(R.id.picture_added);
        final ImageButton likeButton =  (ImageButton) convertView.findViewById(R.id.likeButton);
        EditText commentET = (EditText) convertView.findViewById(R.id.commentEditText);
        commentET.setTypeface(fontPTSerif);

        commentET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getContext(), post_intent.class);
                loginIntent.putExtra("postId", post.id);
                getContext().startActivity(loginIntent);
            }
        });

        if (post.likedByMe == 1) {
            likeButton.setImageResource(R.drawable.likedpaw);
        } else  {
            likeButton.setImageResource(R.drawable.notlikedpaw);
        }

        if(post.moreThanThreeLiker == 1){
            likeBodyTextView.setText(post.likesCount+" likes");
        } else if (post.moreThanThreeLiker == 2) {
            likeBodyTextView.setText("Be the first to Like");
        } else {
            //Log.d("Likedby", post.likedby);
            likeBodyTextView.setText("Liked by " + post.likedby);
        }

        final int pos = post.id;
        final String username = post.username;

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YYY","Clicked for post #"+pos);

                //Connection with the database
                Log.d("LikePost", pos+","+username);

                Response.Listener<String> likePostActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSPX:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                if(post.likedByMe == 1){
                                    post.likedByMe = 0;
                                }else{
                                    post.likedByMe = 1;
                                }
                                if (post.likedByMe == 1) {
                                    likeButton.setImageResource(R.drawable.likedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Liked by " + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        int ans = post.likesCount + 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                } else  {
                                    likeButton.setImageResource(R.drawable.notlikedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Be the first to Like");
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        /* ERROR */
                                        int ans = post.likesCount - 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                }
                            } else {
                                Log.d("RSPX", "failed");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LikePostAction likePostAction = new LikePostAction(pos, username, likePostActionListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(likePostAction);
            }
        });

        pictureAddedView.setImageResource(0);
        LinearLayout commentsView = (LinearLayout) convertView.findViewById(R.id.comments_section);
        commentsView.removeAllViews();

        commentsView.removeAllViews();
        if(post.comments == null) {
            Log.d("Comment","length-0");
        } else {
            Log.d("Comment", "length-" + post.comments.length);
        }

        if (post.comments != null) {
            for (int i = 0; i < post.comments.length; i++) {
                View comment = postInflater.inflate(R.layout.comment_layout, commentsView, false);
                TextView fromView = (TextView) comment.findViewById(R.id.from);
                TextView bodyView = (TextView) comment.findViewById(R.id.followerlastonline);
                fromView.setTypeface(fontPTSerif);
                bodyView.setTypeface(fontPTSerif);
                Log.d("Comment","body-"+post.comments[i].body);
                Log.d("Comment","from-"+post.comments[i].from);
                fromView.setText(post.comments[i].from);
                bodyView.setText(post.comments[i].body);
                commentsView.addView(comment);
            }
        }
        nameView.setText(post.name);
        timestampView.setText(post.time_added);
        txtStatusMsgView.setText(post.body);

        new ImageLinkLoad(post.userpic, profileImageView).execute();
        new ImageLinkLoad(post.picture_added, pictureAddedView).execute();

        //FOLLOW OR UNFOLLOW

        ImageButton postViewAdd = (ImageButton) convertView.findViewById(R.id.postViewAdd);

        postViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final RelativeLayout options = (RelativeLayout) convertView.findViewById(R.id.options);
                options.setVisibility(View.VISIBLE);
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options.setVisibility(View.GONE);
            }
        });
 /*
        postViewAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int userid = post.userid;

                Response.Listener<String> likePostActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSPX:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Log.d("Success", "Success");

                            } else {
                                Log.d("RSPX", "failed");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Log.d("Success", userid + "");
                FollowAction followAction = new FollowAction(username, userid, likePostActionListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(followAction);
            }




        });*/

        return convertView;
    }
}
