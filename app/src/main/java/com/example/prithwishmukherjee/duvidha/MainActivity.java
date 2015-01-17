package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;
import com.ibm.mobile.services.push.IBMPush;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class MainActivity extends ActionBarActivity {

    //Defining global variables here
    SuvidhaApplication svdApplication;
    private static final String deviceAlias = "TargetDevice";
    private static final String consumerID = "MBaaSListApp";
    int flag;
    public static final String CLASS_NAME="MainActivity";
    int click = 1;
    public final static String EXTRA_MESSAGE = "com.example.prithwishmukherjee.duvidha.MESSAGE";
    public final static String EXTRA_MESSAGE_NAME = "com.example.prithwishmukherjee.duvidha.MESSAGE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Use application class to maintain global state. */
        svdApplication = (SuvidhaApplication) getApplication();
        //itemList = blApplication.getItemList();

        final Context context = this;

        ImageView emergency = (ImageView) findViewById(R.id.imageView);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click == 2){
                    Intent intent = new Intent(context, EmergencyPage.class);
                    startActivity(intent);
                    click = 1;
                }
                else {
                    click++;
                }
            }
        });

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
    public void gotoMemberPage(View view){

        EditText userId = (EditText)findViewById(R.id.userId);
        EditText password = (EditText)findViewById(R.id.password);
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
                            String type = newUser.getType();//get from Database
                            if (newUser.getName().equalsIgnoreCase(username) && newUser.getPassword().equals(userpass)) {
                                Log.e(CLASS_NAME, "Login successful");

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
                                //svdApplication.push.subscribe(username);

                                if (type.equals("P"))
                                    intent = new Intent(context, SuvidhaMember.class);
                                else if (type.equals("D"))
                                    intent = new Intent(context, SuvidhaDoctor.class);
                                else
                                    intent = new Intent(context, SuvidhaHospital.class);
                                intent.putExtra(EXTRA_MESSAGE, username);
                                intent.putExtra(EXTRA_MESSAGE_NAME,newUser.getName());
                                startActivity(intent);
                                finish();
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

//    public void gotoEmergencyPage(View view){
//        Intent intent = new Intent(this, EmergencyPage.class);
//        startActivity(intent);
//    }

    public void gotoRegistrationPage(View view){
        Intent intent = new Intent(this, RegistrationPage.class);
        //yyy
        startActivity(intent);
    }

}
