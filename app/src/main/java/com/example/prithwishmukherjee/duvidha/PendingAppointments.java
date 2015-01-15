package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.ViewGroup;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.sql.*;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class PendingAppointments extends ActionBarActivity {

    TableLayout maintable;
    String username;
    public static final String CLASS_NAME = "PendingAppointments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(SuvidhaDoctor.EXTRA_MESSAGE);
        Log.e(CLASS_NAME,"Username is : "+username);
        setContentView(R.layout.activity_existing_appointments);

        Date date = new Date(2015-1900, 2-1, 1);
        Time time = new Time(20, 30, 0);

        final TableLayout ll = (TableLayout)findViewById(R.id.mainTable);
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
                            if(app.getDocUsername().equalsIgnoreCase(username) && app.getStatus().equalsIgnoreCase("pending")){

                                //Add the appointments here, and the patients username
                                Log.e(CLASS_NAME,app.getPatName());
                                Log.e(CLASS_NAME,app.getDocUsername());
                                Log.e(CLASS_NAME,app.getTime());
                                Log.e(CLASS_NAME,app.getDate());
                                Log.e(CLASS_NAME,app.getStatus());

                                TableRow row = new TableRow(context);
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);

                                //Get from Database
                                TextView pname = new TextView(context);
                                pname.setText(app.getPatName());

                                TextView adate = new TextView(context);
                                adate.setText(app.getDate());

                                TextView atime = new TextView(context);
                                atime.setText(app.getTime());

                                ImageButton acceptAppointment = new ImageButton(context);
                                acceptAppointment.setImageResource(R.drawable.plus);
                                acceptAppointment.setClickable(true);
                                acceptAppointment.setBackgroundColor(Color.WHITE);
                                acceptAppointment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // row is your row, the parent of the clicked button
                                        View row = (View) v.getParent();
                                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                                        ViewGroup container = ((ViewGroup)row.getParent());
                                        // delete the row and invalidate your view so it gets redrawn
                                        TextView temptextview= (TextView) ((ViewGroup)row).getChildAt(0);
                                        String username = (String) temptextview.getText();

                                        //Change status from pending to existing

                                        container.removeView(row);
                                        container.invalidate();
                                    }
                                });


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

                                        //Delete from this pending database

                                        container.removeView(row);
                                        container.invalidate();
                                    }
                                });

                                row.setBackgroundColor(Color.WHITE);

                                row.addView(pname);row.addView(adate);row.addView(atime);row.addView(acceptAppointment);row.addView(cancelAppointment);
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


        //Access Database
        //for (int i = 0; i <2; i++) {

        //}
    }
}
