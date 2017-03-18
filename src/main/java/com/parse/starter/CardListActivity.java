package com.parse.starter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CardListActivity extends AppCompatActivity {
    //make global variables
    ListView list;
    List<String> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       //declare boilerplate UI
        super.onCreate(savedInstanceState);
        setTitle("Cambicard");
        setContentView(R.layout.activity_card_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //assign values to variables
        //make list, listView, and adapter to populate the listView
        myList = new ArrayList<String>();
        list = (ListView) findViewById(R.id.listy);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        list.setAdapter(adapter);
        //query the online database object "MyCards", where the uploader is the username the user clicked on
        ParseQuery query = ParseQuery.getQuery("MyCards");
        query.whereEqualTo("uploader", ParseUser.getCurrentUser().getUsername().toString());


        query.findInBackground(new FindCallback<ParseObject>() {
            //still have to populate spinners and make reference to them to use them
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                    //get the first returned card number and add it to the list
                    String number = String.valueOf(objects.get(0).get("number"));
                    myList.add(number);
                    adapter.notifyDataSetChanged();


            }
        });





    }

}
