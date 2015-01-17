package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class SuvidhaHospital extends ActionBarActivity {

    public static final String CLASS_NAME = "SuvidhaHospital";
    String username;
    String hosName;
    List<Doctor> doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suvidha_hospital);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        hosName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NAME);

        TextView textView = (TextView)findViewById(R.id.textView11);
        textView.setText("Welcome " + username + " Hospital");

        doctors = new ArrayList<Doctor>();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suvidha_hospital, menu);
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

    public void addDoctors(View view)
    {
        final TableLayout ll = (TableLayout)findViewById(R.id.docTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);
        ll.removeAllViews();

        final Context context = this;

        try {
            //To retrieve from the database
            doctors.clear();
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);
            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    int index=0;
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Doctor> objects = task.getResult();
                        for(final Doctor doc:objects){
                            Log.e(CLASS_NAME, doc.getUsername());
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME, doc.getArea());
                            //doctors.add(doc);

                            //if(!doc.getHosUsername().equalsIgnoreCase(username)){
                                TableRow row = new TableRow(context);
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);

                                //Get from Database
                                TextView dname = new TextView(context);
                                dname.setText(doc.getName());
                                dname.setTextSize(30);

                                TextView darea = new TextView(context);
                                darea.setText(doc.getArea());
                                darea.setTextSize(30);

                                ImageButton acceptAppointment = new ImageButton(context);
                                acceptAppointment.setImageResource(R.drawable.plus);
                                acceptAppointment.setClickable(true);
                                acceptAppointment.setBackgroundColor(Color.WHITE);
                                acceptAppointment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //confirmAppointment(app.getDocName(),app.getDate(),app.getTime(),app.getPatUsername());
                                        // row is your row, the parent of the clicked button
                                        View row = (View) v.getParent();
                                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                                        ViewGroup container = ((ViewGroup)row.getParent());
                                        // delete the row and invalidate your view so it gets redrawn
                                        TextView temptextview= (TextView) ((ViewGroup)row).getChildAt(0);
                                        String username = (String) temptextview.getText();

                                        //Add to hospital_Doctor
                                        Hospital_Doctor hos_doc = new Hospital_Doctor();
                                        hos_doc.setDocName(doc.getName());
                                        hos_doc.setHosName(hosName);
                                        hos_doc.setDocUsername(doc.getUsername());
                                        hos_doc.setArea(doc.getArea());
                                        hos_doc.setHosUsername(username);

                                        hos_doc.save().continueWith(new Continuation<IBMDataObject, Void>() {

                                            @Override
                                            public Void then(Task<IBMDataObject> task) throws Exception {
                                                if (task.isFaulted()) {
                                                    // Handle errors
                                                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                                                    return null;
                                                } else {
                                                    Hospital_Doctor myHos = (Hospital_Doctor) task.getResult();
                                                    Log.e(CLASS_NAME,myHos.getDocName());
                                                    Log.e(CLASS_NAME,myHos.getArea());
                                                    Log.e(CLASS_NAME,myHos.getHosUsername());

                                                    // Do more work
                                                }
                                                return null;
                                            }
                                        });

                                        container.removeView(row);
                                        container.invalidate();
                                    }
                                });



                                row.setBackgroundColor(Color.WHITE);

                                row.addView(dname);row.addView(darea);row.addView(acceptAppointment);
                                ll.addView(row, index);
                                index++;
                            //}
                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
    }

    public void existingDoctors(View view)
    {
        final TableLayout ll = (TableLayout)findViewById(R.id.docTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);
        ll.removeAllViews();
        final Context context = this;

        try {
            //To retrieve from the database
            doctors.clear();
            IBMQuery<Hospital_Doctor> query = IBMQuery.queryForClass(Hospital_Doctor.class);
            query.find().continueWith(new Continuation<List<Hospital_Doctor>, Void>() {

                @Override
                public Void then(Task<List<Hospital_Doctor>> task) throws Exception {
                    int index=0;
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Hospital_Doctor> objects = task.getResult();
                        for(final Hospital_Doctor doc:objects){
                            Log.e(CLASS_NAME, doc.getDocName());
                            Log.e(CLASS_NAME, doc.getArea());
                            //doctors.add(doc);

                            //if(!doc.getHosUsername().equalsIgnoreCase(username)){
                            TableRow row = new TableRow(context);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);

                            //Get from Database
                            TextView dname = new TextView(context);
                            dname.setText(doc.getDocName());
                            dname.setTextSize(30);

                            TextView darea = new TextView(context);
                            darea.setText(doc.getArea());
                            darea.setTextSize(30);

                            row.setBackgroundColor(Color.WHITE);

                            row.addView(dname);row.addView(darea);
                            ll.addView(row, index);
                            index++;
                            //}
                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }


    }
}
