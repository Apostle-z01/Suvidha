package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.http.IBMHttpResponse;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import org.json.JSONException;
import org.json.JSONObject;

import bolts.Continuation;
import bolts.Task;

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
    public static final String CLASS_NAME="EmergencyPage";
    LocationManager myLocationManager;
    String PROVIDER = LocationManager.GPS_PROVIDER;
    ArrayList<Hospital> hospitals = new ArrayList<Hospital>();


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
        final double lat = Globals.latitude;
        final double lon = Globals.longitude;

        //To retrieve from the database

        try {
            IBMQuery<Hospital> query = IBMQuery.queryForClass(Hospital.class);
            query.find().continueWith(new Continuation<List<Hospital>, Void>() {

              @Override
              public Void then(Task<List<Hospital>> task) throws Exception {
                  if (task.isFaulted()) {
                      // Handle errors
                      System.out.println("Entered");
                  } else {
                      // do more work
                      System.out.println("Here");
                      final List<Hospital> objects = task.getResult();
                      System.out.println("After here");
                      for (Hospital hos : objects) {
                          System.out.println("Entered here");
                          Log.e(CLASS_NAME, hos.getName());
                          Log.e(CLASS_NAME, hos.getUsername());
                          Log.e(CLASS_NAME, hos.getAddress());
                          hospitals.add(hos);
                      }
                      Log.e(CLASS_NAME, "HERE");
                  }

                  Collections.sort(hospitals, new Comparator<Hospital>() {
                      public int compare(Hospital d1, Hospital d2) {
                          float dist1 = distFrom((float) Double.parseDouble(d1.getLat()), (float) Double.parseDouble(d1.getLon()), (float) lat, (float) lon);
                          float dist2 = distFrom((float) Double.parseDouble(d2.getLat()), (float) Double.parseDouble(d2.getLon()), (float) lat, (float) lon);
                          if (dist1 > dist2) {
                              return -1;
                          }
                          else if (dist1 < dist2) {
                              return 1;
                          } else {
                              return 0;
                          }
                      }
                  });
                  sendHospitalNotification(Globals.add,hospitals.get(0).getUsername());
                  return null;
              }
          }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " + error.getMessage());
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Hospital Notified");
        alertDialog.setMessage("Hospital is notified, please hold on.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        AlertDialog dialog = alertDialog.create();
        alertDialog.show();


        /*Intent intent = new Intent(this, Ambulance.class);
        EditText editText = (EditText)findViewById(R.id.enterAddress);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);*/
    }

    /**
     * Send a notification to all devices whenever the BlueList is modified (create, update, or delete).
     */
    private void sendHospitalNotification(String patAddress, String idName) {

        // Initialize and retrieve an instance of the IBM CloudCode service.
        IBMCloudCode.initializeService();
        IBMCloudCode myCloudCodeService = IBMCloudCode.getService();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("address", patAddress);
            jsonObj.put("consumerId",idName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

		/*
		 * Call the node.js application hosted in the IBM Cloud Code service
		 * with a POST call, passing in a non-essential JSONObject.
		 * The URI is relative to/appended to the BlueMix context root.
		 */

        myCloudCodeService.post("hospitalNotification", jsonObj).continueWith(new Continuation<IBMHttpResponse, Void>() {

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
                    Log.i(CLASS_NAME, "Response Status from hospitalNotification: " + task.getResult().getHttpResponseCode());
                }
                return null;
            }

        });

    }



    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371; //kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
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
