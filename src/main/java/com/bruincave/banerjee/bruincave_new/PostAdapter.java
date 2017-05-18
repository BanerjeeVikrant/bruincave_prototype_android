package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vikrant Banerjee on 1/21/2017.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public Typeface fontPTSerif, fontBitter;

    class ViewHolderNoImg extends RecyclerView.ViewHolder {


        public TextView nameView;
        public TextView timestampView;
        public TextView likeBody;
        public TextView likeBodyTextView;
        public TextView txtStatusMsgView;
        public com.github.siyamed.shapeimageview.CircularImageView profileImageView;
        public ImageButton likeButton;
        public EditText commentET;
        public LinearLayout commentsView;

        public ViewHolderNoImg(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            nameView.setTypeface(fontBitter);
            timestampView = (TextView) view.findViewById(R.id.timestamp);
            timestampView.setTypeface(fontPTSerif);
            likeBodyTextView = (TextView) view.findViewById(R.id.likeBodyText);
            likeBodyTextView.setTypeface(fontPTSerif);
            txtStatusMsgView = (TextView) view.findViewById(R.id.txtStatusMsg);
            txtStatusMsgView.setTypeface(fontPTSerif);
            profileImageView = (com.github.siyamed.shapeimageview.CircularImageView) view.findViewById(R.id.profilepic);
            likeButton =  (ImageButton) view.findViewById(R.id.likeButton);
            commentET = (EditText) view.findViewById(R.id.commentEditText);
            commentET.setTypeface(fontPTSerif);
            commentsView = (LinearLayout) view.findViewById(R.id.comments_section);
        }
    }

    class ViewHolderImg extends ViewHolderNoImg {
        public ImageView pictureAddedView;
        public ViewHolderImg(View view) {
            super(view);
            pictureAddedView =  (ImageView)view.findViewById(R.id.picture_added);
        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder {
        public View footerView;
        public ViewHolderFooter(View view) {
            super(view);
            footerView =  view;
        }
    }

    private ArrayList<Post> postList;

    @Override
    public int getItemCount() {
        if (postList == null) {
            return 0;
        } else {
            Log.d("ADPTR", "getItemCount(): " + postList.size());
            return postList.size();
        }
    }
    private Context mContext;

    public PostAdapter(Context contexr, ArrayList<Post> postList) {
        this.postList = postList;
        this.mContext = contexr;
        fontPTSerif = Typeface.createFromAsset(contexr.getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(contexr.getAssets(),"fonts/Bitter.otf");
        //Log.d("ADPTR", "Constructor with posts: "+postList.size());
    }

    public void addAll(List<Post> morePosts) {
        if (postList == null) {
            postList = new ArrayList<Post>();
        }
        postList.addAll(morePosts);
        Log.d("ADPTR", "add more posts: "+postList.size());
    }

    @Override
    public int getItemViewType(int pos) {

        if (postList == null) { return 2; }
        if (!(postList.get(pos).picture_added).equals("http://www.bruincave.com/m/")) {
            Log.d("ADPTR", "getItemViewType at:"+pos+": with image");
            return 1;  // use view with image
        } else {
            Log.d("ADPTR", "getItemViewType at:"+pos+": without image");
            return 0;  // use view without image
        }
    }

    LayoutInflater inflator = null;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ADPTR", "createHolder for type:"+viewType);
        if (inflator == null) {
            inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        RecyclerView.ViewHolder vh =  null;
        switch (viewType) {
            case 0: {
                View view = inflator.inflate(R.layout.post_layout_noimg, parent, false);
                vh =  new ViewHolderNoImg(view);
                break;
            }
            case 1: {
                View view = inflator.inflate(R.layout.post_layout, parent, false);
                vh =  new ViewHolderImg(view);
                break;
            }
            case 2: {
                View view = inflator.inflate(R.layout.footer_layout, parent, false);
                vh =  new ViewHolderFooter(view);
                break;
            }
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        final int currPos = pos;
        Log.d("ADPTR", "Binding with pos:"+currPos+" getItemCount():"+(getItemCount()-1));
        final Post post = postList.get(currPos);

        if (viewHolder.getItemViewType() == 2) { //Footer
            Log.d("ADPT", "Footer node  found");
            return;
        }
        ViewHolderNoImg  vh = (ViewHolderNoImg)viewHolder;
        final TextView likeBodyTextView = vh.likeBodyTextView;
        final com.github.siyamed.shapeimageview.CircularImageView profileImageView = vh.profileImageView;
        final ImageButton likeButton = vh.likeButton;

        if (vh.getItemViewType() == 1){
            ((ViewHolderImg)vh).pictureAddedView.setImageResource(R.drawable.defaultpic);
        }

        vh.commentET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(mContext, post_intent.class);
                loginIntent.putExtra("postId", post.id);
                mContext.startActivity(loginIntent);
            }
        });

        if (post.likedByMe == 1) {
            likeButton.setImageResource(R.drawable.likedpaw);
        } else  {
            likeButton.setImageResource(R.drawable.notlikedpaw);
        }

        if(post.moreThanThreeLiker == 1){
            likeBodyTextView.setText(post.likesCount+" likes");
        } else if (post.moreThanThreeLiker == 2) {
            likeBodyTextView.setText("Be the first to Like");
        } else {
            //Log.d("Likedby", post.likedby);
            likeBodyTextView.setText("Liked by " + post.likedby);
        }

        //final int pos = post.id;
        final String username = post.username;

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> likePostActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSPX:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                if(post.likedByMe == 1){
                                    post.likedByMe = 0;
                                }else{
                                    post.likedByMe = 1;
                                }
                                if (post.likedByMe == 1) {
                                    likeButton.setImageResource(R.drawable.likedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Liked by " + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        int ans = post.likesCount + 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                } else  {
                                    likeButton.setImageResource(R.drawable.notlikedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Be the first to Like");
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        int ans = post.likesCount - 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                }
                            } else {
                                Log.d("RSPX", "failed");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LikePostAction likePostAction = new LikePostAction(currPos, username, likePostActionListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(likePostAction);
            }
        });
        vh.commentsView.removeAllViews();

        if (post.comments != null) {
            for (int i = 0; i < post.comments.length; i++) {
                View comment = inflator.inflate(R.layout.comment_layout, vh.commentsView, false);
                String fromBody = post.comments[i].from;
                String postBody = post.comments[i].body;
                TextView bodyView = (TextView) comment.findViewById(R.id.followerlastonline);
                bodyView.setTypeface(fontPTSerif);

                SpannableStringBuilder sb = new SpannableStringBuilder(fromBody + " " + postBody);
                StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);

                sb.setSpan(b, 0, fromBody.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                bodyView.setText(sb);
                vh.commentsView.addView(comment);
            }
        }
        vh.nameView.setText(post.name);
        vh.timestampView.setText(post.time_added);
        vh.txtStatusMsgView.setText(post.body);

        new ImageLinkLoad(post.userpic, vh.profileImageView).execute();
        if (vh.getItemViewType() == 1) {
            new ImageLinkLoad(postList.get(currPos).picture_added, ((ViewHolderImg)vh).pictureAddedView).execute();
        }
    }
}
/*
public class PostAdapter extends ArrayAdapter<Post> {
    private Typeface fontPTSerif;
    private Typeface fontBitter;
    public PostAdapter(Context context, List<Post> info) {
        super(context, R.layout.post_layout, info);
        fontPTSerif = Typeface.createFromAsset(context.getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(context.getAssets(), "fonts/Bitter.otf");
    }
    private boolean forProfile =  false;
    public void setForProfile() { forProfile = true; }
    private int lastPosition = 0;
    class PostViewHolder {
        public TextView nameView;
        public TextView timestampView;
        public TextView likeBody;
        public TextView likeBodyTextView;
        public TextView txtStatusMsgView;
        public com.github.siyamed.shapeimageview.CircularImageView profileImageView;
        public ImageView pictureAddedView;
        public ImageButton likeButton;
        public EditText commentET;
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater postInflater = LayoutInflater.from(getContext());

        PostViewHolder pvh;

        if (convertView == null) {
            convertView = postInflater.inflate(R.layout.post_layout, parent, false);
            pvh = new PostViewHolder();
            pvh.nameView = (TextView) convertView.findViewById(R.id.name);
            pvh.nameView.setTypeface(fontPTSerif);
            pvh.timestampView = (TextView) convertView.findViewById(R.id.timestamp);
            pvh.timestampView.setTypeface(fontPTSerif);
            pvh.likeBodyTextView = (TextView) convertView.findViewById(R.id.likeBodyText);
            pvh.likeBodyTextView.setTypeface(fontPTSerif);
            pvh.txtStatusMsgView = (TextView) convertView.findViewById(R.id.txtStatusMsg);
            pvh.txtStatusMsgView.setTypeface(fontPTSerif);
            pvh.profileImageView = (com.github.siyamed.shapeimageview.CircularImageView) convertView.findViewById(R.id.profilepic);
            pvh.pictureAddedView = (ImageView) convertView.findViewById(R.id.picture_added);
            pvh.likeButton =  (ImageButton) convertView.findViewById(R.id.likeButton);
            pvh.commentET = (EditText) convertView.findViewById(R.id.commentEditText);
            pvh.commentET.setTypeface(fontPTSerif);
            convertView.setTag(pvh);
        } //else if (forProfile == true) {
        //    if (convertView.findViewById(R.id.timestamp) == null) { // must be top profile view, can't be reused.
         //       convertView = postInflater.inflate(R.layout.post_layout, parent, false);
         //   }
        //}
        else {
            pvh =  (PostViewHolder) convertView.getTag();
        }
        final TextView likeBodyTextView = pvh.likeBodyTextView;
        final com.github.siyamed.shapeimageview.CircularImageView profileImageView = pvh.profileImageView;
        final ImageButton likeButton = pvh.likeButton;

        final Post post = getItem(position);

        pvh.commentET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getContext(), post_intent.class);
                loginIntent.putExtra("postId", post.id);
                getContext().startActivity(loginIntent);
            }
        });

        if (post.likedByMe == 1) {
            likeButton.setImageResource(R.drawable.likedpaw);
        } else  {
            likeButton.setImageResource(R.drawable.notlikedpaw);
        }

        if(post.moreThanThreeLiker == 1){
            likeBodyTextView.setText(post.likesCount+" likes");
        } else if (post.moreThanThreeLiker == 2) {
            likeBodyTextView.setText("Be the first to Like");
        } else {
            //Log.d("Likedby", post.likedby);
            likeBodyTextView.setText("Liked by " + post.likedby);
        }

        final int pos = post.id;
        final String username = post.username;

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YYY","Clicked for post #"+pos);

                //Connection with the database
                Log.d("LikePost", pos+","+username);

                Response.Listener<String> likePostActionListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSPX:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                if(post.likedByMe == 1){
                                    post.likedByMe = 0;
                                }else{
                                    post.likedByMe = 1;
                                }
                                if (post.likedByMe == 1) {
                                    likeButton.setImageResource(R.drawable.likedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Liked by " + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        int ans = post.likesCount + 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                } else  {
                                    likeButton.setImageResource(R.drawable.notlikedpaw);

                                    if(post.moreThanThreeLiker == 2){
                                        likeBodyTextView.setText("Be the first to Like");
                                    }
                                    else if(post.moreThanThreeLiker == 0){
                                        likeBodyTextView.setText("Liked by " + post.likedby + "," + post.username);
                                    }
                                    else if(post.moreThanThreeLiker == 1){
                                        int ans = post.likesCount - 1;
                                        likeBodyTextView.setText(ans+" likes");
                                    }
                                }
                            } else {
                                Log.d("RSPX", "failed");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LikePostAction likePostAction = new LikePostAction(pos, username, likePostActionListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(likePostAction);
            }
        });

        //pictureAddedView.setImageResource(0);
        LinearLayout commentsView = (LinearLayout) convertView.findViewById(R.id.comments_section);
        commentsView.removeAllViews();

        commentsView.removeAllViews();
        if(post.comments == null) {
            Log.d("Comment","length-0");
        } else {
            Log.d("Comment", "length-" + post.comments.length);
        }

        if (post.comments != null) {
            for (int i = 0; i < post.comments.length; i++) {
                View comment = postInflater.inflate(R.layout.comment_layout, commentsView, false);
                String fromBody = post.comments[i].from;
                String postBody = post.comments[i].body;
                TextView bodyView = (TextView) comment.findViewById(R.id.followerlastonline);
                bodyView.setTypeface(fontPTSerif);

                SpannableStringBuilder sb = new SpannableStringBuilder(fromBody + " " + postBody);
                StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);

                sb.setSpan(b, 0, fromBody.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                bodyView.setText(sb);
                commentsView.addView(comment);
            }
        }
        pvh.nameView.setText(post.name);
        pvh.timestampView.setText(post.time_added);
        pvh.txtStatusMsgView.setText(post.body);

        new ImageLinkLoad(post.userpic, profileImageView).execute();
        new ImageLinkLoad(post.picture_added, pvh.pictureAddedView,(ListView)parent, (position < lastPosition)).execute();

        lastPosition = position;
        return convertView;
    }
}
*/