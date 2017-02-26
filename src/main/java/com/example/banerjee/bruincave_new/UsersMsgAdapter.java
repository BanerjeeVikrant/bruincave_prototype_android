package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
public class UsersMsgAdapter extends ArrayAdapter<BringUsersMsgs> {
    public UsersMsgAdapter(Context context, List<BringUsersMsgs> info) {
        super(context, R.layout.msgusers_layout, info);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater usersMsgInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = usersMsgInflater.inflate(R.layout.msgusers_layout, parent, false);
        }
        final BringUsersMsgs bringUsersMsgs = getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getContext(), messages_intent.class);
                loginIntent.putExtra("profileUserId", bringUsersMsgs.id);
                getContext().startActivity(loginIntent);
            }
        });

        ImageView userpic = (ImageView) convertView.findViewById(R.id.followerfromPic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.followername);
        TextView userbody = (TextView) convertView.findViewById(R.id.followerlastonline);

        new ImageLinkLoad(bringUsersMsgs.fromPic, userpic).execute();
        userFirst.setText(bringUsersMsgs.name);
        userbody.setText(bringUsersMsgs.body);

        return convertView;
    }
}
