package com.bruincave.banerjee.bruincave_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.banerjee.bruincave_new.R;

public class register_layout2 extends AppCompatActivity{


    private Typeface fontPTSerif, fontBitter;

    private String firstnameText, lastnameText, dobText, classSpinText, genderSpinText, usernameText, passwordText, passwordRepeatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layouttwo);
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        fontPTSerif = Typeface.createFromAsset(getAssets(),"fonts/PTSerif.ttf");
        fontBitter = Typeface.createFromAsset(getAssets(),"fonts/Bitter.otf");

        TextView textLabel = (TextView) findViewById(R.id.textLabel1);
        textLabel.setTypeface(fontBitter);

        final EditText dob = (EditText) findViewById(R.id.dobRg);
        final Spinner genderSpin = (Spinner) findViewById(R.id.genderSpin);
        final Spinner classSpin = (Spinner) findViewById(R.id.classSpin);
        final Button next_two = (Button) findViewById(R.id.nexttwo);
        final ImageButton back_two = (ImageButton) findViewById(R.id.back_two);

        next_two.setTypeface(fontPTSerif);

        dob.setTypeface(fontPTSerif);

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

        dob.setText(dobText);
        classSpin.setSelection(getIndex(classSpin, classSpinText));
        genderSpin.setSelection(getIndex(genderSpin, genderSpinText));

        next_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dobText = dob.getText().toString().trim();
                classSpinText = classSpin.getSelectedItem().toString().trim();
                genderSpinText = genderSpin.getSelectedItem().toString();

                if(dobText.trim().equals("") || classSpinText.trim().equals("Class") || genderSpinText.trim().equals("Gender") ) {
                    if (dobText.trim().equals("")) {
                        dob.setError("First name is required!");
                    }

                    if (genderSpinText.trim().equals("Gender")) {
                        TextView errorText = (TextView)genderSpin.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Gender is required!");//changes the selected item text to this
                    }

                    if (classSpinText.trim().equals("Class")) {
                        TextView errorText = (TextView)classSpin.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Class is required!");//changes the selected item text to this
                    }
                }else {

                    Intent register3Intent = new Intent(register_layout2.this, register_layout3.class);
                    register3Intent.putExtra("firstname", firstnameText);
                    register3Intent.putExtra("lastname", lastnameText);
                    register3Intent.putExtra("dob", dobText);
                    register3Intent.putExtra("classSpin", classSpinText);
                    register3Intent.putExtra("genderSpin", genderSpinText);
                    register3Intent.putExtra("username", usernameText);
                    register3Intent.putExtra("password", passwordText);
                    register3Intent.putExtra("passwordRepeat", passwordRepeatText);
                    register_layout2.this.startActivity(register3Intent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

                }

            }
        });
        back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(register_layout2.this, register_layout.class);
                registerIntent.putExtra("firstname", firstnameText);
                registerIntent.putExtra("lastname", lastnameText);
                registerIntent.putExtra("dob", dobText);
                registerIntent.putExtra("classSpin", classSpinText);
                registerIntent.putExtra("genderSpin", genderSpinText);
                registerIntent.putExtra("username", usernameText);
                registerIntent.putExtra("password", passwordText);
                registerIntent.putExtra("passwordRepeat", passwordRepeatText);
                register_layout2.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });


    }
    public int getIndex(Spinner spinner, String myString){
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}