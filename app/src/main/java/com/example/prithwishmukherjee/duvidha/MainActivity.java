package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.List;

import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends ActionBarActivity {

    //Defining global variables here
    SuvidhaApplication svdApplication;
    int flag;
    public static final String CLASS_NAME="MainActivity";

    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Use application class to maintain global state. */
        svdApplication = (SuvidhaApplication) getApplication();
        //itemList = blApplication.getItemList();

        //Code to add new doctor to the database
        /*
        Doctor doc = new Doctor();
        doc.setAddress("LLR A-111, IIT Kharagpur");
        doc.setName("Prithvish Mukerjee");
        doc.setArea("Genitals");
        doc.save().continueWith(new Continuation<IBMDataObject, Void>() {

            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                if (task.isFaulted()) {
                    // Handle errors
                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                    return null;
                } else {
                    Doctor myDoc = (Doctor) task.getResult();
                    Log.e(CLASS_NAME,myDoc.getAddress());
                    Log.e(CLASS_NAME,myDoc.getName());
                    Log.e(CLASS_NAME,myDoc.getArea());

                    // Do more work
                }
                return null;
            }
        });
        */
        /*
        Log.e(CLASS_NAME,"Inside onCreate");
        try {
            //To retrieve from the database
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Doctor> objects = task.getResult();
                        for(Doctor doc:objects){
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME,doc.getArea());
                            Log.e(CLASS_NAME,doc.getAddress());
                        }
                        Log.e(CLASS_NAME,"HERE");
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
        */
        //Add users
        /*
        Users user = new Users();
        user.setName("kediamanav");
        user.setPassword("manav");
        user.setType("P");

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

        user = new Users();
        user.setName("prits");
        user.setPassword("prithwish");
        user.setType("D");

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
        */
    }

    //Log in button
    public void gotoMemberPage(View view) {

        EditText userId = (EditText) findViewById(R.id.userId);
        EditText password = (EditText) findViewById(R.id.password);
        final String username = userId.getText().toString();
        final String userpass = password.getText().toString();

        Log.e(CLASS_NAME, username);
        Log.e(CLASS_NAME, userpass);
        Log.e(CLASS_NAME, "Checking user credentials from database");

        //To retrieve from the database
        Users user = new Users();
        user.setName(username);
        user.setPassword(userpass);
        user.setType("P");
        final Context context = this;

        try {
            IBMQuery<Users> query = IBMQuery.queryForClass(Users.class);
            query.find().continueWith(new Continuation<List<Users>, Void>() {
                @Override
                public Void then(Task<List<Users>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                        Log.e(CLASS_NAME, "Error in retrieving");
                    } else {
                        // do more work
                        List<Users> objects = task.getResult();
                        for (Users newUser : objects) {
                            Log.e(CLASS_NAME, newUser.getName());
                            Log.e(CLASS_NAME, newUser.getPassword());
                            Log.e(CLASS_NAME, newUser.getType());
                            Intent intent;
                            String type = "D";//get from Database
                            if (newUser.getName().equalsIgnoreCase(username) && newUser.getPassword().equals(userpass)) {
                                Log.e(CLASS_NAME, "Login successful");
                                if (type.equals("P"))
                                    intent = new Intent(context, SuvidhaMember.class);
                                else if (type.equals("D"))
                                    intent = new Intent(context, SuvidhaDoctor.class);
                                else
                                    intent = new Intent(context, SuvidhaHospital.class);
                                intent.putExtra(EXTRA_MESSAGE, username);
                                startActivity(intent);
                                return null;
                            }
                        }
                        Log.e(CLASS_NAME, "Login unsuccessful");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        Log.e(CLASS_NAME, "Inside user received");
                        alertDialog.setTitle("Wrong credentials");
                        alertDialog.setMessage("Username/Password is wrong");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // here you can add functions
                            }
                        });
                        AlertDialog dialog = alertDialog.create();
                        alertDialog.show();
                        Log.e(CLASS_NAME, "Inside user received");
                    }
                    return null;
                }
            });
        } catch (IBMDataException e) {
            Log.e(CLASS_NAME, "IBM Exception");
        }
    }

    public void gotoEmergencyPage(View view){
        Intent intent = new Intent(this, EmergencyPage.class);
        startActivity(intent);
    }

}
