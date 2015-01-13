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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class SearchResults extends ActionBarActivity {

    LinearLayout ll;
    List<String> list;
    List<String> list1;
    List<String> search_type;
    ArrayList<Doctor> doctors = new ArrayList<Doctor>();
    public static final String CLASS_NAME="Search_Results";
    int num_doctors;
    int num_views = 1;
    int queried = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        ll=(LinearLayout) findViewById(R.id.li);

        num_doctors = 3;

        final Context context = this;

        search_type = new ArrayList<String>();
        search_type.add("Top Rated Doctors");
        search_type.add("Nearest First");
        search_type.add("Cheapest First");

        Spinner sp = (Spinner) findViewById(R.id.spinner2);
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if(queried == 1) {
                    for (int i = num_views - 1; i > 0; i--) {
                        ll.removeViewAt(i);
                    }
                    num_views = 1;

                    if(search_type.get(arg2).equals("Nearest First")){
                        Intent intent = getIntent();
                        String message = intent.getStringExtra("search");
                        String[] lat_lon = message.split(" ",2);
                        final double lat = Double.parseDouble(lat_lon[0]);
                        final double lon = Double.parseDouble(lat_lon[1]);
                        Collections.sort(doctors, new Comparator<Doctor>() {
                            public int compare(Doctor d1, Doctor d2) {
                                float dist1 = distFrom((float)Double.parseDouble(d1.getLat()),(float)Double.parseDouble(d1.getLon()),(float)lat,(float)lon);
                                float dist2 = distFrom((float)Double.parseDouble(d2.getLat()),(float)Double.parseDouble(d2.getLon()),(float)lat,(float)lon);
                                if (dist1 > dist2) {
                                    return -1;
                                }
                                if (dist1 < dist2) {
                                    return 1;
                                }
                                else {
                                    if(Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating())){
                                        return -1;
                                    }
                                    if(Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating())){
                                        return 1;
                                    }
                                    else {
                                        return 0;
                                    }
                                }
                            }
                        });

                    }
                    else if(search_type.get(arg2).equals("Cheapest First")) {
                        Collections.sort(doctors, new Comparator<Doctor>() {
                            public int compare(Doctor d1, Doctor d2) {
                                if (Integer.parseInt(d1.getFees()) > Integer.parseInt(d2.getFees())) {
                                    return -1;
                                }
                                if (Integer.parseInt(d1.getFees()) < Integer.parseInt(d2.getFees())) {
                                    return 1;
                                }
                                else {
                                    if(Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating())){
                                        return -1;
                                    }
                                    if(Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating())){
                                        return 1;
                                    }
                                    else {
                                        return 0;
                                    }
                                }
                            }
                        });

                    }
                    else if(search_type.get(arg2).equals("Top Rated Doctors")) {
                        Collections.sort(doctors, new Comparator<Doctor>() {
                            public int compare(Doctor d1, Doctor d2) {
                                if (Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating()))
                                    return -1;
                                if (Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating()))
                                    return 1;
                                else return 0;
                            }
                        });

                    }

                    list1 = new ArrayList<String>();

                    list1.add("List of Doctors for ");

                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list1);
                    GridView grid1 = new GridView(SearchResults.this);
                    grid1.setNumColumns(1);
                    grid1.setBackgroundColor(Color.GREEN);
                    grid1.setAdapter(adp1);

                    grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                long arg3) {

                            Toast.makeText(getBaseContext(), list1.get(arg2),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    ll.addView(grid1);
                    num_views++;

                    for (int i = 0; i < num_doctors; i++) {
                        System.out.println(i);
                        GridView gridview = new GridView(SearchResults.this);
                        gridview.setNumColumns(1);
                        gridview.setAdapter(new ImageAdapter(context));


                        if(i % 2 == 0) {
                            gridview.setBackgroundColor(Color.argb(255, 213, 213, 213));
                        }
                        else {
                            gridview.setBackgroundColor(Color.YELLOW);
                        }

                        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                Toast.makeText(SearchResults.this, "" + position, Toast.LENGTH_SHORT).show();
                            }
                        });

                        ll.addView(gridview);
                        num_views++;

                        list = new ArrayList<String>();

                        list.add("Doctor - " + doctors.get(i).getName() + "\n Fees - " + doctors.get(i).getFees() + "\n");

                        ArrayAdapter<String> adp = new ArrayAdapter<String>(context,
                                android.R.layout.simple_dropdown_item_1line, list);
                        GridView grid = new GridView(SearchResults.this);
                        grid.setNumColumns(1);
                        if(i % 2 == 0) {
                            grid.setBackgroundColor(Color.argb(255, 213, 213, 213));
                        }
                        else {
                            grid.setBackgroundColor(Color.YELLOW);
                        }

                        grid.setAdapter(adp);

                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {

                                Toast.makeText(getBaseContext(), list.get(arg2),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        ll.addView(grid);
                        num_views++;

                        RatingBar rating = new RatingBar(SearchResults.this);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                                ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);

                        rating.setLayoutParams(params);

                        if(i % 2 == 0) {
                            rating.setBackgroundColor(Color.argb(255, 213, 213, 213));
                        }
                        else {
                            rating.setBackgroundColor(Color.YELLOW);
                        }

                        ll.addView(rating);
                        num_views++;

                        rating.setNumStars(10);
                        rating.setStepSize((float) 0.5);
                        rating.setRating((float) Double.parseDouble(doctors.get(i).getRating()));
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



        Log.e(CLASS_NAME, "Inside onCreate");
        try {
            //To retrieve from the database
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    ll = (LinearLayout)findViewById(R.id.li);
                    if(task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Doctor> objects = task.getResult();
                        System.out.println("After here");
                        for(Doctor doc:objects){
                            System.out.println("Entered here");
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME,doc.getArea());
                            Log.e(CLASS_NAME,doc.getAddress());
                            Log.e(CLASS_NAME,doc.getRating());
                            doctors.add(doc);
                        }
                        Log.e(CLASS_NAME,"HERE");
                    }

                    System.out.println("Before Sorting");

                    for(int i = 0;i < doctors.size();i++){
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

                    list1 = new ArrayList<String>();

                    list1.add("List of Doctors for " );

                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list1);
                    GridView grid1 = new GridView(SearchResults.this);
                    grid1.setNumColumns(1);
                    grid1.setBackgroundColor(Color.GREEN);
                    grid1.setAdapter(adp1);

                    grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                long arg3) {

                            Toast.makeText(getBaseContext(), list1.get(arg2),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    ll.addView(grid1);
                    num_views++;

                    for(int i = 0;i < num_doctors;i++) {
                        System.out.println(i);
                        GridView gridview = new GridView(SearchResults.this);
                        gridview.setNumColumns(1);
                        gridview.setAdapter(new ImageAdapter(context));

                        if(i % 2 == 0) {
                            gridview.setBackgroundColor(Color.argb(255,213,213,213));
                        }
                        else {
                            gridview.setBackgroundColor(Color.YELLOW);
                        }

                        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                Toast.makeText(SearchResults.this, "" + position, Toast.LENGTH_SHORT).show();
                            }
                        });

                        ll.addView(gridview);
                        num_views++;

                        list = new ArrayList<String>();

                        list.add("Doctor - " + doctors.get(i).getName() + "\n Fees - " + doctors.get(i).getFees() + "\n");

                        ArrayAdapter<String> adp = new ArrayAdapter<String>(context,
                                android.R.layout.simple_dropdown_item_1line, list);
                        GridView grid = new GridView(SearchResults.this);
                        grid.setNumColumns(1);
                        if(i % 2 == 0) {
                            grid.setBackgroundColor(Color.argb(255,213,213,213));
                        }
                        else {
                            grid.setBackgroundColor(Color.YELLOW);
                        }
                        grid.setAdapter(adp);

                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {

                                Toast.makeText(getBaseContext(), list.get(arg2),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        ll.addView(grid);
                        num_views++;

                        RatingBar rating = new RatingBar(SearchResults.this);

//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
//                                ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                        rating.setLayoutParams(params);
                        if(i % 2 == 0) {
                            rating.setBackgroundColor(Color.argb(255,213,213,213));
                        }
                        else {
                            rating.setBackgroundColor(Color.YELLOW);
                        }
                        ll.addView(rating);
                        num_views++;

                        rating.setNumStars(10);
                        rating.setStepSize((float) 0.5);
                        rating.setRating((float)Double.parseDouble(doctors.get(i).getRating()));
                        queried = 1;

                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME,"Exception : " + error.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seach_results, menu);
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

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371; //kilometers
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

}
