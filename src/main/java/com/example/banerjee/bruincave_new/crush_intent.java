package com.example.banerjee.bruincave_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class crush_intent extends AppCompatActivity {

    private String username;
    private int crushid;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        contentView = (LinearLayout) inflater.inflate(R.layout.content_crush_intent, null);
        setContentView(contentView);
        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
        username = prefs.getString("username", null);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                crushid= 0;
            } else {
                crushid= extras.getInt("crushId");
                Log.d("Now", crushid + "");
            }
        } else {
            crushid= (Integer) savedInstanceState.getSerializable("profileUserId");
        }

        bringCrush(crushid);

    }

    public void bringCrush(final int postid) {
        Response.Listener<String> crushListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String crush) {
                try {
                    Log.d("RSP:", crush);
                    JSONObject crushResponse = new JSONObject(crush);
                    if (crushResponse != null) {
                        JSONArray crushArray = crushResponse.getJSONArray("crush");
                        JSONObject crushObject = crushArray.getJSONObject(0);

                        String body = crushObject.getString("body");
                        String time_added = crushObject.getString("time_added");

                        TextView timestamp = (TextView) contentView.findViewById(R.id.timestamp);
                        TextView bodyET = (TextView) contentView.findViewById(R.id.txtStatusMsg);

                        final EditText commentET = (EditText) contentView.findViewById(R.id.commentEditText);
                        //commentET.setTypeface(fontPTSerif);

                        commentET.setOnKeyListener(new View.OnKeyListener() {
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                // If the event is a key-down event on the "enter" button
                                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                    // Perform action on key press
                                    String commentString = commentET.getText().toString().trim();
                                    Response.Listener<String> comListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("Message RSP:", response);
                                                JSONObject jsonResponse = new JSONObject(response);

                                                if (jsonResponse != null) {

                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(crush_intent.this);
                                                    builder.setMessage("Loading Failed")
                                                            .setNegativeButton("Retry", null)
                                                            .create()
                                                            .show();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    SendCrushComment sendCrushComment = new SendCrushComment(username, postid, commentString, comListener);
                                    RequestQueue queue = Volley.newRequestQueue(crush_intent.this);
                                    queue.add(sendCrushComment);

                                    commentET.setText("");
                                    return true;
                                }
                                return false;
                            }
                        });

                        JSONArray comments = crushObject.getJSONArray("comments");
                        LinearLayout postNcomment = (LinearLayout)contentView.findViewById(R.id.postNcomment);
                        JSONObject comment;
                        String bodyInfo;
                        TextView bodyView;

                        for (int cn = 0; cn < comments.length(); cn++) {
                            LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                            View commentView = inflater.inflate(R.layout.crush_comment_layout, postNcomment);
                        }

                        for (int cn=0; cn<comments.length(); cn++) {
                            comment = comments.getJSONObject(cn);
                            bodyInfo = comment.getString("body");
                            LinearLayout commentView = (LinearLayout)postNcomment.getChildAt(cn+1);
                            bodyView = (TextView)commentView.findViewById(R.id.crushcommentbody);
                            bodyView.setText(bodyInfo);
                            Log.d("text", bodyView.getText().toString());
                            Log.d("CommentX",  bodyInfo);
                        }
                        timestamp.setText(time_added);
                        bodyET.setText(body);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetCrushBasedId getCrushBasedId = new GetCrushBasedId(postid, username, crushListener);
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(getCrushBasedId);
    }
}
