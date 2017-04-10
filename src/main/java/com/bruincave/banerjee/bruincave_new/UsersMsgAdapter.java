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
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 1/25/2017.
 */
public class UsersMsgAdapter extends ArrayAdapter<BringUsersMsgs> {
    private Typeface fontPTSerif;
    private Typeface fontBitter;
    public UsersMsgAdapter(Context context, List<BringUsersMsgs> info) {
        super(context, R.layout.msgusers_layout, info);
        fontPTSerif = Typeface.createFromAsset(context.getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(context.getAssets(),"fonts/Bitter.otf");
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
                loginIntent.putExtra("profileUserName", bringUsersMsgs.name);
                getContext().startActivity(loginIntent);
            }
        });

        com.github.siyamed.shapeimageview.CircularImageView userpic = (com.github.siyamed.shapeimageview.CircularImageView) convertView.findViewById(R.id.followerfromPic);
        TextView userFirst = (TextView) convertView.findViewById(R.id.followername);
        TextView userbody = (TextView) convertView.findViewById(R.id.followerlastonline);

        userbody.setTypeface(fontPTSerif);
        userFirst.setTypeface(fontBitter);

        new ImageLinkLoad(bringUsersMsgs.fromPic, userpic).execute();
        userFirst.setText(bringUsersMsgs.name);
        userbody.setText(bringUsersMsgs.body);

        return convertView;
    }
}
