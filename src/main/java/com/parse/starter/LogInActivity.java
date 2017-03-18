package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void logInUser(View view){
        //declare text fields for use
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        //get text fields' values
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        //log user in
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    //user successfully logged in, start new activity so the user can do stuff
                    Intent intent = new Intent(getApplicationContext(), NaviActivity.class);
                    startActivity(intent);
                    //let user know they have been logged in
                    Toast.makeText(LogInActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                }else{
                    //yikes
                }
            }
        });


    }


}
