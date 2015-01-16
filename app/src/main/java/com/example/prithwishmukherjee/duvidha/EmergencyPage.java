package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


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
        new HttpAsyncTask().execute("https://maps.googleapis.com/maps/api/geocode/json?latlng="+Globals.latitude+","+Globals.longitude);

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
        new HttpAsyncTask().execute("https://maps.googleapis.com/maps/api/geocode/json?latlng="+Globals.latitude+","+Globals.longitude);

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
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(result);
                String address = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getString("formatted_address");
                Globals.add = address;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
