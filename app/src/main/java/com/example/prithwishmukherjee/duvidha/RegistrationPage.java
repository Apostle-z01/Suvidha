package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.http.IBMHttpResponse;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.push.IBMPush;

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

import bolts.Continuation;
import bolts.Task;


public class RegistrationPage extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";
    public static final String CLASS_NAME = "RegistrationPage";
    private static final String deviceAlias = "TargetDevice";
    private static final String consumerID = "MBaaSListApp";
    SuvidhaApplication svdApplication;
    String username;
    String password;
    String address;
    String onlineApp;
    String regNum;
    String name;
    String type;
    String area;
    String fees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        svdApplication = (SuvidhaApplication) getApplication();


        String address1 = "1600 Amphitheatre Parkway, Mountain View, CA";
        //((TextView)findViewById(R.id.regAddress)).setText(address1);

    }

    public void genMap(View view){
        Globals.address = (EditText)findViewById(R.id.regAddress);
        Intent intent = new Intent(this,genMap.class);
        startActivity(intent);

    }

    public void activateRegNo(View view)
    {
        EditText editText = (EditText)findViewById(R.id.regRegistrationNo);
        EditText editText1 = (EditText)findViewById(R.id.OnlineApp);
        RadioGroup r1 = (RadioGroup)findViewById(R.id.regOnlineApp);
        EditText editText22 = (EditText)findViewById(R.id.Specialisation);
        EditText editText33 = (EditText)findViewById(R.id.Fees);
        editText.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);
        editText22.setVisibility(View.VISIBLE);
        editText33.setVisibility(View.VISIBLE);
        Spinner sp1 = (Spinner)findViewById(R.id.specialisationSpinner);
        sp1.setVisibility(View.VISIBLE);
    }

    public void deactivateRegNo(View view)
    {
        EditText editText = (EditText)findViewById(R.id.regRegistrationNo);
        EditText editText1 = (EditText)findViewById(R.id.OnlineApp);
        RadioGroup r1 = (RadioGroup)findViewById(R.id.regOnlineApp);
        EditText editText22 = (EditText)findViewById(R.id.Specialisation);
        EditText editText33 = (EditText)findViewById(R.id.Fees);
        editText.setVisibility(View.GONE);
        editText1.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        editText22.setVisibility(View.GONE);
        editText33.setVisibility(View.GONE);
        Spinner sp1 = (Spinner)findViewById(R.id.specialisationSpinner);
        sp1.setVisibility(View.GONE);

    }

    public void partialActivate(View view)
    {
        EditText editText = (EditText)findViewById(R.id.regRegistrationNo);
        EditText editText1 = (EditText)findViewById(R.id.OnlineApp);
        RadioGroup r1 = (RadioGroup)findViewById(R.id.regOnlineApp);
        EditText editText22 = (EditText)findViewById(R.id.Specialisation);
        EditText editText33 = (EditText)findViewById(R.id.Fees);
        editText.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        editText22.setVisibility(View.GONE);
        editText33.setVisibility(View.GONE);
        Spinner sp1 = (Spinner)findViewById(R.id.specialisationSpinner);
        sp1.setVisibility(View.GONE);
    }

    public void submitRegForm(View view) {
        //Entry into database;
        // test address
        EditText etAddress = (EditText) findViewById(R.id.regAddress);
        address = String.valueOf(etAddress.getText());
        address = address.replace(' ', '+');
        //  parse address
        //onCompletion("async");
        //address = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
        EditText etUsername = (EditText) findViewById(R.id.regUsername);
        EditText etPassword = (EditText) findViewById(R.id.regPassword);
        EditText etName = (EditText) findViewById(R.id.regName);

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        name = etName.getText().toString();

        System.out.println("HERE HERE");
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.regType);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == R.id.regHospital){
            type = "H";
            EditText etReg = (EditText) findViewById(R.id.regRegistrationNo);
            regNum = etReg.getText().toString();

            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.regOnlineApp);
            int id1 = radioGroup1.getCheckedRadioButtonId();

            if(id1 == R.id.regDocOnlineAppYes)
                onlineApp = "yes";
            else
                onlineApp = "no";
        }
        else if(id == R.id.regDoctor) {
            type = "D";
            EditText etReg = (EditText) findViewById(R.id.regRegistrationNo);
            regNum = etReg.getText().toString();
            EditText etFees = (EditText) findViewById(R.id.Fees);
            fees = etFees.getText().toString();

            Spinner sp1 = (Spinner)findViewById(R.id.specialisationSpinner);
            area = sp1.getSelectedItem().toString();

            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.regOnlineApp);
            int id1 = radioGroup1.getCheckedRadioButtonId();

            if(id1 == R.id.regDocOnlineAppNo)
                onlineApp = "yes";
            else
                onlineApp = "no";
        }
        else
            type = "P";

        System.out.println(username);
        System.out.println(password);
        System.out.println(name);
        System.out.println(type);
        System.out.println(address);
        if(!type.equalsIgnoreCase("P")){
            System.out.println(onlineApp);
            System.out.println(regNum);
        }

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

                System.out.println("latitude:" + lat + ",longitude:" + lng);
                onCompletion(Double.toString(lat),Double.toString(lng));
                Log.d("latitude", "" + lat);
                Log.d("longitude", "" + lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCompletion(String lat,String lon){
        //Create new user
        Users user = new Users();
        user.setName(username);
        user.setPassword(password);
        user.setType(type);

        user.save().continueWith(new Continuation<IBMDataObject, Void>() {

            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                if (task.isFaulted()) {
                    // Handle errors
                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                    return null;
                } else {
                    Users myUser = (Users) task.getResult();
                    Log.e(CLASS_NAME,myUser.getName());
                    Log.e(CLASS_NAME,myUser.getPassword());
                    Log.e(CLASS_NAME,myUser.getType());

                    // Do more work
                }
                return null;
            }
        });

        if(type.equalsIgnoreCase("P")){
            Patient pat = new Patient();
            pat.setName(name);
            pat.setUsername(username);
            pat.setAddress(address);
            pat.setLat(lat);
            pat.setLon(lon);

            pat.save().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                        return null;
                    } else {
                        Patient myPat = (Patient) task.getResult();
                        Log.e(CLASS_NAME,myPat.getName());
                        // Do more work
                    }
                    return null;
                }
            });
        }
        else if(type.equalsIgnoreCase("D")){
            //Doctor
            Doctor doc = new Doctor();
            doc.setName(name);
            doc.setUsername(username);
            doc.setAddress(address);
            doc.setLat(lat);
            doc.setLon(lon);
            doc.setRegNum(regNum);
            doc.setOnlineAppointment(onlineApp);
            doc.setArea(area);
            doc.setFees(fees);

            doc.save().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                        return null;
                    } else {
                        Doctor myDoc = (Doctor) task.getResult();
                        Log.e(CLASS_NAME,myDoc.getName());
                        // Do more work
                    }
                    return null;
                }
            });
        }
        else{
            //Hospital
            Hospital hos = new Hospital();
            hos.setName(name);
            hos.setUsername(username);
            hos.setAddress(address);
            hos.setLat(lat);
            hos.setLon(lon);
            hos.setRegNum(regNum);
            hos.setOnlineAppointment(onlineApp);

            hos.save().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                        return null;
                    } else {
                        Hospital myHos = (Hospital) task.getResult();
                        Log.e(CLASS_NAME,myHos.getName());
                        // Do more work
                    }
                    return null;
                }
            });
        }

        //Initialize push
        IBMPush.initializeService();
        svdApplication.push = IBMPush.getService();
        svdApplication.push.register(deviceAlias, username).continueWith(new Continuation<String, Void>() {

            @Override
            public Void then(Task<String> task) throws Exception {
                if (task.isCancelled()) {
                    Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                } else if (task.isFaulted()) {
                    Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                } else {
                    Log.d(CLASS_NAME, "Device Successfully Registered");
                }

                return null;
            }

        });

        //Create new tag
        //createTag(username);
        //svdApplication.push.subscribe(username);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Succesful Registration");
        alertDialog.setMessage("You have been successfully registered");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               // here you can add functions
                finish();
            }
        });
        AlertDialog dialog = alertDialog.create();
        alertDialog.show();

//        Intent intent = new Intent(this,SuccesfulRegistration.class);
//        intent.putExtra(EXTRA_MESSAGE,str);
//        startActivity(intent);
    }

    /**
     * Send a notification to all devices whenever the BlueList is modified (create, update, or delete).
     */
    private void createTag(String tagName) {

        // Initialize and retrieve an instance of the IBM CloudCode service.
        IBMCloudCode.initializeService();
        IBMCloudCode myCloudCodeService = IBMCloudCode.getService();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("name", tagName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myCloudCodeService.post("createTag", jsonObj).continueWith(new Continuation<IBMHttpResponse, Void>() {

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
                    Log.i(CLASS_NAME, "Response Status from createTag: " + task.getResult().getHttpResponseCode());
                }
                return null;
            }

        });

    }

}
