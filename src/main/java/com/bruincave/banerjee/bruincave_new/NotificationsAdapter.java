package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONException;
import org.json.JSONObject;

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

        final Notifications notifications = getItem(position);

        ImageView userpic = (ImageView) convertView.findViewById(R.id.userpic);
        TextView userBody = (TextView) convertView.findViewById(R.id.userBody);
        TextView userTimeAdded = (TextView) convertView.findViewById(R.id.time_added);
        RelativeLayout bodyLayout = (RelativeLayout) convertView.findViewById(R.id.bodyLayout);

        bodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notifications.type == 2){

                    Intent postIntent = new Intent(getContext(), post_intent.class);
                    postIntent.putExtra("postId", notifications.postid);
                    getContext().startActivity(postIntent);

                }else if(notifications.type == 3){

                    Intent postIntent = new Intent(getContext(), post_intent.class);
                    postIntent.putExtra("postId", notifications.postid);
                    getContext().startActivity(postIntent);
                }
            }
        });

        userBody.setTypeface(fontBitter);
        userTimeAdded.setTypeface(fontSansita);

        new ImageLinkLoad(notifications.userpic, userpic).execute();
        userBody.setText(notifications.userFirst+" "+notifications.body);
        userTimeAdded.setText(notifications.time_added);


        return convertView;
    }
}
