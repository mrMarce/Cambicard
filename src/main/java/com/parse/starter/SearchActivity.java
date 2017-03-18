package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
//declare global variables
    Spinner spinner;
    Spinner spinner1;
    ListView listView;
    List<String> queryObjects;
    ArrayAdapter<String> objectAdapter;
    String number;
    String number1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.list);
        queryObjects = new ArrayList<String>();

//when a listView item is clicked, do...
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        //get the value of the list item that was clicked
        final String clickedUser = queryObjects.get(position).toString();
        //get the current user's username
        final String clickingUser = ParseUser.getCurrentUser().getUsername().toString();

        //remove the card the user selected from the list
        queryObjects.remove(position);
        objectAdapter.notifyDataSetChanged();
        //query the online "CardInfo" object
        ParseQuery query = ParseQuery.getQuery("CardInfo");
        query.whereEqualTo("uploader",clickedUser );
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                     number = String.valueOf(objects.get(0).get("number"));
//if the card number is real, upload it to the "MyCards" segment of the database
if(number != null) {
    ParseObject myCards = new ParseObject("MyCards");
    myCards.add("uploader", clickingUser.toString());
    myCards.add("number", number);
    //make it accessable by more than one user
    ParseACL giftCardACL = new ParseACL();
    giftCardACL.setPublicReadAccess(true);
    myCards.setACL(giftCardACL);
    myCards.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {

        }
    });
}

            }
        });


        /*basically the same query as above, except this time the purpose is
        to give the current user's card to the user whose card the current user just took.
        This is how the exchange happens*/
       ParseQuery query1 = ParseQuery.getQuery("CardInfo");
        query1.whereEqualTo("uploader",ParseUser.getCurrentUser().getUsername().toString() );
        query1.setLimit(1);


        query1.findInBackground(new FindCallback<ParseObject>() {
            //still have to populate spinners and make reference to them to use them
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                number1 = String.valueOf(objects.get(0).get("number"));

        //upload the card number to "MyCards," just as above
                ParseObject myCards = new ParseObject("MyCards");
                myCards.add("uploader", clickedUser.toString());
                myCards.add("number", number1);
        //make it accessable by more than one user
                ParseACL giftCardACL = new ParseACL();
                giftCardACL.setPublicReadAccess(true);
                myCards.setACL(giftCardACL);
                myCards.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                    }
                });
            }
        });




    }
});


        //declare spinner, list, and adapter, so user can filter search for cards
        spinner = (Spinner) findViewById(R.id.brandSpinner);
        spinner1 =(Spinner) findViewById(R.id.amountSpinner);


        List<String> brandItems =  new ArrayList<String>();
        brandItems.add("Amazon");
        brandItems.add("AMX");
        brandItems.add("Walmart");
        brandItems.add("Target");
        brandItems.add("iTunes");
        brandItems.add("Hollister");

        if (queryObjects == null) {
            objectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, brandItems);
listView.setAdapter(objectAdapter);
        }else{

                objectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, queryObjects);
            listView.setAdapter(objectAdapter);


        }

        //declare spinner, list, and adapter, so user can filter search for cards


        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, brandItems);

        List<String> amounts =  new ArrayList<String>();
        amounts.add("$10");
        amounts.add("$15");
        amounts.add("$25");
        amounts.add("$50");
        amounts.add("$100");
        amounts.add("$150");


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, amounts);

        spinner1.setAdapter(adapter1);
        spinner.setAdapter(adapter0);
    }
//query the database for the card the user wants
public void queryImages(View view){



queryObjects.clear();
    final ParseQuery<ParseObject> query = ParseQuery.getQuery("CardInfo");
    query.whereEqualTo("brand", spinner.getSelectedItem().toString());
    query.whereNotEqualTo("uploader", ParseUser.getCurrentUser().getUsername());
    query.whereEqualTo("amount", spinner1.getSelectedItem().toString());
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
//for every returned value, add it to the list so the user can see it
            for(int i = 0; i <objects.size();i++){

                queryObjects.add(String.valueOf(objects.get(i).get("uploader")));
                objectAdapter.notifyDataSetChanged();
            }

        }
    });





}



}
