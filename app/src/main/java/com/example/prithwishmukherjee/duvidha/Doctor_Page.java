package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class Doctor_Page extends ActionBarActivity {

    String doc_name = new String();
    Doctor doctor = new Doctor();
    String doc_user_name = new String();
    String doc_specialization = new String();
    String lat = new String();
    String lon = new String();
    final String CLASS_NAME = "Doctor_Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__page);
        final RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingBar2);
        ratingbar.setIsIndicator(true);

        final TextView textview1 = (TextView) findViewById(R.id.textView18);
        textview1.setTextSize(12);

        final TextView textview2 = (TextView) findViewById(R.id.textView19);
        textview2.setTextSize(12);

        final TextView textview3 = (TextView) findViewById(R.id.textView20);
        textview3.setTextSize(12);

        final TextView textview4 = (TextView) findViewById(R.id.textView21);
        textview4.setTextSize(12);

        Intent intent = getIntent();
        String message = intent.getStringExtra("doctor");
        String[] doc = message.split("#", 5);;

        doc_name = doc[0];
        doc_user_name = doc[1];
        doc_specialization = doc[2];
        lat = doc[3];
        lon = doc[4];

        try {
            //To retrieve from the database
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    if(task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Doctor> objects = task.getResult();
                        System.out.println("After here");
                        for(Doctor doc:objects){
                            if(doc.getName().equals(doc_name) && doc.getUsername().equals(doc_user_name)){
                                doctor = doc;
                            }
                        }
                        Log.e(CLASS_NAME, "HERE");

                        textview1.setText("Name - " + doctor.getName());
                        textview1.setEnabled(true);
                        textview1.setTextColor(Color.BLACK);
                        textview1.setTextSize(30);
                        textview2.setText("Address - " + doctor.getAddress());
                        textview2.setEnabled(true);
                        textview2.setTextColor(Color.BLACK);
                        textview2.setTextSize(30);
                        textview3.setText("Fees - " + doctor.getFees());
                        textview3.setEnabled(true);
                        textview3.setTextColor(Color.BLACK);
                        textview3.setTextSize(30);

                        ratingbar.setRating((float) Double.parseDouble(doctor.getRating()));

                        System.out.println("Kyu nahi ho raha hain bc!!");

                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " + error.getMessage());
        }

        final Button take_appointment = (Button) findViewById(R.id.button2);


        try {
            //To retrieve from the database
            IBMQuery<Appointments> query = IBMQuery.queryForClass(Appointments.class);

            query.find().continueWith(new Continuation<List<Appointments>, Void>() {

                @Override
                public Void then(Task<List<Appointments>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {

                        final List<Appointments> objects = task.getResult();
                        List<Appointments> appointments = new ArrayList<Appointments>();
                        for (Appointments app : objects) {
                            if (app.getDocName().equals(doc_name) && app.getDocUsername().equals(doc_user_name)) {
                                appointments.add(app);
                            }
                        }
                        textview4.setTextSize(30);
                        if(appointments.size() == 0){
                            textview4.setTextColor(Color.rgb(0,200,0));
                            textview4.setText("Doctor is Available\n");
                        }
                        else {
                            Collections.sort(appointments, new Comparator<Appointments>() {
                                public int compare(Appointments a1, Appointments a2) {
                                    if (Date.valueOf(a1.getDate()).compareTo(Date.valueOf(a2.getDate())) > 0)
                                        return -1;
                                    if (Date.valueOf(a1.getDate()).compareTo(Date.valueOf(a2.getDate())) < 0)
                                        return 1;
                                    else {
                                        if (Time.valueOf(a1.getTime()).compareTo(Time.valueOf(a2.getTime())) > 0)
                                            return -1;
                                        if (Date.valueOf(a1.getTime()).compareTo(Date.valueOf(a2.getTime())) < 0)
                                            return 1;
                                        else return 0;
                                    }
                                }
                            });

                            textview4.setTextColor(Color.RED);
                            textview4.setText("Doctor Available after Date :- " + appointments.get(0).getDate() + " and Time :- " + appointments.get(0).getTime());

                            take_appointment.setEnabled(true);

                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " + error.getMessage());
        }

        take_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Context context = this;

        Button back = (Button) findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResults.class);
                intent.putExtra("search", lat + " " + lon + " " + doc_specialization);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor__page, menu);
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
}
