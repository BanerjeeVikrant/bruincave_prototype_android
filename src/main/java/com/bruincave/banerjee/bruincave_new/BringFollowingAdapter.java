package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 1/29/2017.
 */
public class BringFollowingAdapter extends ArrayAdapter<BringFollowing> {
    public BringFollowingAdapter(Context context, List<BringFollowing> info) {
        super(context, R.layout.followusers_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater usersMsgInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = usersMsgInflater.inflate(R.layout.followusers_layout, parent, false);
        }

        BringFollowing bringFollowing = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.followingfromPic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.followingname);
        TextView userbody = (TextView) convertView.findViewById(R.id.followinglastonline);

        new ImageLinkLoad(bringFollowing.followingPic, userpic).execute();
        userFirst.setText(bringFollowing.followingName);
        userbody.setText(bringFollowing.followingLastOnline);

        return convertView;
    }
}