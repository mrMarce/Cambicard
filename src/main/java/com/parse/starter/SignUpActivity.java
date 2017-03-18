package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    public void signUpUser(View view){
        //declare text fields for use

        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        //make new user with username and password
        ParseUser user = new ParseUser();
        user.setUsername(usernameField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        //sign up new user
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    //start new activity so user can do stuff
                    Intent intent = new Intent(getApplicationContext(), NaviActivity.class);
                    startActivity(intent);
        //let user know they have been signed up
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                }else{
//print error
                    Log.i("noo", e.toString());

                }
            }
        });


    }


}
