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
 * Created by Vikrant Banerjee on 1/21/2017.
 */
public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(Context context, List<Post> info) {
        super(context, R.layout.post_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater postInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = postInflater.inflate(R.layout.post_layout, parent, false);
        }

        Post post = getItem(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView timestampView = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txtStatusMsgView = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        ImageView profileImageView = (ImageView) convertView.findViewById(R.id.profilepic);
        ImageView pictureAddedView = (ImageView) convertView.findViewById(R.id.picture_added);

        nameView.setText(post.name);
        timestampView.setText(post.time_added);
        txtStatusMsgView.setText(post.body);
        new ImageLinkLoad(post.userpic, profileImageView).execute();
        new ImageLinkLoad(post.picture_added, pictureAddedView).execute();

        return convertView;
    }
}
