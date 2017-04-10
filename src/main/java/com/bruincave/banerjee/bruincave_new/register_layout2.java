package com.bruincave.banerjee.bruincave_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.banerjee.bruincave_new.R;

public class register_layout2 extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layouttwo);
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        final EditText dob = (EditText) findViewById(R.id.dobRg);
        final Spinner genderSpin = (Spinner) findViewById(R.id.genderSpin);
        final Spinner classSpin = (Spinner) findViewById(R.id.classSpin);

        final Button next_two = (Button) findViewById(R.id.nexttwo);

        final ImageButton back_two = (ImageButton) findViewById(R.id.back_two);

        next_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dob_str = dob.getText().toString();
                final String classSpin_str = classSpin.getSelectedItem().toString();
                final String genderSpin_str = genderSpin.getSelectedItem().toString();

                if(dob_str.trim().equals("") || classSpin_str.trim().equals("Class") || genderSpin_str.trim().equals("Gender") ) {
                    if (dob_str.trim().equals("")) {
                        dob.setError("First name is required!");
                    }

                    if (genderSpin_str.trim().equals("Gender")) {
                        TextView errorText = (TextView)genderSpin.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Gender is required!");//changes the selected item text to this
                    }

                    if (classSpin_str.trim().equals("Class")) {
                        TextView errorText = (TextView)classSpin.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Class is required!");//changes the selected item text to this
                    }
                }else {

                    Intent register3Intent = new Intent(register_layout2.this, register_layout3.class);
                    register_layout2.this.startActivity(register3Intent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

                }

            }
        });
        back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(register_layout2.this, register_layout.class);
                register_layout2.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });
    }
}