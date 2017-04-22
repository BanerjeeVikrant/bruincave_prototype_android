package com.bruincave.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vikrant Banerjee on 2/22/2017.
 */
public class postAnon_intent extends AppCompatActivity {

    final String MY_PREFS_NAME = "userinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postanon_layout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String username = prefs.getString("username", null);

        ImageButton back_home = (ImageButton) findViewById(R.id.backAnonPost);

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAnon_intent.this.finish();
            }
        });

        ImageButton send_btn = (ImageButton) findViewById(R.id.btnPostCrush);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText message = (EditText) findViewById(R.id.crushEditText);

                String msgTxt = message.getText().toString().trim();

                Response.Listener<String> sendDataListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String users) {
                        try {
                            JSONObject usersResponse = new JSONObject(users);
                            if (usersResponse != null) {
                                Intent openHome = new Intent(postAnon_intent.this, home_layout.class);
                                postAnon_intent.this.startActivity(openHome);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SendCrush sendCrush = new SendCrush(username, msgTxt, sendDataListener);
                RequestQueue queue3 = Volley.newRequestQueue(getApplication());
                queue3.add(sendCrush);
            }
        });


    }
}