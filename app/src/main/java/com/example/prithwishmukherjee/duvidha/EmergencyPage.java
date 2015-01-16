package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.maps.model.LatLng;


public class EmergencyPage extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";
    LocationManager myLocationManager;
    String PROVIDER = LocationManager.GPS_PROVIDER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_page);

        myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //get last known location, if available
        Location location = myLocationManager.getLastKnownLocation(PROVIDER);
        Globals.latitude = location.getLatitude();
        Globals.longitude = location.getLongitude();

        //final RadioButton current = (RadioButton)findViewById(R.id.radioButton);
        //final RadioButton home2 = (RadioButton)findViewById(R.id.radioButton2);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_page, menu);
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

    public void genMap(View view){
        Globals.address = (EditText)findViewById(R.id.enterAddress);
        Intent intent = new Intent(this,genMap.class);
        startActivity(intent);
    }


    public void activateAddressEntry(View view)
    {
        EditText editText = (EditText)findViewById(R.id.enterAddress);
        editText.setText(null);
        editText.setVisibility(View.VISIBLE);
        editText.setEnabled(true);
    }

    public void deactivateAddressEntry(View view)
    {
        //get last known location, if available
        Location location = myLocationManager.getLastKnownLocation(PROVIDER);
        Globals.latitude = location.getLatitude();
        Globals.longitude = location.getLongitude();

        EditText editText = (EditText)findViewById(R.id.enterAddress);
        editText.setText(null);
        editText.setVisibility(View.GONE);
        editText.setEnabled(false);
    }

    public void gotoAmbulance(View view)
    {
        Intent intent = new Intent(this, Ambulance.class);
        EditText editText = (EditText)findViewById(R.id.enterAddress);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }
}
