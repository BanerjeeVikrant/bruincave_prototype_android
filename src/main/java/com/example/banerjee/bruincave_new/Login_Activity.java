package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        // Variables
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        final String MY_PREFS_NAME = "userinfo";

        //login
        final EditText user_login = (EditText) findViewById(R.id.etUser);
        final EditText user_pass = (EditText) findViewById(R.id.etPass);
        final Button login_btn = (Button) findViewById(R.id.bLogin);

        //register
        final EditText reg_fn = (EditText) findViewById(R.id.etfn);
        final EditText reg_ln = (EditText) findViewById(R.id.etln);
        final EditText reg_login = (EditText) findViewById(R.id.etUsername);
        final EditText reg_pass = (EditText) findViewById(R.id.etPassword);
        final EditText reg_pass_again = (EditText) findViewById(R.id.etPasswordAgain);
        final EditText reg_stuid = (EditText) findViewById(R.id.etStudentId);
        final EditText reg_dob = (EditText) findViewById(R.id.etdob);
        final EditText reg_email = (EditText) findViewById(R.id.etEmail);
        final EditText reg_phone = (EditText) findViewById(R.id.etPhone);
        final Button register_btn = (Button) findViewById(R.id.bRegister);

        // Define Tabhost
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("login");
        tabSpec.setContent(R.id.loginOption);
        tabSpec.setIndicator("Login");
        tabHost.addTab(tabSpec);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("signup");
        tabSpec2.setContent(R.id.registerOption);
        tabSpec2.setIndicator("SignUp");
        tabHost.addTab(tabSpec2);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fn = reg_fn.getText().toString();
                final String ln = reg_ln.getText().toString();
                final String username = reg_login.getText().toString();
                final String dob = reg_dob.getText().toString();
                final String password = reg_pass.getText().toString();
                final int stuid = Integer.parseInt(reg_stuid.getText().toString());
                final String email = reg_email.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSP:",response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
                                builder.setMessage("Registering Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(fn, ln, username, email, password, dob, 9, stuid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
                queue.add(registerRequest);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = user_login.getText().toString();
                final String password = user_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSP:",response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if(success){

                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("username", username);
                                editor.apply();

                                Intent loginIntent = new Intent(Login_Activity.this, home_layout.class);
                                Login_Activity.this.startActivity(loginIntent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
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
                RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
                queue.add(loginRequest);
            }
        });

    }
}
