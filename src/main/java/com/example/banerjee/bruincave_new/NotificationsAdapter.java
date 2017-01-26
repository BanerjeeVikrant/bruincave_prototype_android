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
public class NotificationsAdapter extends ArrayAdapter<Notifications>{
    public NotificationsAdapter(Context context, List<Notifications> info) {
        super(context, R.layout.notifications_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater notificationsInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = notificationsInflater.inflate(R.layout.notifications_layout, parent, false);
        }

        Notifications notifications = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.userpic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.userFirst);

        new ImageLinkLoad(notifications.userpic, userpic).execute();
        userFirst.setText(notifications.userFirst);

        return convertView;
    }
}
