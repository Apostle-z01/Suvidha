package com.example.prithwishmukherjee.duvidha;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.RatingBar.OnRatingBarChangeListener;


public class RateDoctors extends ActionBarActivity implements OnItemSelectedListener{

    Spinner spinner;
    RatingBar ratingBar2;
    EditText editReview;
    Button buttonPost;
    TextView textDcotorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_doctors);
        spinner = (Spinner)findViewById(R.id.spinner3);
        ratingBar2 = (RatingBar)findViewById(R.id.ratingBar2);
        editReview = (EditText)findViewById(R.id.editReview);
        buttonPost = (Button)findViewById(R.id.buttonPost);
        textDcotorName = (TextView)findViewById(R.id.textDoctorName);
        //  db connection and populate Globals.state with the names

        ratingBar2.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar2, float rating,
                                        boolean fromUser) {
                if (ratingBar2.getRating() < 1)
                    ratingBar2.setRating(1);
            }
        });

        populate();

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Globals.state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_state);

        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_doctors, menu);
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

    public void populate(){

        //  populate Globals.state here

        Globals.state = new String[3];
        Globals.state[0] = "Select Doctor";
        Globals.state[1] = "best";
        Globals.state[2] = "test";

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        if(!selState.equals("Select Doctor")) {
            ratingBar2.setRating(1);
            ratingBar2.setVisibility(View.VISIBLE);
            editReview.setVisibility(View.VISIBLE);
            buttonPost.setVisibility(View.VISIBLE);
            textDcotorName.setText(selState);
            editReview.setText("");
            editReview.setHint("Your review here");
        }
        else{
            textDcotorName.setText("");
            ratingBar2.setVisibility(View.INVISIBLE);
            editReview.setVisibility(View.INVISIBLE);
            buttonPost.setVisibility(View.INVISIBLE);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }
}
