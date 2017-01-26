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
 * Created by Vikrant Banerjee on 1/25/2017.
 */
public class UsersMsgAdapter extends ArrayAdapter<BringUsersMsg> {
    public UsersMsgAdapter(Context context, List<BringUsersMsg> info) {
        super(context, R.layout.msgUsers_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater usersMsgInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = usersMsgInflater.inflate(R.layout.msgUsers_layout, parent, false);
        }

        BringUsersMsg bringUsersMsg = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.fromPic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.name);
        TextView userbody = (TextView) convertView.findViewById(R.id.body);

        new ImageLinkLoad(bringUsersMsg.userpic, userpic).execute();
        userFirst.setText(bringUsersMsg.userFirst);
        userFirst.setText(bringUsersMsg.userFirst);

        return convertView;
    }
}
