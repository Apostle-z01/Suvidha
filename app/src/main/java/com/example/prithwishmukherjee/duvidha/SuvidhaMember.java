package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class SuvidhaMember extends ActionBarActivity {

    LocationManager myLocationManager;
    String PROVIDER = LocationManager.GPS_PROVIDER;

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";
    public static final String CLASS_NAME = "ExistingAppointments";


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

        TableLayout temp = (TableLayout)findViewById(R.id.appointmentTable);
        temp.setVisibility(View.GONE);
        TextView t1 = (TextView)findViewById(R.id.textView);
        t1.setVisibility(View.GONE);
        TextView t2 = (TextView)findViewById(R.id.close);
        t2.setVisibility(View.GONE);
        View stt = (View)findViewById(R.id.stline);
        stt.setVisibility(View.GONE);


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
        //pass username to forum page
        TextView t1 = (TextView)findViewById(R.id.textView);
        t1.setVisibility(View.VISIBLE);
        TextView t2 = (TextView)findViewById(R.id.close);
        t2.setVisibility(View.VISIBLE);
        View stt = (View)findViewById(R.id.stline);
        stt.setVisibility(View.VISIBLE);
        TableLayout temp = (TableLayout)findViewById(R.id.appointmentTable);
        temp.setVisibility(View.VISIBLE);
        TextView bb = (TextView)findViewById(R.id.Appointment);
        bb.setClickable(false);



        final TableLayout ll = (TableLayout)findViewById(R.id.appointmentTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);

        final Context context = this;
        try {
            //To retrieve from the database
            IBMQuery<Appointments> query = IBMQuery.queryForClass(Appointments.class);

            query.find().continueWith(new Continuation<List<Appointments>, Void>() {

                @Override
                public Void then(Task<List<Appointments>> task) throws Exception {
                    int index=0;
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Appointments> objects = task.getResult();
                        for(Appointments app:objects){
                            Log.e(CLASS_NAME, app.getDocUsername());
                            Log.e(CLASS_NAME, app.getPatUsername());
                            if(app.getPatUsername().equalsIgnoreCase(username)){
                                //Add the appointments here, and the patients username

                                TableRow row = new TableRow(context);
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);

                                //Get from Database
                                TextView dname = new TextView(context);
                                dname.setText(app.getDocUsername());

                                TextView adate = new TextView(context);
                                adate.setText(app.getDate());

                                TextView atime = new TextView(context);
                                atime.setText(app.getTime());


                                row.setBackgroundColor(Color.WHITE);

                                ImageButton appointmentStatus = new ImageButton(context);
                                if((app.getStatus()).equals("confirmed"))appointmentStatus.setImageResource(R.drawable.tick);
                                else appointmentStatus.setImageResource(R.drawable.waiting);
                                appointmentStatus.setClickable(false);
                                appointmentStatus.setBackgroundColor(Color.WHITE);



                                ImageButton cancelAppointment = new ImageButton(context);
                                cancelAppointment.setImageResource(R.drawable.minus);
                                cancelAppointment.setClickable(true);
                                cancelAppointment.setBackgroundColor(Color.WHITE);
                                cancelAppointment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // row is your row, the parent of the clicked button
                                        View row = (View) v.getParent();
                                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                                        ViewGroup container = ((ViewGroup)row.getParent());
                                        // delete the row and invalidate your view so it gets redrawn
                                        TextView temptextview= (TextView) ((ViewGroup)row).getChildAt(0);
                                        String username = (String) temptextview.getText();

                                        //Delete from Database



                                        container.removeView(row);
                                        container.invalidate();
                                    }
                                });
                                row.addView(dname);row.addView(adate);row.addView(atime);row.addView(appointmentStatus);row.addView(cancelAppointment);

                                ll.addView(row, index);
                                index++;
                            }
                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }


    //        intent.putExtra(EXTRA_MESSAGE,username);
    //        startActivity(intent);
    }


    public void deactiveAppointmentTable(View view)
    {
        TableLayout temp = (TableLayout)findViewById(R.id.appointmentTable);
        temp.removeAllViews();
        temp.setVisibility(View.GONE);
        TextView t1 = (TextView)findViewById(R.id.textView);
        t1.setVisibility(View.GONE);
        TextView t2 = (TextView)findViewById(R.id.close);
        t2.setVisibility(View.GONE);
        View stt = (View)findViewById(R.id.stline);
        stt.setVisibility(View.GONE);
        TextView bb = (TextView)findViewById(R.id.Appointment);
        bb.setClickable(true);
    }

}
