package com.example.prithwishmukherjee.duvidha;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prithwishmukherjee.duvidha.MainActivity;


public class SuvidhaMember extends ActionBarActivity {

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
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String selected = spinner.getSelectedItem().toString();
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(EXTRA_MESSAGE,selected);
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
