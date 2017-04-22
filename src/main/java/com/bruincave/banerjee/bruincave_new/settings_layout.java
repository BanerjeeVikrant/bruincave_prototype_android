package com.bruincave.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class settings_layout extends AppCompatActivity {

    private Typeface fontPTSerif;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");

        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);

        username = prefs.getString("username", null);

        final EditText usernameET = (EditText)findViewById(R.id.usernameET);
        final EditText passwordET = (EditText)findViewById(R.id.passwordET);
        final EditText passwordAgainET = (EditText)findViewById(R.id.passwordAgainET);

        usernameET.setTypeface(fontPTSerif);
        passwordET.setTypeface(fontPTSerif);
        passwordAgainET.setTypeface(fontPTSerif);

        usernameET.setText(username);

        Button changeBtn = (Button)findViewById(R.id.btnPostHome);
        ImageButton backProfile = (ImageButton)findViewById(R.id.backProfile);

        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_now = usernameET.getText().toString().toLowerCase().trim();
                String password_now = passwordET.getText().toString().trim();
                String passwordAgain_now = passwordAgainET.getText().toString().trim();

                if(password_now.equals("") && username_now.equals(username)){
                    Toast.makeText(getApplicationContext(),
                            "Nothing Changed.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if(password_now.equals("") && username_now.equals(username)){
                    onlyChangeUsername(username_now);
                }else if(!password_now.equals(passwordAgain_now)){
                    passwordAgainET.setError("Password does not Match!");
                }else {
                    changeUsernameAndPassword(username_now, password_now);
                }
            }
        });

    }

    public void onlyChangeUsername(final String usernameNow){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {

                        Toast.makeText(getApplicationContext(),
                                "Successfully Changed.", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
                        editor.putString("username", null);
                        editor.apply();

                        SharedPreferences.Editor editorx = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
                        editorx.putString("username", usernameNow);
                        editorx.apply();


                        Intent loginIntent = new Intent(settings_layout.this, home_layout.class);
                        startActivity(loginIntent);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(settings_layout.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        changeUsername ChangeUsername = new changeUsername(username, usernameNow, responseListener);
        RequestQueue queue = Volley.newRequestQueue(settings_layout.this);
        queue.add(ChangeUsername);
    }
    public void changeUsername(String usernameNow){
        final String usernameThis = usernameNow;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {

                        SharedPreferences.Editor editor = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
                        editor.putString("username", null);
                        editor.apply();

                        SharedPreferences.Editor editorx = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
                        editorx.putString("username", usernameThis);
                        editorx.apply();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(settings_layout.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        changeUsername ChangeUsername = new changeUsername(username, usernameNow, responseListener);
        RequestQueue queue = Volley.newRequestQueue(settings_layout.this);
        queue.add(ChangeUsername);
    }
    public void changePassword(String passwordNow){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {

                        Toast.makeText(getApplicationContext(),
                                "Successfully Changed.", Toast.LENGTH_SHORT).show();

                        Intent loginIntent = new Intent(settings_layout.this, home_layout.class);
                        startActivity(loginIntent);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(settings_layout.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        changePassword ChangePassword = new changePassword(username, passwordNow, responseListener);
        RequestQueue queue = Volley.newRequestQueue(settings_layout.this);
        queue.add(ChangePassword);
    }

    public void changeUsernameAndPassword(String usernameNow, String passwordNow){
        changeUsername(usernameNow);
        changePassword(passwordNow);
    }

}
