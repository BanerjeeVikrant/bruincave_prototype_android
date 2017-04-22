package com.bruincave.banerjee.bruincave_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONException;
import org.json.JSONObject;

public class register_layout3 extends AppCompatActivity{

    private Typeface fontPTSerif, fontBitter;

    private String firstnameText, lastnameText, dobText, classSpinText, genderSpinText, usernameText, passwordText, passwordRepeatText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layoutthree);

        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                firstnameText = "";
                lastnameText = "";
                dobText = "";
                classSpinText = "";
                genderSpinText = "";
                usernameText = "";
                passwordText = "";
                passwordRepeatText = "";
            } else {
                lastnameText = extras.getString("lastname");
                firstnameText = extras.getString("firstname");
                dobText = extras.getString("dob");
                classSpinText = extras.getString("classSpin");
                genderSpinText = extras.getString("genderSpin");
                usernameText = extras.getString("username");
                passwordText = extras.getString("password");
                passwordRepeatText = extras.getString("passwordRepeat");
            }
        } else {
            //profileuserid= (Integer) savedInstanceState.getSerializable("profileUserId");
        }
        TextView textLabel = (TextView) findViewById(R.id.textLabel1);
        textLabel.setTypeface(fontBitter);

        final EditText usernameET = (EditText) findViewById(R.id.usernameRg);
        final EditText passwordET = (EditText) findViewById(R.id.passwordRg);
        final EditText passwordRepeatET = (EditText) findViewById(R.id.passowrdRepeatRg);

        usernameET.setTypeface(fontPTSerif);
        passwordET.setTypeface(fontPTSerif);
        passwordRepeatET.setTypeface(fontPTSerif);

        usernameET.setText(usernameText);
        passwordET.setText(passwordText);
        passwordRepeatET.setText(passwordRepeatText);

        final ImageButton back_three = (ImageButton) findViewById(R.id.back_three);
        final Button next_three = (Button) findViewById(R.id.nextthree);

        next_three.setTypeface(fontBitter);


        next_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameText = usernameET.getText().toString().trim();
                passwordText = passwordET.getText().toString().trim();
                passwordRepeatText = passwordRepeatET.getText().toString().trim();

                int grade = 0;
                if(classSpinText.equals("Freshmen")){
                    grade = 9;
                } else if(classSpinText.equals("Sophomore")){
                    grade = 10;
                } else if(classSpinText.equals("Junior")){
                    grade = 11;
                } else if(classSpinText.equals("Senior")){
                    grade = 12;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSP:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {

                                Intent loginIntent = new Intent(register_layout3.this, login_layout.class);
                                loginIntent.putExtra("usernamelogin", usernameText);
                                loginIntent.putExtra("passwordlogin", passwordText);
                                startActivity(loginIntent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(register_layout3.this);
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

                RegisterRequest registerRequest = new RegisterRequest(firstnameText, lastnameText, usernameText, passwordText, dobText, grade, responseListener);
                RequestQueue queue = Volley.newRequestQueue(register_layout3.this);
                queue.add(registerRequest);
            }
        });

        back_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameText = usernameET.getText().toString().trim();
                passwordText = passwordET.getText().toString().trim();
                passwordRepeatText = passwordRepeatET.getText().toString().trim();

                Intent registerIntent = new Intent(register_layout3.this, register_layout2.class);
                registerIntent.putExtra("firstname", firstnameText);
                registerIntent.putExtra("lastname", lastnameText);
                registerIntent.putExtra("dob", dobText);
                registerIntent.putExtra("classSpin", classSpinText);
                registerIntent.putExtra("genderSpin", genderSpinText);
                registerIntent.putExtra("username", usernameText);
                registerIntent.putExtra("password", passwordText);
                registerIntent.putExtra("passwordRepeat", passwordRepeatText);
                register_layout3.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });


    }
}