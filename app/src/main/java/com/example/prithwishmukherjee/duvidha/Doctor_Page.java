package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class Doctor_Page extends ActionBarActivity {

    String doc_name = new String();
    Doctor doctor = new Doctor();
    Patient patient = new Patient();
    String doc_user_name = new String();
    String doc_specialization = new String();
    String lat = new String();
    String lon = new String();
    String pat_user_name = new String();
    final String CLASS_NAME = "Doctor_Page";
    int year,month,day,hour,minute;

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
        String[] doc = message.split("#", 6);

        doc_name = doc[0];
        doc_user_name = doc[1];
        doc_specialization = doc[2];
        lat = doc[3];
        lon = doc[4];
        pat_user_name = doc[5];

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl1);

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
                            if (app.getDocName().equals(doc_name) && app.getDocUsername().equals(doc_user_name) && app.getStatus().equals("confirmed")) {
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
                                    String[] dateobtained1 = a1.getDate().split(".", 3);
                                    int day1 = Integer.parseInt(dateobtained1[0]);
                                    int month1 = Integer.parseInt(dateobtained1[1]);
                                    int year1 = Integer.parseInt(dateobtained1[2]);
                                    String[] timeobtained1 = a1.getTime().split(":",2);
                                    int hour1 = Integer.parseInt(timeobtained1[0]);
                                    int minute1 = Integer.parseInt(timeobtained1[1]);
                                    String[] dateobtained2 = a2.getDate().split(".", 3);
                                    int day2 = Integer.parseInt(dateobtained2[0]);
                                    int month2 = Integer.parseInt(dateobtained2[1]);
                                    int year2 = Integer.parseInt(dateobtained2[2]);
                                    String[] timeobtained2 = a2.getTime().split(":",2);
                                    int hour2 = Integer.parseInt(timeobtained2[0]);
                                    int minute2 = Integer.parseInt(timeobtained2[1]);
                                    if (year1 > year2) {
                                        return -1;
                                    }
                                    else if (year1 < year2) {
                                        return 1;
                                    }
                                    else {
                                        if(month1 > month2){
                                            return -1;
                                        }
                                        else if(month1 < month2){
                                            return 1;
                                        }
                                        else {
                                            if(day1 > day2){
                                                return -1;
                                            }
                                            else if(day1 < day2){
                                                return 1;
                                            }
                                            else {
                                                if(hour1 > hour2){
                                                    return -1;
                                                }
                                                else if(hour1 < hour2){
                                                    return 1;
                                                }
                                                else {
                                                    if(minute1 > minute2){
                                                       return -1;
                                                    }
                                                    else if(minute1 < minute2){
                                                        return 1;
                                                    }
                                                    else {
                                                        return 0;
                                                    }
                                                }
                                            }
                                        }
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

        final Calendar myCalendar = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        final Context context = this;

        final TimePickerDialog.OnTimeSetListener timePickerListener =
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        myCalendar.set(Calendar.HOUR, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);

                        try {
                            //To retrieve from the database
                            IBMQuery<Patient> query = IBMQuery.queryForClass(Patient.class);

                            query.find().continueWith(new Continuation<List<Patient>, Void>() {

                                @Override
                                public Void then(Task<List<Patient>> task) throws Exception {
                                    if(task.isFaulted()) {
                                        // Handle errors
                                        System.out.println("Entered");
                                    } else {
                                        // do more work
                                        System.out.println("Here");
                                        final List<Patient> objects = task.getResult();
                                        System.out.println("After here");
                                        for (Patient pat : objects) {
                                            if (pat.getUsername().equals(pat_user_name)) {
                                                patient = pat;
                                                break;
                                            }
                                        }
                                        Log.e(CLASS_NAME, "HERE");
                                        Appointments app = new Appointments();
                                        app.setPatUsername(pat_user_name);
                                        app.setPatName(patient.getName());
                                        app.setStatus("pending");
                                        app.setDocName(doc_name);
                                        app.setDocUsername(doc_user_name);
                                        String setdate = "";
                                        if (myCalendar.get(Calendar.DAY_OF_MONTH) <= 9){
                                            setdate += "0" + myCalendar.get(Calendar.DAY_OF_MONTH) + ".";
                                        }
                                        else {
                                            setdate += myCalendar.get(Calendar.DAY_OF_MONTH) + ".";
                                        }
                                        if (myCalendar.get(Calendar.MONTH) < 9){
                                            setdate += "0" + (myCalendar.get(Calendar.MONTH) + 1) + ".";
                                        }
                                        else {
                                            setdate += (myCalendar.get(Calendar.MONTH) + 1) + ".";
                                        }
                                        setdate += myCalendar.get(Calendar.YEAR);
                                        app.setDate(setdate);
                                        String settime = "";
                                        if (myCalendar.get(Calendar.HOUR) <= 9){
                                            settime += "0" + myCalendar.get(Calendar.HOUR) + ":";
                                        }
                                        else {
                                            settime += myCalendar.get(Calendar.HOUR) + ":";
                                        }
                                        if (myCalendar.get(Calendar.MINUTE) <= 9){
                                            settime += "0" + myCalendar.get(Calendar.MINUTE);
                                        }
                                        else {
                                            settime += myCalendar.get(Calendar.MINUTE);
                                        }
                                        app.setTime(settime);

                                        app.save().continueWith(new Continuation<IBMDataObject, Void>() {

                                            @Override
                                            public Void then(Task<IBMDataObject> task) throws Exception {
                                                if (task.isFaulted()) {
                                                    // Handle errors
                                                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                                                    return null;
                                                } else {
                                                    Appointments myapp = (Appointments) task.getResult();
                                                    Log.e(CLASS_NAME,myapp.getDocName());
                                                    Log.e(CLASS_NAME,myapp.getPatName());
                                                    // Do more work
                                                }
                                                return null;
                                            }
                                        });

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                        Log.e(CLASS_NAME, "Inside user received");
                                        alertDialog.setTitle("Appointment Accepted");
                                        alertDialog.setMessage("Request for Appointment Notified to Doctor");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // here you can add functions
                                            }
                                        });
                                        AlertDialog dialog = alertDialog.create();
                                        alertDialog.show();



                                    }
                                    return null;
                                }
                            }, Task.UI_THREAD_EXECUTOR);
                        } catch (IBMDataException error) {
                            Log.e(CLASS_NAME,"Exception : " + error.getMessage());
                        }

                    }
                };

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                view.setMinDate(System.currentTimeMillis() - 1000);
                TimePickerDialog time_picker = new TimePickerDialog(context,
                        timePickerListener, hour, minute,false);
                time_picker.show();

            }

        };



        take_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog date_picker = new DatePickerDialog(Doctor_Page.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date_picker.show();
            }
        });

        Button back = (Button) findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResults.class);
                intent.putExtra("search", lat + " " + lon + " " + doc_specialization + " " + pat_user_name);
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
