/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {
public static ParseUser currUser;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

      ParseAnalytics.trackAppOpenedInBackground(getIntent());
      setContentView(R.layout.activity_main);



  }





public void launchLogIn(View view){

    Intent intent = new Intent(this, LogInActivity.class);
    startActivity(intent);
}

    public void launchSignUp(View view){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.


      return super.onOptionsItemSelected(item);
  }
}
