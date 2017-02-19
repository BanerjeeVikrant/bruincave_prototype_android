package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikrant Banerjee on 2/6/2017.
 */
public class MessagesAdapter extends ArrayAdapter<Messages>{
    float fPixel = getContext().getResources().getDisplayMetrics().density;
    public MessagesAdapter(Context context, List<Messages> info) {
        super(context, R.layout.your_messages_layout, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater messagesInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = messagesInflater.inflate(R.layout.your_messages_layout, parent, false);
        }

        Messages messages = getItem(position);

        LinearLayout messageBox = (LinearLayout) convertView.findViewById(R.id.messagebox);
        TextView body = (TextView) convertView.findViewById(R.id.messagebody);
        if ((messageBox.getChildCount() > 0) && (messageBox.getChildAt(0) instanceof ImageView)) {
            messageBox.removeViewAt(0);
        }
        if (messageBox.getLayoutParams() == null) {
            messageBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        //new ImageLinkLoad(messages.userpic, userpic).execute();
        body.setText(messages.body);
        if(new String(messages.format).equals("1")){
            ImageView imageView = new ImageView(getContext());
            //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            LinearLayout.LayoutParams layout_204 = new LinearLayout.LayoutParams((int)fPixel * 60,(int)fPixel * 60);
            imageView.setLayoutParams(layout_204);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (body.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) body.getLayoutParams();

                p.setMargins((int)fPixel*20, 0, (int)fPixel*40, (int)fPixel*10);
                body.requestLayout();
            }
            messageBox.addView(imageView, 0);
            new ImageLinkLoad(messages.userpic, imageView).execute();


            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)messageBox.getLayoutParams();
            params.gravity = Gravity.LEFT;
            //messageBox.setLayoutParams(params);
        }
        else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)messageBox.getLayoutParams();
            params.gravity = Gravity.RIGHT;
            if (body.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) body.getLayoutParams();
                p.setMargins((int)fPixel*40, 0, (int)fPixel*15, (int)fPixel*10);
                body.requestLayout();
            }
            Log.d("this", messages.format);
        }


        return convertView;
    }

}