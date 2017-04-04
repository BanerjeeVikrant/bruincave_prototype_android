package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 3/29/2017.
 */
public class UsersAdapter extends ArrayAdapter<BringUser> {
    private Typeface fontPTSerif;
    private String username;
    public UsersAdapter(Context context, List<BringUser> info) {
        super(context, R.layout.searchresult, info);
        fontPTSerif = Typeface.createFromAsset(context.getAssets(),"fonts/PTSerif.ttf");

        SharedPreferences prefs = context.getSharedPreferences("userinfo", context.MODE_PRIVATE);
        username = prefs.getString("username", null);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater usersMsgInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = usersMsgInflater.inflate(R.layout.searchresult, parent, false);
        }
        final BringUser bringUser = getItem(position);

        final ImageButton favButton = (ImageButton) convertView.findViewById(R.id.imageButton);

        if(bringUser.body == 1){
            favButton.setImageResource(R.drawable.likefilled);
        }
        else{
            favButton.setImageResource(R.drawable.likeoutline);
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> likePostActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSPX:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean alreadyFollowed = jsonResponse.getBoolean("success");

                            if (!alreadyFollowed) {
                                favButton.setImageResource(R.drawable.likefilled);
                            } else {
                                favButton.setImageResource(R.drawable.likeoutline);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Log.d("Success", bringUser.id + "");
                FollowAction followAction = new FollowAction(username, bringUser.id, likePostActionListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(followAction);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        com.github.siyamed.shapeimageview.CircularImageView userpic = (com.github.siyamed.shapeimageview.CircularImageView) convertView.findViewById(R.id.imageView);
        TextView userFirst = (TextView) convertView.findViewById(R.id.nameView);

        userFirst.setTypeface(fontPTSerif);

        new ImageLinkLoad(bringUser.fromPic, userpic).execute();
        userFirst.setText(bringUser.name);

        return convertView;
    }
}
