package com.example.prithwishmukherjee.duvidha;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class DoctorReview extends ActionBarActivity {

    public static final String CLASS_NAME = "DoctorReview";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_review);
        Intent intent = getIntent();
        //username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        username="kedia";
        final List<Reviews> reviews = new ArrayList<Reviews>();
        reviews.clear();
        try{
            IBMQuery<Reviews> query = IBMQuery.queryForClass(Reviews.class);
            query.find().continueWith(new Continuation<List<Reviews>, Void>() {

                @Override
                public Void then(Task<List<Reviews>> task) throws Exception {
                    if (task.isFaulted()) {
                        // Handle errors
                    } else {
                        // do more work
                        List<Reviews> objects = task.getResult();
                        for(final Reviews rev:objects){
                            Log.e(CLASS_NAME, rev.getPatUsername());
                            Log.e(CLASS_NAME, rev.getReview());
                            if(rev.getDocUsername().equalsIgnoreCase(username)){
                                reviews.add(rev);
                            }
                        }
                    }
                    int count=reviews.size();
                    if(count>=1){
                        TextView tv = (TextView) findViewById(R.id.review1);
                        tv.setText(reviews.get(0).getReview());
                    }

                    if(count>=2){
                        TextView tv = (TextView) findViewById(R.id.review2);
                        tv.setText(reviews.get(1).getReview());
                    }

                    if(count>=3){
                        TextView tv = (TextView) findViewById(R.id.review3);
                        tv.setText(reviews.get(2).getReview());
                    }

                    if(count>=4){
                        TextView tv = (TextView) findViewById(R.id.review4);
                        tv.setText(reviews.get(3).getReview());
                    }

                    if(count>=5){
                        TextView tv = (TextView) findViewById(R.id.review5);
                        tv.setText(reviews.get(4).getReview());
                    }

                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
    }
}
