package com.example.prithwishmukherjee.duvidha;

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
import com.ibm.mobile.services.data.IBMQuery;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class MainActivity extends ActionBarActivity {

    //Defining global variables here
    SuvidhaApplication svdApplication;
    //ArrayAdapter<Doctor> lvArrayAdapter;
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
