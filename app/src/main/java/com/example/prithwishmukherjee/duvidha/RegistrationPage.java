package com.example.prithwishmukherjee.duvidha;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class RegistrationPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        final RadioButton docType = (RadioButton)findViewById(R.id.regDoctor);
        final RadioButton patientType = (RadioButton)findViewById(R.id.regPatient);
        final RadioButton hospitalType = (RadioButton)findViewById(R.id.regHospital);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration_page, menu);
        return true;////
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

    public void activateRegNo(View view)
    {
        EditText editText = (EditText)findViewById(R.id.regRegistrationNo);
        EditText editText1 = (EditText)findViewById(R.id.OnlineApp);
        RadioGroup r1 = (RadioGroup)findViewById(R.id.regOnlineApp);
        editText.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);
    }

    public void deactivateRegNo(View view)
    {
        EditText editText = (EditText)findViewById(R.id.regRegistrationNo);
        EditText editText1 = (EditText)findViewById(R.id.OnlineApp);
        RadioGroup r1 = (RadioGroup)findViewById(R.id.regOnlineApp);
        r1.setVisibility(View.GONE);
        editText1.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
    }

    public void register(View view)
    {
        //Entry into database;
    }
}
