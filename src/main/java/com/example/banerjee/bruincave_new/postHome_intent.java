package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vikrant Banerjee on 2/22/2017.
 */
public class postHome_intent extends AppCompatActivity {

    final String MY_PREFS_NAME = "userinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posthome_layout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String username = prefs.getString("username", null);

        ImageButton back_home = (ImageButton) findViewById(R.id.backHomePost);

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHome = new Intent(postHome_intent.this, home_layout.class);
                postHome_intent.this.startActivity(openHome);
            }
        });

        Button send_btn = (Button) findViewById(R.id.btnPostHome);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText message = (EditText) findViewById(R.id.homeEditText);

                String msgTxt = message.getText().toString().trim();

                Response.Listener<String> sendDataListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String users) {
                        try {
                            JSONObject usersResponse = new JSONObject(users);
                            if (usersResponse != null) {
                                Intent openHome = new Intent(postHome_intent.this, home_layout.class);
                                postHome_intent.this.startActivity(openHome);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SendPostText sendPostText = new SendPostText(username, "", msgTxt, 0, sendDataListener);
                RequestQueue queue3 = Volley.newRequestQueue(getApplication());
                queue3.add(sendPostText);
            }
        });


    }
}
