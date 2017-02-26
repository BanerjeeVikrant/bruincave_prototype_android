package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 1/29/2017.
 */
public class BringFollowersAdapter extends ArrayAdapter<BringFollowers> {
    public BringFollowersAdapter(Context context, List<BringFollowers> info) {
        super(context, R.layout.followusers_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater usersMsgInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = usersMsgInflater.inflate(R.layout.followusers_layout, parent, false);
        }

        BringFollowers bringFollowers = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.followerfromPic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.followername);
        TextView userbody = (TextView) convertView.findViewById(R.id.followerlastonline);

        new ImageLinkLoad(bringFollowers.followerPic, userpic).execute();
        userFirst.setText(bringFollowers.followerName);
        userbody.setText(bringFollowers.followerLastOnline);

        return convertView;
    }
}