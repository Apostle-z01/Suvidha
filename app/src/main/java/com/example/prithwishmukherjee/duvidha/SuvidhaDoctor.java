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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import org.w3c.dom.Text;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class SuvidhaDoctor extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";
    public final static String CLASS_NAME = "SuvidhaDoctor";

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suvidha_doctor);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Get name from database using username
        String name = username;
        TextView textView = (TextView)findViewById(R.id.textView4);
        textView.setText("Welcome Dr." + name);

        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        int rating = 4;
        rb.setRating(rating);
        rb.setIsIndicator(true);
        formTable();
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

    public void viewPendingAppointments(View view)
    {
        Intent intent = new Intent(this,PendingAppointments.class);
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    public void deactivateDocAppointmentTable(View view)
    {
        TextView temp = (TextView)findViewById(R.id.textViewss);
        temp.setVisibility(View.GONE);
        View stt = (View)findViewById(R.id.stline5);
        stt.setVisibility(View.GONE);
        Button t1 = (Button)findViewById(R.id.addAppointment);
        t1.setVisibility(View.GONE);
        TextView b1= (TextView)findViewById(R.id.done);
        b1.setVisibility(View.GONE);


        TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setVisibility(View.GONE);

        Spinner sp1 = (Spinner)findViewById(R.id.dayspinner);
        sp1.setVisibility(View.GONE);
        Spinner time_hour = (Spinner)findViewById(R.id.doctime_hour);
        time_hour.setVisibility(View.GONE);
        Spinner time_min = (Spinner)findViewById(R.id.doctime_min);
        time_min.setVisibility(View.GONE);
        ImageButton tick = (ImageButton)findViewById(R.id.addb);
        tick.setVisibility(View.GONE);
        ImageButton cross = (ImageButton)findViewById(R.id.cancelb);
        cross.setVisibility(View.GONE);

    }

    public void showOptiontoAdd(View view)
    {
        TextView temp = (TextView)findViewById(R.id.textViewss);
        temp.setVisibility(View.VISIBLE);
        View stt = (View)findViewById(R.id.stline5);
        stt.setVisibility(View.VISIBLE);
        Button t1 = (Button)findViewById(R.id.addAppointment);
        t1.setVisibility(View.GONE);

        TextView b1= (TextView)findViewById(R.id.done);
        b1.setVisibility(View.GONE);

        TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setVisibility(View.VISIBLE);

        Spinner sp1 = (Spinner)findViewById(R.id.dayspinner);
        String appDay = sp1.getSelectedItem().toString();
        Spinner time_hour = (Spinner)findViewById(R.id.doctime_hour);
        time_hour.setVisibility(View.VISIBLE);
        Spinner time_min = (Spinner)findViewById(R.id.doctime_min);
        time_min.setVisibility(View.VISIBLE);
        sp1.setVisibility(View.VISIBLE);
        ImageButton tick = (ImageButton)findViewById(R.id.addb);
        tick.setVisibility(View.VISIBLE);
        ImageButton cross = (ImageButton)findViewById(R.id.cancelb);
        cross.setVisibility(View.VISIBLE);

    }

    public void removeOptiontoadd(View view)
    {
        TextView temp = (TextView)findViewById(R.id.textViewss);
        temp.setVisibility(View.VISIBLE);
        View stt = (View)findViewById(R.id.stline5);
        stt.setVisibility(View.VISIBLE);
        Button t1 = (Button)findViewById(R.id.addAppointment);
        t1.setVisibility(View.VISIBLE);

        TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setVisibility(View.VISIBLE);

        TextView b1= (TextView)findViewById(R.id.done);
        b1.setVisibility(View.VISIBLE);

        Spinner sp1 = (Spinner)findViewById(R.id.dayspinner);
        sp1.setVisibility(View.GONE);
        Spinner time_hour = (Spinner)findViewById(R.id.doctime_hour);
        time_hour.setVisibility(View.GONE);
        Spinner time_min = (Spinner)findViewById(R.id.doctime_min);
        time_min.setVisibility(View.GONE);
        ImageButton tick = (ImageButton)findViewById(R.id.addb);
        tick.setVisibility(View.GONE);
        ImageButton cross = (ImageButton)findViewById(R.id.cancelb);
        cross.setVisibility(View.GONE);

    }

    public void addAppointmentDoc(View view)
    {
        Spinner sp1 = (Spinner)findViewById(R.id.dayspinner);
        String appDay = sp1.getSelectedItem().toString();
        Spinner time_hour = (Spinner)findViewById(R.id.doctime_hour);
        String sp_hour=time_hour.getSelectedItem().toString();
        time_hour.setVisibility(View.GONE);
        Spinner time_min = (Spinner)findViewById(R.id.doctime_min);
        String sp_min=time_min.getSelectedItem().toString();
        time_min.setVisibility(View.GONE);
        sp1.setVisibility(View.GONE);
        String appTime=sp_hour+":"+sp_min;

        ImageButton tick = (ImageButton)findViewById(R.id.addb);
        tick.setVisibility(View.GONE);
        ImageButton cross = (ImageButton)findViewById(R.id.cancelb);
        cross.setVisibility(View.GONE);

        TextView b1= (TextView)findViewById(R.id.done);
        b1.setVisibility(View.VISIBLE);

        TextView temp = (TextView)findViewById(R.id.textViewss);
        temp.setVisibility(View.VISIBLE);
        View stt = (View)findViewById(R.id.stline5);
        stt.setVisibility(View.VISIBLE);
        Button t1 = (Button)findViewById(R.id.addAppointment);
        t1.setVisibility(View.VISIBLE);
        TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setVisibility(View.VISIBLE);

        //Add to database using appDay,appTime
        Doctor_Time doc_time = new Doctor_Time();
        doc_time.setDay(appDay);
        doc_time.setTime(appTime);
        doc_time.setUsername(username);
        doc_time.save().continueWith(new Continuation<IBMDataObject, Void>() {

            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                if (task.isFaulted()) {
                    // Handle errors
                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                    return null;
                } else {
                    Doctor_Time myDoc = (Doctor_Time) task.getResult();
                    Log.e(CLASS_NAME,myDoc.getTime());
                    Log.e(CLASS_NAME,myDoc.getDay());
                    Log.e(CLASS_NAME,myDoc.getUsername());

                    // Do more work
                }
                return null;
            }
        });
        formTable();
    }

    public void updateDetails(View view)
    {
    //    Intent intent = new Intent(this,UpdateDetails.class);
    //    intent.putExtra(EXTRA_MESSAGE,username);
    //    startActivity(intent);
        //pass username to forum page

        TextView temp = (TextView)findViewById(R.id.textViewss);
        temp.setVisibility(View.VISIBLE);
        View stt = (View)findViewById(R.id.stline5);
        stt.setVisibility(View.VISIBLE);
        Button t1 = (Button)findViewById(R.id.addAppointment);
        t1.setVisibility(View.VISIBLE);
        TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setVisibility(View.VISIBLE);

        TextView b1= (TextView)findViewById(R.id.done);
        b1.setVisibility(View.VISIBLE);

        Spinner sp1 = (Spinner)findViewById(R.id.dayspinner);
        String appDay = sp1.getSelectedItem().toString();
        Spinner time_hour = (Spinner)findViewById(R.id.doctime_hour);
        time_hour.setVisibility(View.GONE);
        Spinner time_min = (Spinner)findViewById(R.id.doctime_min);
        time_min.setVisibility(View.GONE);
        sp1.setVisibility(View.GONE);
        ImageButton tick = (ImageButton)findViewById(R.id.addb);
        tick.setVisibility(View.GONE);
        ImageButton cross = (ImageButton)findViewById(R.id.cancelb);
        cross.setVisibility(View.GONE);
        //        intent.putExtra(EXTRA_MESSAGE,username);
        //        startActivity(intent);
    }

    public void formTable(){
        final TableLayout ll = (TableLayout)findViewById(R.id.docAppointmentTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);
        ll.removeAllViews();

        final Context context = this;
        try {
            //To retrieve from the database
            IBMQuery<Doctor_Time> query = IBMQuery.queryForClass(Doctor_Time.class);

            query.find().continueWith(new Continuation<List<Doctor_Time>, Void>() {

                @Override
                public Void then(Task<List<Doctor_Time>> task) throws Exception {
                    int index=0;
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Doctor_Time> objects = task.getResult();
                        for(final Doctor_Time doc_time:objects){
                            Log.e(CLASS_NAME, doc_time.getTime());
                            Log.e(CLASS_NAME, doc_time.getDay());

                            TableRow row = new TableRow(context);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);

                            //Get from Database
                            TextView adate = new TextView(context);
                            adate.setText(doc_time.getDay());

                            TextView atime = new TextView(context);
                            atime.setText(doc_time.getTime());

                            row.setBackgroundColor(Color.WHITE);

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
                                    doc_time.delete().continueWith(new Continuation<IBMDataObject, Object>() {
                                        @Override
                                        public Object then(Task<IBMDataObject> task) throws Exception {
                                        if (task.isFaulted()) {
                                            // Handle errors
                                            Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                                            return null;
                                        } else {
                                            Doctor_Time myApp = (Doctor_Time) task.getResult();
                                            Log.e(CLASS_NAME,myApp.getTime());
                                            Log.e(CLASS_NAME,myApp.getDay());

                                        }
                                        return null;
                                        }
                                    });

                                    container.removeView(row);
                                    container.invalidate();
                                }
                            });
                            row.addView(adate);
                            row.addView(atime);
                            row.addView(cancelAppointment);

                            ll.addView(row, index);
                            index++;
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
