package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;


public class SuvidhaMember extends ActionBarActivity {

    LocationManager myLocationManager;
    String PROVIDER = LocationManager.GPS_PROVIDER;

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";

    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suvidha_member);

        // Get the message from the intent
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView welcomelogo = (TextView)findViewById(R.id.Welcomelogo);

        welcomelogo.setText("Hi " + username);

        // Set the text view as the activity layout
        //setContentView(textView);
    }

    public void gotoSearchResults(View view)
    {
        //Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //String selected = spinner.getSelectedItem().toString();
        myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //get last known location, if available
        Location location = myLocationManager.getLastKnownLocation(PROVIDER);
        String lat, lng;
        if (location == null) {
            //  gps fix not available
            //  error handling
            lat = "0";
            lng = "0";
        } else {
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
        }

        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra("search", lat + " " + lng);
        startActivity(intent);
    }

    public void gotoRateDoctors(View view)
    {
        Intent intent = new Intent(this, RateDoctors.class);
        startActivity(intent);
    }

    public void gotoForum(View view)
    {
        Intent intent = new Intent(this, Forum.class);
        //pass username to forum page
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    public void gotoCheckAppointmentStatus(View view)
    {
        Intent intent = new Intent(this, CheckAppointmentStatus.class);
        //pass username to forum page
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

}
