package com.bruincave.banerjee.bruincave_new;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant Banerjee on 2/8/2017.
 */
public class MessagesTab extends Fragment{
    private int offset_msgusers=0;
    public MessagesTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        offset_msgusers=0;
        return inflater.inflate(R.layout.messages_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getView() != null) {
            bringUsersMsg(getArguments().getString("username"), "");
        }

        final EditText searchFollowersEdit = (EditText) getView().findViewById(R.id.searchUsers);

        searchFollowersEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String str = searchFollowersEdit.getText().toString();

                bringUsersMsg(getArguments().getString("username"), str);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }
    public void bringUsersMsg(String username, String str){
        //final home_layout parent_this = this;
        final View currView = getView();
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
                        ListView usersMsgListView = (ListView) currView.findViewById(R.id.usersMsgListView);
                        if (usersMsgListView == null) { Log.d("Info1:", "usersMsgListView is null"); }
                        ArrayList<BringUsersMsgs> info = new ArrayList<BringUsersMsgs>();

                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject usersObject = usersArray.getJSONObject(i);
                            Log.d("users-id:", "" + usersObject.getInt("id"));

                            BringUsersMsgs newMsgsUsers =  new BringUsersMsgs();
                            newMsgsUsers.id = usersObject.getInt("id");
                            newMsgsUsers.fromPic = usersObject.getString("fromPic");
                            newMsgsUsers.name = usersObject.getString("name");
                            newMsgsUsers.body = usersObject.getString("body");

                            info.add(newMsgsUsers);


                        }
                        if (usersMsgListView == null) { Log.d("Info2:", "usersMsgListView is null"); }
                        if (offset_msgusers == 0) {
                            UsersMsgAdapter usersMsgAdapter = new UsersMsgAdapter(getContext(), info);
                            if (usersMsgListView == null) { Log.d("Info3:", "usersMsgListView is null"); }
                            usersMsgListView.setAdapter(usersMsgAdapter);

                        } else {
                            UsersMsgAdapter usersMsgAdapter  =  (UsersMsgAdapter)usersMsgListView.getAdapter();
                            usersMsgAdapter.addAll(info);
                        }

                        usersMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Start an alpha animation for clicked item
                                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                                animation1.setDuration(4000);
                                view.startAnimation(animation1);

                                Log.d("This doesnt work", "try again");
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        BringUsersMsg bringUsersMsg = new BringUsersMsg(offset_msgusers, username, str, usersListener);
        RequestQueue queue3 = Volley.newRequestQueue(getContext());
        queue3.add(bringUsersMsg);

    }

}
