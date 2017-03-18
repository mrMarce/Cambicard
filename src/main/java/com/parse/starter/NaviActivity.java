package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.LogOutCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //declare variables for GUI items
        final EditText numberGet = (EditText) findViewById(R.id.cardNumber);
        final Spinner brandSpinner = (Spinner) findViewById(R.id.brandSpinner);
        final Spinner amountSpinner = (Spinner) findViewById(R.id.amountSpinner);
        final Spinner wantedSpinner = (Spinner) findViewById(R.id.wantedSpinner);

        //declare the upload butotn
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //set the title
        setTitle("Cambicard");

        //create the list of card brands
        List<String> brandItems =  new ArrayList<String>();
        brandItems.add("Amazon");
        brandItems.add("AMX");
        brandItems.add("Walmart");
        brandItems.add("Target");
        brandItems.add("iTunes");
        brandItems.add("Hollister");

        //make an array adapter so that the list can be put into a Spinner
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, brandItems);
        //attach the adapter to the spinner
        brandSpinner.setAdapter(adapter0);
        wantedSpinner.setAdapter(adapter0);
        //create list of amounts
        List<String> amounts =  new ArrayList<String>();
        amounts.add("$10");
        amounts.add("$15");
        amounts.add("$25");
        amounts.add("$50");
        amounts.add("$100");
        amounts.add("$150");

        //make another adapter to put this list in a Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, amounts);
        //attach list to spinner
        amountSpinner.setAdapter(adapter1);




        //When the upload button is clicked...
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


        //get the values from the spinners and the text field to upload to my database
                String brand = brandSpinner.getSelectedItem().toString();
                String amount = amountSpinner.getSelectedItem().toString();
                String wanted = wantedSpinner.getSelectedItem().toString();
                long cardNumber = Long.parseLong(String.valueOf(numberGet.getText()));

        //create new ParseObject called "CardInfo", add the chosen values to it
                ParseObject giftCard = new ParseObject("CardInfo");
                giftCard.put("brand", brand);
                giftCard.put("amount", amount);
                giftCard.put("number", cardNumber);
                giftCard.put("wanted", wanted);
        //get the current user to distinguish who uploaded the card
                giftCard.put("uploader", ParseUser.getCurrentUser().getUsername().toString());
        //specify ACL to set that the card info can be downloaded by any user, not just the one that uploaded it
                ParseACL giftCardACL = new ParseACL();
                giftCardACL.setPublicReadAccess(true);
                giftCard.setACL(giftCardACL);
                giftCard.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            //if the card is uploaded successfully, let the user know

                            Snackbar.make(view, "Card uploaded", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else{
                            //if not, print the error
                            Log.i("bad", e.toString());

                        }
                    }
                });


            }
        });
        //declare Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {
        //if the user clicks this item, send them to CardListActivity
        Intent i = new Intent(this,CardListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            //log user out
        ParseUser user = ParseUser.getCurrentUser();
            user.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
        //send user to MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);

                    }
                }
            });
        } else if (id == R.id.nav_search) {
        //send user to SearchActivity
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//nothing

    }
}
