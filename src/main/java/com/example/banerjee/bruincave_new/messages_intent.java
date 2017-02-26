package com.example.banerjee.bruincave_new;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class messages_intent extends AppCompatActivity {

    private int offset = 0;
    private int format = 0;
    private int profileuserid;
    final String MY_PREFS_NAME = "userinfo";
    private String username;

    Timer t;

    @Override protected void onStart() {
        super.onStart();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.d("XXY", "Check and  bring message:"+offset);
                Log.d("Now", profileuserid + "");
                bringMessages(username, profileuserid);
            }
        },0,2000);
    }
    @Override protected void onStop() {
        super.onStop();
        t.cancel();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_intent_activity);
        offset = 0;
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("username", null);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profileuserid= 0;
            } else {
                profileuserid= extras.getInt("profileUserId");
                Log.d("Now", profileuserid + "");
            }
        } else {
            profileuserid= (Integer) savedInstanceState.getSerializable("profileUserId");
        }


        ImageButton send_msg = (ImageButton) findViewById(R.id.sendMsgBtn);

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText msgET = (EditText) findViewById(R.id.msgEditText);
                String msgTxt = msgET.getText().toString().trim();

                Response.Listener<String> msgListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Message RSP:", response);
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse != null) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(messages_intent.this);
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
                SendMsg sendMsg = new SendMsg(username, profileuserid, msgTxt, msgListener);
                RequestQueue queue = Volley.newRequestQueue(messages_intent.this);
                queue.add(sendMsg);

            }
        });

    }
    public void bringMessages(String username, int profileUserId) {
        final messages_intent parent_this = this;
        Response.Listener<String> msgListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Message RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray messages = jsonResponse.getJSONArray("messages");
                        ListView msgListView = (ListView) findViewById(R.id.msgListView);
                        ArrayList<Messages> info = new ArrayList<Messages>();
                        MessagesAdapter messagesAdapter;
                        Messages newMessage = null;
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject msg = messages.getJSONObject(i);
                            newMessage =  new Messages();
                            newMessage.userpic = msg.getString("userpic");
                            newMessage.body = msg.getString("message");
                            newMessage.format = msg.getString("side");
                            newMessage.id = msg.getInt("id");
                            info.add(newMessage);
                        }
                        if (newMessage != null) {
                            if (offset == 0) {
                                messagesAdapter = new MessagesAdapter(parent_this,info);
                                scrollMyListViewTo(messagesAdapter.getCount()-1);
                                msgListView.setAdapter(messagesAdapter);
                            } else {
                                messagesAdapter = (MessagesAdapter) msgListView.getAdapter();
                                messagesAdapter.addAll(info);
                            }
                            offset = newMessage.id;
                            msgListView.setSelection(messagesAdapter.getCount()-1);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(messages_intent.this);
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
        GetMessages getMessages = new GetMessages(offset, username, profileUserId, msgListener);
        RequestQueue queue = Volley.newRequestQueue(messages_intent.this);
        queue.add(getMessages);
    }

    private void scrollMyListViewTo(int loc) {
        final ListView msgListView = (ListView)findViewById(R.id.msgListView);
        msgListView.setSelection(loc);
        Log.d("XXX","scroll to position:"+ loc);
    }
}
