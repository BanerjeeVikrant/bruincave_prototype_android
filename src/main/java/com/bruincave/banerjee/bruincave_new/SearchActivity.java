package com.bruincave.banerjee.bruincave_new;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Typeface fontBitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);

        final String username = prefs.getString("username", null);

        ImageButton backButton = (ImageButton) findViewById(R.id.backProfile);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.finish();
            }
        });

        final EditText search = (EditText) findViewById(R.id.search);
        search.setTypeface(fontBitter);

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
                            newMsgsUsers.body = usersObject.getInt("following");

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
        BringSearchUsers bringSearchUsers = new BringSearchUsers(username, str, usersListener);
        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
        queue3.add(bringSearchUsers);

    }

}
