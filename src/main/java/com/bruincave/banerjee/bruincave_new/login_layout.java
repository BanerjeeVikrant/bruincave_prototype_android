package com.bruincave.banerjee.bruincave_new;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONException;
import org.json.JSONObject;

public class login_layout extends AppCompatActivity {

    private Typeface fontPTSerif, fontBitter;

    private String usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar);
        setContentView(R.layout.activity_login_layout);

        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        final String MY_PREFS_NAME = "userinfo";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                usernameText = "";
                passwordText = "";
            } else {
                usernameText = extras.getString("usernamelogin");
                passwordText = extras.getString("passwordlogin");
            }
        } else {
            //profileuserid= (Integer) savedInstanceState.getSerializable("profileUserId");
        }

        String username = prefs.getString("username", null);
        if (username != null) {
            Intent homeIntent = new Intent(login_layout.this, home_layout.class);
            login_layout.this.startActivity(homeIntent);
            finish();
        } else {

            //login
            final EditText user_login = (EditText) findViewById(R.id.lgUsername);
            final EditText user_pass = (EditText) findViewById(R.id.lgPassword);
            final TextView tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
            final TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

            user_login.setText(usernameText);
            user_pass.setText(passwordText);

            user_login.setTypeface(fontPTSerif);
            user_pass.setTypeface(fontPTSerif);
            tvForgotPass.setTypeface(fontBitter);
            tvTitle.setTypeface(fontBitter);
            final Button login_btn = (Button) findViewById(R.id.loginBtn);

            final Button register_intentBtn  = (Button) findViewById(R.id.registerStartBtn);

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = user_login.getText().toString();
                    final String password = user_pass.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("RSP:", response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");


                                if (success) {

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("username", username);
                                    editor.apply();

                                    Intent loginIntent = new Intent(login_layout.this, home_layout.class);
                                    startActivity(loginIntent);

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(login_layout.this);
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

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(login_layout.this);
                    queue.add(loginRequest);
                }
            });

            register_intentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(login_layout.this, register_layout.class);
                    startActivity(registerIntent);
                }
            });


        }

    }
}
