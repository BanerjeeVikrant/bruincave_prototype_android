package com.bruincave.banerjee.bruincave_new;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

public class register_layout extends AppCompatActivity {

    private Typeface fontPTSerif, fontBitter;

    private String firstnameText, lastnameText, dobText, classSpinText, genderSpinText, usernameText, passwordText, passwordRepeatText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layoutone);

        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        TextView textLabel = (TextView) findViewById(R.id.textLabel1);
        textLabel.setTypeface(fontBitter);

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

        final EditText firstname = (EditText) findViewById(R.id.firstnameRg);
        final EditText lastname = (EditText) findViewById(R.id.lastnameRg);
        final Button next_one = (Button) findViewById(R.id.nextone);

        firstname.setText(firstnameText);
        lastname.setText(lastnameText);

        firstname.setTypeface(fontPTSerif);
        lastname.setTypeface(fontPTSerif);
        next_one.setTypeface(fontBitter);

        final ImageButton back_one = (ImageButton) findViewById(R.id.back_one);

        next_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname_str = firstname.getText().toString().trim();
                final String lastname_str = lastname.getText().toString().trim();

                if(firstname_str.trim().equals("") || lastname_str.trim().equals("")) {
                    if (firstname_str.trim().equals("")) {
                        firstname.setError("First name is required!");
                    }

                    if (lastname_str.trim().equals("")) {
                        lastname.setError("Last name is required!");
                    }
                }else {
                    Intent register2Intent = new Intent(register_layout.this, register_layout2.class);
                    register2Intent.putExtra("firstname", firstname_str);
                    register2Intent.putExtra("lastname", lastname_str);
                    register2Intent.putExtra("dob", dobText);
                    register2Intent.putExtra("classSpin", classSpinText);
                    register2Intent.putExtra("genderSpin", genderSpinText);
                    register2Intent.putExtra("username", usernameText);
                    register2Intent.putExtra("password", passwordText);
                    register2Intent.putExtra("passwordRepeat", passwordRepeatText);
                    register_layout.this.startActivity(register2Intent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }

            }
        });
        back_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(register_layout.this, login_layout.class);
                register_layout.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }

}
