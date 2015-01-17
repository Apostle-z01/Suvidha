package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class DoctorReview extends ActionBarActivity {

    public static final String CLASS_NAME = "DoctorReview";
    String username;
    String doc_name = new String();
    String doc_specialization = new String();
    String lat = new String();
    String lon = new String();
    String pat_user_name = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_review);
        Intent intent = getIntent();
        String message = intent.getStringExtra("review");
        String[] doc = message.split("#", 6);

        doc_name = doc[0];
        username = doc[1];
        doc_specialization = doc[2];
        lat = doc[3];
        lon = doc[4];
        pat_user_name = doc[5];

        final List<Reviews> reviews = new ArrayList<Reviews>();
        reviews.clear();

        final Context context = this;

        Button back = (Button) findViewById(R.id.goBackReview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Doctor_Page.class);
                intent.putExtra("doctor", doc_name + "#" + username + "#" + doc_specialization + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                startActivity(intent);
            }
        });

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
                        tv.setTextColor(Color.BLACK);
                    }

                    if(count>=2){
                        TextView tv = (TextView) findViewById(R.id.review2);
                        tv.setText(reviews.get(1).getReview());
                        tv.setTextColor(Color.BLACK);
                    }

                    if(count>=3){
                        TextView tv = (TextView) findViewById(R.id.review3);
                        tv.setText(reviews.get(2).getReview());
                        tv.setTextColor(Color.BLACK);
                    }

                    if(count>=4){
                        TextView tv = (TextView) findViewById(R.id.review4);
                        tv.setText(reviews.get(3).getReview());
                        tv.setTextColor(Color.BLACK);
                    }

                    if(count>=5){
                        TextView tv = (TextView) findViewById(R.id.review5);
                        tv.setText(reviews.get(4).getReview());
                        tv.setTextColor(Color.BLACK);
                    }

                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " +error.getMessage());
        }
    }
}
