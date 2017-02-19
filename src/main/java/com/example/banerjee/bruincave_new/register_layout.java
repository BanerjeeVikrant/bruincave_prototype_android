package com.example.banerjee.bruincave_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class register_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layoutone);

        final EditText firstname = (EditText) findViewById(R.id.firstnameRg);
        final EditText lastname = (EditText) findViewById(R.id.lastnameRg);
        final Button next_one = (Button) findViewById(R.id.nextone);

        final ImageButton back_one = (ImageButton) findViewById(R.id.back_one);

        next_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname_str = firstname.getText().toString();
                final String lastname_str = lastname.getText().toString();

                if(firstname_str.trim().equals("") || lastname_str.trim().equals("")) {
                    if (firstname_str.trim().equals("")) {
                        firstname.setError("First name is required!");
                    }

                    if (lastname_str.trim().equals("")) {
                        lastname.setError("Last name is required!");
                    }
                }else {
                    Intent register2Intent = new Intent(register_layout.this, register_layout2.class);
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
