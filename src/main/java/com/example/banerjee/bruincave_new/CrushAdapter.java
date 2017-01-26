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
 * Created by Vikrant Banerjee on 1/23/2017.
 */
public class CrushAdapter extends ArrayAdapter<Crush>{
    public CrushAdapter(Context context, List<Crush> info) {
        super(context, R.layout.crush_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater crushInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = crushInflater.inflate(R.layout.crush_layout, parent, false);
        }

        Crush crush = getItem(position);

        TextView timestampView = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txtStatusMsgView = (TextView) convertView.findViewById(R.id.txtStatusMsg);

        timestampView.setText(crush.time_added);
        txtStatusMsgView.setText(crush.body);

        return convertView;
    }
}
