package com.example.prithwishmukherjee.duvidha;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Doctor_Page extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__page);
        RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingBar2);
        ratingbar.setIsIndicator(true);

        TextView textview1 = (TextView) findViewById(R.id.textView18);
        textview1.setTextSize(12);

        TextView textview2 = (TextView) findViewById(R.id.textView19);
        textview2.setTextSize(12);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl1);
        LinearLayout lll = new LinearLayout(Doctor_Page.this);

        lll.setOrientation(LinearLayout.HORIZONTAL);

        Button b1 = new Button(Doctor_Page.this);
        b1.setText("Take Appointment");
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
