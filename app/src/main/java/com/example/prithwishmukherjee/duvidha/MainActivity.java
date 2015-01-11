package com.example.prithwishmukherjee.duvidha;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Log in button
    public void gotoMemberPage(View view){

        EditText userId = (EditText)findViewById(R.id.userId);
        EditText password = (EditText)findViewById(R.id.password);
        String username = userId.getText().toString();

        Intent intent;

        String type = "D";//get from Database

        if(type.equals("P"))
        intent = new Intent(this, SuvidhaMember.class);
        else if(type.equals("D"))
        intent = new Intent(this, SuvidhaDoctor.class);
        else intent = new Intent(this, SuvidhaHospital.class);
        //Check through database

        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    public void gotoEmergencyPage(View view){
        Intent intent = new Intent(this, EmergencyPage.class);
        startActivity(intent);
    }
}
