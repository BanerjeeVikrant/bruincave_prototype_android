package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 1/25/2017.
 */
public class NotificationsAdapter extends ArrayAdapter<Notifications>{
    private Typeface fontPTSerif;
    private Typeface fontSansita;
    private Typeface fontBitter;
    public NotificationsAdapter(Context context, List<Notifications> info) {
        super(context, R.layout.notifications_layout, info);
        fontPTSerif = Typeface.createFromAsset(context.getAssets(),"fonts/PTSerif.ttf");
        fontSansita = Typeface.createFromAsset(context.getAssets(),"fonts/Sansita.otf");
        fontBitter = Typeface.createFromAsset(context.getAssets(), "fonts/Bitter.otf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater notificationsInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = notificationsInflater.inflate(R.layout.notifications_layout, parent, false);
        }

        Notifications notifications = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.userpic);
        TextView userBody = (TextView) convertView.findViewById(R.id.userBody);
        TextView userTimeAdded = (TextView) convertView.findViewById(R.id.time_added);
        userBody.setTypeface(fontBitter);
        userTimeAdded.setTypeface(fontSansita);

        new ImageLinkLoad(notifications.userpic, userpic).execute();
        userBody.setText(notifications.userFirst+" "+notifications.body);
        userTimeAdded.setText(notifications.time_added);

        return convertView;
    }
}
