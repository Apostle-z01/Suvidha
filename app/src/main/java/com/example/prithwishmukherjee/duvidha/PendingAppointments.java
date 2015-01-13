package com.example.prithwishmukherjee.duvidha;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import java.sql.*;


public class PendingAppointments extends ActionBarActivity {

    TableLayout maintable;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(SuvidhaDoctor.EXTRA_MESSAGE);
        setContentView(R.layout.activity_existing_appointments);


        Date date = new Date(2015-1900, 2-1, 1);
        Time time = new Time(20, 30, 0);

        TableLayout ll = (TableLayout)findViewById(R.id.mainTable);
        ll.setStretchAllColumns(true);
        ll.setVerticalScrollBarEnabled(true);
        //Access Database
        for (int i = 0; i <2; i++) {

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            //Get from Database
            TextView pname = new TextView(this);
            pname.setText("Patient-"+i);

            TextView adate = new TextView(this);
            adate.setText(date.toString());

            TextView atime = new TextView(this);
            atime.setText(time.getHours()+ ":" + time.getMinutes());

            ImageButton acceptAppointment = new ImageButton(this);
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
                    //Add to Database

                    container.removeView(row);
                    container.invalidate();
                }
            });


            ImageButton cancelAppointment = new ImageButton(this);
            cancelAppointment.setImageResource(R.drawable.plus);
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

            row.setBackgroundColor(Color.WHITE);

            row.addView(pname);row.addView(adate);row.addView(atime);row.addView(acceptAppointment);row.addView(cancelAppointment);
            ll.addView(row, i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_existing_appointments, menu);
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
