package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);

        final String username = prefs.getString("username", null);

        ImageButton backButton = (ImageButton) findViewById(R.id.backProfile);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SearchActivity.this, home_layout.class);
                SearchActivity.this.startActivity(homeIntent);
            }
        });

        final EditText search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String str = search.getText().toString();

                bringUsers(username, str);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void bringUsers(String username, String str) {

        Response.Listener<String> usersListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String users) {
                try {
                    JSONObject usersResponse = new JSONObject(users);
                    Log.d("users:", users);
                    if (usersResponse != null) {
                        JSONArray usersArray = usersResponse.getJSONArray("usersMsg");
                        //LinearLayout usersLayout = (LinearLayout) findViewById(R.id.userswrapper);
                        ListView usersMsgListView = (ListView) findViewById(R.id.searchresults);
                        if (usersMsgListView == null) { Log.d("Info1:", "usersMsgListView is null"); }
                        ArrayList<BringUser> info = new ArrayList<BringUser>();

                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject usersObject = usersArray.getJSONObject(i);
                            Log.d("users-id:", "" + usersObject.getInt("id"));

                            BringUser newMsgsUsers =  new BringUser();
                            newMsgsUsers.id = usersObject.getInt("id");
                            newMsgsUsers.fromPic = usersObject.getString("fromPic");
                            newMsgsUsers.name = usersObject.getString("name");
                            newMsgsUsers.body = usersObject.getString("body");

                            info.add(newMsgsUsers);


                        }

                        UsersAdapter usersAdapter = new UsersAdapter(getApplicationContext(), info);
                        if (usersMsgListView == null) { Log.d("Info3:", "usersMsgListView is null"); }
                        usersMsgListView.setAdapter(usersAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        BringUsers bringUsers = new BringUsers(username, str, usersListener);
        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
        queue3.add(bringUsers);

    }

}
