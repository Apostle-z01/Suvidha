package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.http.IBMHttpResponse;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class CheckAppointmentStatus extends ActionBarActivity {

    String username;
    public static final String CLASS_NAME = "CheckAppointmentStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(SuvidhaDoctor.EXTRA_MESSAGE);
        setContentView(R.layout.activity_check_appointment_status);
        //maintable = (TableLayout)this.maintable;

        Date date = new Date(2015, 2, 1);
        Time time = new Time(20, 30, 0);

        final TableLayout ll = (TableLayout) findViewById(R.id.appointmentTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);

        final Context context = this;
        try {
            //To retrieve from the database
            IBMQuery<Appointments> query = IBMQuery.queryForClass(Appointments.class);

            query.find().continueWith(new Continuation<List<Appointments>, Void>() {

                @Override
                public Void then(Task<List<Appointments>> task) throws Exception {
                    int index = 0;
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Appointments> objects = task.getResult();
                        for (final Appointments app : objects) {
                            Log.e(CLASS_NAME, app.getDocUsername());
                            Log.e(CLASS_NAME, app.getPatUsername());
                            if (app.getPatUsername().equalsIgnoreCase(username)) {
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
                                if ((app.getStatus()).equals("confirmed"))
                                    appointmentStatus.setImageResource(R.drawable.tick);
                                else
                                    appointmentStatus.setImageResource(R.drawable.waiting);
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
                                        cancelAppointment(app.getDocName(), app.getDate(), app.getTime(), app.getDocUsername());
                                        View row = (View) v.getParent();
                                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                                        ViewGroup container = ((ViewGroup) row.getParent());
                                        // delete the row and invalidate your view so it gets redrawn
                                        TextView temptextview = (TextView) ((ViewGroup) row).getChildAt(0);
                                        String username = (String) temptextview.getText();

                                        //Delete from Database
                                        app.delete().continueWith(new Continuation<IBMDataObject, Object>() {
                                            @Override
                                            public Object then(Task<IBMDataObject> task) throws Exception {
                                                if (task.isFaulted()) {
                                                    // Handle errors
                                                    Log.e(CLASS_NAME, "Exception: " + task.getError().getMessage());
                                                    return null;
                                                } else {
                                                    Appointments myApp = (Appointments) task.getResult();
                                                    Log.e(CLASS_NAME, myApp.getStatus());
                                                    Log.e(CLASS_NAME, myApp.getDocUsername());
                                                    Log.e(CLASS_NAME, myApp.getPatUsername());

                                                    // Do more work
                                                }
                                                return null;
                                            }
                                        });


                                        container.removeView(row);
                                        container.invalidate();
                                    }
                                });
                                row.addView(dname);
                                row.addView(adate);
                                row.addView(atime);
                                row.addView(appointmentStatus);
                                row.addView(cancelAppointment);

                                ll.addView(row, index);
                                index++;
                            }
                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
    }

        /**
         * Send a notification to all devices whenever the BlueList is modified (create, update, or delete).
         */
    private void cancelAppointment(String docName, String date, String time,String idName) {

        // Initialize and retrieve an instance of the IBM CloudCode service.
        IBMCloudCode.initializeService();
        IBMCloudCode myCloudCodeService = IBMCloudCode.getService();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("name", docName);
            jsonObj.put("date", date);
            jsonObj.put("time", time);
            jsonObj.put("consumerId",idName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

		/*
		 * Call the node.js application hosted in the IBM Cloud Code service
		 * with a POST call, passing in a non-essential JSONObject.
		 * The URI is relative to/appended to the BlueMix context root.
		 */

        myCloudCodeService.post("cancelAppointment", jsonObj).continueWith(new Continuation<IBMHttpResponse, Void>() {

            @Override
            public Void then(Task<IBMHttpResponse> task) throws Exception {
                if (task.isCancelled()) {
                    Log.e(CLASS_NAME, "Exception : Task" + task.isCancelled() + "was cancelled.");
                } else if (task.isFaulted()) {
                    Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                } else {
                    InputStream is = task.getResult().getInputStream();
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(is));
                        String responseString = "";
                        String myString = "";
                        while ((myString = in.readLine()) != null)
                            responseString += myString;

                        in.close();
                        Log.i(CLASS_NAME, "Response Body: " + responseString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(CLASS_NAME, "Response Status from cancelAppointment: " + task.getResult().getHttpResponseCode());
                }
                return null;
            }

        });

    }

        //Access Database
        //for (int i = 0; i <2; i++) {


        //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_appointment_status, menu);
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
