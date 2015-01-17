package com.example.prithwishmukherjee.duvidha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class Hospital_Page extends ActionBarActivity {

    String hos_name = new String();
    Doctor doctor = new Doctor();
    Patient patient = new Patient();
    String hos_user_name = new String();
    String lat = new String();
    String lon = new String();
    String pat_user_name = new String();
    int num_views = 0;

    ArrayList<Hospital_Doctor> hospital_doctors = new ArrayList<Hospital_Doctor>();
    ArrayList<Doctor> doctors = new ArrayList<Doctor>();

    public  static final  String CLASS_NAME = "Hospital_Page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__page);

        final Spinner sp = (Spinner)findViewById(R.id.specialisationHospitals);
        final LinearLayout ll = (LinearLayout)findViewById(R.id.rrl);
        Intent intent = getIntent();
        String message = intent.getStringExtra("hospital");
        String[] hos = message.split("#", 5);

        hos_name = hos[0];
        hos_user_name = hos[1];
        lat = hos[2];
        lon = hos[3];
        pat_user_name = hos[4];

        final Context context = this;

        TextView text = new TextView(Hospital_Page.this);
        text.setHeight(100);
        ll.addView(text);

        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                final String area = sp.getSelectedItem().toString();
                hospital_doctors.clear();
                doctors.clear();
                for(int i = num_views + 1;i > 1;i--){
                    ll.removeViewAt(i);
                }
                try {
                    //To retrieve from the database
                    IBMQuery<Hospital_Doctor> query = IBMQuery.queryForClass(Hospital_Doctor.class);

                    query.find().continueWith(new Continuation<List<Hospital_Doctor>, Void>() {

                        @Override
                        public Void then(Task<List<Hospital_Doctor>> task) throws Exception {
                            if(task.isFaulted()) {
                                // Handle errors
                                System.out.println("Entered");
                            } else {
                                // do more work
                                System.out.println("Here");
                                final List<Hospital_Doctor> objects = task.getResult();
                                System.out.println("After here");
                                for(Hospital_Doctor hos_doc:objects){
                                    System.out.println("" + hos_doc.getArea() + " : " + area + " " + hos_doc.getHosName() + " : " + hos_name + " " + hos_doc.getHosUsername() + " : " + hos_user_name);
                                    if(hos_doc.getArea().equals(area) && hos_doc.getHosName().equals(hos_name) && hos_doc.getHosUsername().equals(hos_user_name)){
                                        hospital_doctors.add(hos_doc);
                                    }
                                }
                                Log.e(CLASS_NAME, "HERE");

                                System.out.println("Hospitals " + hospital_doctors.size());

                                try {
                                    //To retrieve from the database
                                    IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

                                    query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                                        @Override
                                        public Void then(Task<List<Doctor>> task) throws Exception {
                                            if(task.isFaulted()) {
                                                // Handle errors
                                                System.out.println("Entered");
                                            } else {
                                                // do more work
                                                System.out.println("Here");
                                                final List<Doctor> objects = task.getResult();
                                                System.out.println("After here");
                                                for(Doctor doc:objects){
                                                    for(int i = 0;i < hospital_doctors.size();i++){
                                                        if(doc.getName().equals(hospital_doctors.get(i).getDocName()) && doc.getUsername().equals(hospital_doctors.get(i).getDocUsername())){
                                                            doctors.add(doc);
                                                        }
                                                    }
                                                }
                                                Log.e(CLASS_NAME, "HERE");

                                                for (int i = 0; i < doctors.size(); i++) {
                                                    System.out.println(doctors.get(i).getRating());
                                                }

                                                Collections.sort(doctors, new Comparator<Doctor>() {
                                                    public int compare(Doctor d1, Doctor d2) {
                                                        if (Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating()))
                                                            return -1;
                                                        if (Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating()))
                                                            return 1;
                                                        else return 0;
                                                    }
                                                });

                                                System.out.println("After Sorting");

                                                for (int i = 0; i < doctors.size(); i++) {

                                                    final TextView grid = new TextView(Hospital_Page.this);
                                                    if (i % 2 == 0) {
                                                        grid.setBackgroundColor(Color.rgb(180, 180, 180));
                                                    } else {
                                                        grid.setBackgroundColor(Color.rgb(120, 120, 120));
                                                    }
                                                    grid.setId(i);
                                                    grid.setTextColor(Color.WHITE);

                                                    grid.setText("Name - " + doctors.get(i).getName() + " Fees - " + doctors.get(i).getFees());

                                                    grid.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(context, Hospital_Page.class);
                                                            intent.putExtra("doctor", doctors.get(grid.getId()).getName() + "#" + doctors.get(grid.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    ll.addView(grid);
                                                    num_views++;

                                                    LinearLayout lil = new LinearLayout(Hospital_Page.this);
                                                    lil.setOrientation(LinearLayout.HORIZONTAL);

                                                    final RatingBar rating = new RatingBar(Hospital_Page.this);

                                                    rating.setNumStars(5);
                                                    rating.setStepSize((float) 0.5);
                                                    rating.setMinimumHeight(200);
                                                    rating.setMinimumWidth(200);
                                                    rating.setRating((float) Double.parseDouble(doctors.get(i).getRating()));
                                                    rating.setId(i);
                                                    rating.setIsIndicator(true);

                                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                                                            ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);

                                                    rating.setLayoutParams(params);

                                                    if (i % 2 == 0) {
                                                        rating.setBackgroundColor(Color.rgb(180, 180, 180));
                                                    } else {
                                                        rating.setBackgroundColor(Color.rgb(120, 120, 120));
                                                    }
                                                    lil.addView(rating);

                                                    rating.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(context, Doctor_Page.class);
                                                            intent.putExtra("doctor", doctors.get(rating.getId()).getName() + "#" + doctors.get(rating.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                            startActivity(intent);

                                                        }
                                                    });

                                                    final TextView textviews1 = new TextView(Hospital_Page.this);
                                                    if (i % 2 == 0) {
                                                        textviews1.setBackgroundColor(Color.rgb(180, 180, 180));
                                                    } else {
                                                        textviews1.setBackgroundColor(Color.rgb(120, 120, 120));
                                                    }
                                                    textviews1.setWidth(420);
                                                    textviews1.setHeight(115);
                                                    textviews1.setId(i);

                                                    textviews1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(context, Doctor_Page.class);
                                                            intent.putExtra("doctor", doctors.get(textviews1.getId()).getName() + "#" + doctors.get(textviews1.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    lil.addView(textviews1);

                                                    final TextView textviews = new TextView(Hospital_Page.this);
                                                    if (i % 2 == 0) {
                                                        textviews.setBackgroundColor(Color.rgb(180, 180, 180));
                                                    } else {
                                                        textviews.setBackgroundColor(Color.rgb(120, 120, 120));
                                                    }
                                                    textviews.setWidth(300);
                                                    textviews.setHeight(115);
                                                    textviews.setId(i);
                                                    lil.addView(textviews);

                                                    textviews.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(context, Doctor_Page.class);
                                                            intent.putExtra("doctor", doctors.get(textviews.getId()).getName() + "#" + doctors.get(textviews.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                    ll.addView(lil);
                                                    num_views++;
                                                }

                                            }
                                            return null;
                                        }
                                    }, Task.UI_THREAD_EXECUTOR);
                                } catch (IBMDataException error) {
                                    Log.e(CLASS_NAME, "Exception : " + error.getMessage());
                                }

                            }
                            return null;
                        }
                    }, Task.UI_THREAD_EXECUTOR);
                } catch (IBMDataException error) {
                    Log.e(CLASS_NAME, "Exception : " + error.getMessage());
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hospital__page, menu);
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
}
