package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 1/23/2017.
 */
public class CrushAdapter extends ArrayAdapter<Crush>{
    private Typeface fontMuli;
    public CrushAdapter(Context context, List<Crush> info) {
        super(context, R.layout.crush_layout, info);
        fontMuli = Typeface.createFromAsset(context.getAssets(),"fonts/Muli.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater crushInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = crushInflater.inflate(R.layout.crush_layout, parent, false);
        }

        final Crush crush = getItem(position);

        TextView timestampView = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txtStatusMsgView = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView nameCrush = (TextView) convertView.findViewById(R.id.nameCrush);
        TextView commentsCount = (TextView) convertView.findViewById(R.id.numberOfComments);
        ImageButton commentSectionOpen = (ImageButton) convertView.findViewById(R.id.commentCrush);

        commentSectionOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crushIntent = new Intent(getContext(), crush_intent.class);
                crushIntent.putExtra("crushId", crush.id);
                getContext().startActivity(crushIntent);
            }
        });

        timestampView.setText(crush.time_added);
        txtStatusMsgView.setText(crush.body);
        commentsCount.setText(crush.commentscount + "");

        timestampView.setTypeface(fontMuli);
        txtStatusMsgView.setTypeface(fontMuli);
        nameCrush.setTypeface(fontMuli);
        commentsCount.setTypeface(fontMuli);

        return convertView;
    }

}
