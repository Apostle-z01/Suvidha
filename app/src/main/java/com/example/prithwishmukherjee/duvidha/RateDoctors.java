package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Rating;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class RateDoctors extends ActionBarActivity implements OnItemSelectedListener{

    Spinner spinner;
    String username;
    String patName;
    RatingBar ratingBar2;
    EditText editReview;
    Button buttonPost;
    TextView textDcotorName;
    public static final String CLASS_NAME="RateDoctors";
    final List<Appointments> appList = new ArrayList<Appointments>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_doctors);
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        patName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NAME);

        spinner = (Spinner)findViewById(R.id.spinner3);
        ratingBar2 = (RatingBar)findViewById(R.id.ratingBar2);
        editReview = (EditText)findViewById(R.id.editReview);
        buttonPost = (Button)findViewById(R.id.buttonPost);
        textDcotorName = (TextView)findViewById(R.id.textDoctorName);
        //  db connection and populate Globals.state with the names

        ratingBar2.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar2, float rating,
                                        boolean fromUser) {
                if (ratingBar2.getRating() < 1)
                    ratingBar2.setRating(1);
            }
        });

        //  connect to database and invoke populate

        populate();

        spinner.setOnItemSelectedListener(this);
    }

    public void populate(){

        //  populate Globals.state here
        final Context context = this;
        try {
            //To retrieve from the database
            IBMQuery<Appointments> query = IBMQuery.queryForClass(Appointments.class);
            query.find().continueWith(new Continuation<List<Appointments>, Void>() {

                @Override
                public Void then(Task<List<Appointments>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Appointments> objects = task.getResult();
                        for(final Appointments app:objects){
                            Log.e(CLASS_NAME, app.getDocUsername());
                            Log.e(CLASS_NAME, app.getPatUsername());
                            Log.e(CLASS_NAME, app.getStatus());

                            if(app.getPatUsername().equalsIgnoreCase(username) && app.getStatus().equalsIgnoreCase("confirmed")){
                                //Add the appointments here, and the patients username
                                Log.e(CLASS_NAME, "Patient visited this doctor");
                                Log.e(CLASS_NAME,app.getDocName());
                                if(!appList.contains(app)) {
                                    appList.add(app);
                                }
                            }
                        }
                    }
                    Globals.state = new String[appList.size()+1];
                    Globals.state[0] = "Select Doctor";
                    int index=1;
                    for(Appointments app:appList) {
                        Globals.state[index] = "Dr. "+ appList.get(index-1).getDocName();
                        index++;
                    }
                    Log.e(CLASS_NAME,"Populate done");

                    ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, Globals.state);
                    adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter_state);
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        if(!selState.equals("Select Doctor")) {
            ratingBar2.setRating(1);
            ratingBar2.setVisibility(View.VISIBLE);
            editReview.setVisibility(View.VISIBLE);
            buttonPost.setVisibility(View.VISIBLE);
            textDcotorName.setText(selState);
            editReview.setText("");
            editReview.setHint("Your review here");
        }
        else{
            textDcotorName.setText("");
            ratingBar2.setVisibility(View.INVISIBLE);
            editReview.setVisibility(View.INVISIBLE);
            buttonPost.setVisibility(View.INVISIBLE);
        }
    }

    public void submit(View view){
        Log.e(CLASS_NAME,"Posting data");
        postReview();
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }

    public void postReview(){
        String reviewMessage = editReview.getText().toString();

        //Add to hospital_Doctor
        Reviews rev = new Reviews();
        rev.setPatUsername(username);
        rev.setPatName(patName);
        rev.setDocName(appList.get(spinner.getSelectedItemPosition() - 1).getDocName());
        final String docUsername = appList.get(spinner.getSelectedItemPosition()-1).getDocUsername();
        rev.setDocUsername(docUsername);
        rev.setReview(reviewMessage);

        rev.save().continueWith(new Continuation<IBMDataObject, Void>() {

            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                if (task.isFaulted()) {
                    // Handle errors
                    Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                    return null;
                } else {
                    Reviews review = (Reviews) task.getResult();
                    Log.e(CLASS_NAME,review.getDocName());
                    Log.e(CLASS_NAME,review.getReview());

                    // Do more work
                }
                return null;
            }
        });

        final Context context = this;
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
                        for(final Doctor doc:objects){
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME, doc.getUsername());
                            Log.e(CLASS_NAME, doc.getArea());

                            if(doc.getUsername().equalsIgnoreCase(docUsername)){
                                //Add the appointments here, and the patients username
                                float rating = Float.parseFloat(doc.getRating());
                                float numRating = Float.parseFloat(doc.getNumRating());
                                rating = ratingBar2.getRating()+rating*numRating;
                                numRating+=1;
                                rating/=numRating;
                                doc.setNumRating(Float.toString(numRating));
                                doc.setRating(Float.toString(rating));

                                doc.save().continueWith(new Continuation<IBMDataObject, Void>() {

                                    @Override
                                    public Void then(Task<IBMDataObject> task) throws Exception {
                                        if (task.isFaulted()) {
                                            // Handle errors
                                            Log.e(CLASS_NAME,"Exception: "+task.getError().getMessage());
                                            return null;
                                        } else {
                                            Doctor doc1 = (Doctor) task.getResult();
                                            Log.e(CLASS_NAME,doc1.getRating());
                                            Log.e(CLASS_NAME,doc1.getNumRating());
                                            Log.e(CLASS_NAME,doc1.getName());
                                            // Do more work
                                        }

                                        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(context);
                                        AlertDialog dialog = alertDialog.create();
                                        alertDialog.setTitle("Reviewed!");
                                        alertDialog.setMessage("Your review has been submitted");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                        dialog = alertDialog.create();
                                        alertDialog.show();
                                        return null;
                                    }
                                });
                            }
                        }
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
    }
}
