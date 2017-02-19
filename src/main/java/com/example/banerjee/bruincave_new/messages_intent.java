package com.example.banerjee.bruincave_new;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
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

public class messages_intent extends AppCompatActivity {

    private int offset = 0;
    private int format = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_intent_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bringMessages("ssdf", "banerjeevikrant");
        ListView msgListView = (ListView) findViewById(R.id.msgListView);
        msgListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0){
                    bringMessages("ssdf", "banerjeevikrant");
                }
            }
        });
    }

    public void bringMessages(String username, String profileUser) {
        final messages_intent parent_this = this;
        Response.Listener<String> msgListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Message RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse != null) {
                        JSONArray messages = jsonResponse.getJSONArray("messages");
                        ListView msgListView = (ListView) findViewById(R.id.msgListView);
                        ArrayList<Messages> info = new ArrayList<Messages>();

                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject msg = messages.getJSONObject(i);
                            Messages newMessage =  new Messages();
                            newMessage.userpic = msg.getString("userpic");
                            newMessage.body = msg.getString("message");
                            newMessage.format = msg.getString("side");


                            info.add(newMessage);
                        }
                        MessagesAdapter messagesAdapter;
                        if (offset == 0) {
                            messagesAdapter = new MessagesAdapter(parent_this, info);
                            msgListView.setAdapter(messagesAdapter);
                            scrollMyListViewTo(messagesAdapter.getCount()-1);
                        } else {
                            messagesAdapter = (MessagesAdapter) msgListView.getAdapter();
                            messagesAdapter.addAll(info);
                        }
                        offset = offset + 15;
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
        GetMessages getMessages = new GetMessages(offset, username, profileUser, msgListener);
        RequestQueue queue = Volley.newRequestQueue(messages_intent.this);
        queue.add(getMessages);
    }

    private void scrollMyListViewTo(int loc) {
        final ListView msgListView = (ListView)findViewById(R.id.msgListView);
        msgListView.setSelection(loc);
        Log.d("XXX","scroll to position:"+ loc);
    }
}
