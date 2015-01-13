package com.example.prithwishmukherjee.duvidha;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class SuvidhaDoctor extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suvidha_doctor);

        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Get name from database using username
        String name = username;
        TextView textView = (TextView)findViewById(R.id.textView4);
        textView.setText("Welcome Dr." + name);
        ImageView iv = (ImageView)findViewById(R.id.imageView2);

        iv.setImageResource(R.drawable.ic_launcher);

        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        int rating = 4;
        rb.setRating(rating);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suvidha_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewExistingAppointments(View view)
    {
        Intent intent = new Intent(this,ExistingAppointments.class);
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    public void updateDetails(View view)
    {
        Intent intent = new Intent(this,UpdateDetails.class);
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }
}
