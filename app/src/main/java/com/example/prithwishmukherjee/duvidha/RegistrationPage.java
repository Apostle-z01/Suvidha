package com.example.prithwishmukherjee.duvidha;

import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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


public class RegistrationPage extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        final RadioButton docType = (RadioButton)findViewById(R.id.regDoctor);
        final RadioButton patientType = (RadioButton)findViewById(R.id.regPatient);
        final RadioButton hospitalType = (RadioButton)findViewById(R.id.regHospital);

        String address1 = "1600 Amphitheatre Parkway, Mountain View, CA";
        ((EditText)findViewById(R.id.regAddress)).setText(address1);

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
        //88
    }

    public void submitRegForm(View view)
    {
        //Entry into database;
        // test address
        EditText ttt = (EditText)findViewById(R.id.regAddress);
        String address = String.valueOf(ttt.getText());
        address = address.replace(' ','+');
        //  parse address
        //onCompletion("async");
        //address = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";

        new HttpAsyncTask().execute("http://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false");

    }

    //---------------------------------------------------------//
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

                double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                //textview_lati.setText(String.valueOf(lat));
                //textview_long.setText(String.valueOf(lng));

                //  String.valueOf(lat) - latitude
                //  String.valueOf(lng) - longitude
                //  database entry

                onCompletion("latitude:" + lat + ",longitude:" + lng);
                Log.d("latitude", "" + lat);
                Log.d("longitude", "" + lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCompletion(String str){

        Intent intent = new Intent(this,SuccesfulRegistration.class);
        intent.putExtra(EXTRA_MESSAGE,str);
        startActivity(intent);

    }
}
