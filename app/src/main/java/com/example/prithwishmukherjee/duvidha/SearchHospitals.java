package com.example.prithwishmukherjee.duvidha;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import bolts.Continuation;
import bolts.Task;


public class SearchHospitals extends ActionBarActivity {

    LinearLayout ll;
    List<String> list;
    List<String> list1;
    List<String> search_type;
    ArrayList<Hospital> hospitals = new ArrayList<Hospital>();
    public static final String CLASS_NAME = "Search_Hospitals";
    Spinner sp;
    AutoCompleteTextView text;
    AutoCompleteTextView autoHospitalNames;
    String pat_user_name = new String();
    int num_doctors;
    int num_views = 0;
    int queried = 0;
    int click = 1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospitals);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        ll = (LinearLayout) findViewById(R.id.lii);

        num_doctors = 3;

        final Context context = this;

        search_type = new ArrayList<String>();
        search_type.add("Nearest First");
        search_type.add("By Name");

        Intent intent = getIntent();
        String message = intent.getStringExtra("search");
        String[] lat_lon = message.split(" ", 4);
        final double lat = Double.parseDouble(lat_lon[0]);
        final double lon = Double.parseDouble(lat_lon[1]);
        final String area = lat_lon[2];
        pat_user_name = lat_lon[3];


        sp = (Spinner) findViewById(R.id.spinner5);

        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (search_type.get(arg2).equals("By Name")) {
                    if (queried == 1) {
                        System.out.println(num_views);
                        for (int i = num_views; i > 0; i--) {
                            System.out.println("Different" + i);
                            ll.removeViewAt(i);
                        }
                        num_views = 0;

                        final TextView textview = new TextView(SearchHospitals.this);
                        textview.setText("Search Name");
                        textview.setTextSize(30);

                        textview.setTextColor(Color.BLACK);
                        ll.addView(textview);
                        num_views++;

                        autoHospitalNames = new AutoCompleteTextView(SearchHospitals.this);
                        populate();
                        autoHospitalNames.setText("Type Name here");

                        autoHospitalNames.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                autoHospitalNames.setText("");
                                return false;
                            }
                        });

                        text = autoHospitalNames;

                        autoHospitalNames.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                autoHospitalNames.setText("");
                            }
                        });

                        ll.addView(autoHospitalNames);
                        num_views++;

                        Button button = new Button(SearchHospitals.this);
                        button.setText("Go");
                        button.setBackgroundColor(Color.YELLOW);
                        button.setHeight(100);
                        button.setWidth(200);
                        button.setLayoutParams(new ActionBar.LayoutParams(200, 100));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final ArrayList<Hospital> new_hospitals = new ArrayList<Hospital>();
                                System.out.println("Size = " + hospitals.size());
                                for (int i = 0; i < hospitals.size(); i++) {
                                    System.out.println(hospitals.get(i).getName() + " " + autoHospitalNames.getText());
                                    if (hospitals.get(i).getName().toLowerCase().matches("(.*)" + autoHospitalNames.getText().toString().toLowerCase() + "(.*)")) {
                                        new_hospitals.add(hospitals.get(i));
                                    }
                                }

                                autoHospitalNames.setText("Type Name here");


                                System.out.println("Checking now" + num_views);
                                for (int i = num_views; i > 3; i--) {
                                    System.out.println("Checking again " + i);
                                    ll.removeViewAt(i);
                                }

                                num_views = 3;
                                for (int i = 0; i < new_hospitals.size(); i++) {

                                    final TextView grid = new TextView(SearchHospitals.this);
                                    if (i % 2 == 0) {
                                        grid.setBackgroundColor(Color.rgb(180,180,180));
                                    } else {
                                        grid.setBackgroundColor(Color.rgb(120,120,120));
                                    }
                                    grid.setId(i);
                                    grid.setTextColor(Color.WHITE);
                                    grid.setText("Name - " + new_hospitals.get(i).getName());
                                    grid.setTextSize(30);

                                    grid.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(click == 1) {
                                                Intent intent = new Intent(context, Hospital_Page.class);
                                                intent.putExtra("hospital", new_hospitals.get(grid.getId()).getName() + "#" + new_hospitals.get(grid.getId()).getUsername() + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                startActivity(intent);
                                                click = 1;
                                            }
                                            else {
                                                click++;
                                            }
                                        }
                                    });

                                    ll.addView(grid);
                                    num_views++;

                                }
                            }
                        });
                        ll.addView(button);
                        num_views++;



                    }
                } else {
                    if (queried == 1) {
                        System.out.println(num_views);
                        for (int i = num_views; i > 0; i--) {
                            System.out.println("Different" + i);
                            ll.removeViewAt(i);
                        }
                        num_views = 0;

                        if (search_type.get(arg2).equals("Nearest First")) {
                            Collections.sort(hospitals, new Comparator<Hospital>() {
                                public int compare(Hospital d1, Hospital d2) {
                                    float dist1 = distFrom((float) Double.parseDouble(d1.getLat()), (float) Double.parseDouble(d1.getLon()), (float) lat, (float) lon);
                                    float dist2 = distFrom((float) Double.parseDouble(d2.getLat()), (float) Double.parseDouble(d2.getLon()), (float) lat, (float) lon);
                                    if (dist1 > dist2) {
                                        return 1;
                                    }
                                    if (dist1 < dist2) {
                                        return -1;
                                    } else {
                                        return 0;
                                    }
                                }
                            });

                        }

                        for (int i = 0; i < hospitals.size(); i++) {

                            final TextView grid = new TextView(SearchHospitals.this);
                            if (i % 2 == 0) {
                                grid.setBackgroundColor(Color.rgb(180,180,180));
                            } else {
                                grid.setBackgroundColor(Color.rgb(120,120,120));
                            }
                            grid.setId(i);
                            grid.setTextColor(Color.WHITE);
                            grid.setText("Name - " + hospitals.get(i).getName());
                            grid.setTextSize(30);

                            grid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(click == 1) {
                                        Intent intent = new Intent(context, Hospital_Page.class);
                                        intent.putExtra("hospital", hospitals.get(grid.getId()).getName() + "#" + hospitals.get(grid.getId()).getUsername() + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                        startActivity(intent);
                                        click = 1;
                                    }
                                    else {
                                        click++;
                                    }
                                }
                            });

                            ll.addView(grid);
                            num_views++;


                        }
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton2);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speech_prompt));
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.speech_not_supported),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        Log.e(CLASS_NAME, "Inside onCreate");
        try {
            //To retrieve from the database
            IBMQuery<Hospital> query = IBMQuery.queryForClass(Hospital.class);

            query.find().continueWith(new Continuation<List<Hospital>, Void>() {

                @Override
                public Void then(Task<List<Hospital>> task) throws Exception {
                    ll = (LinearLayout) findViewById(R.id.lii);
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Hospital> objects = task.getResult();
                        System.out.println("After here");
                        for (Hospital hos : objects) {
                            System.out.println("Entered here");
                            Log.e(CLASS_NAME, hos.getName());
                            Log.e(CLASS_NAME, hos.getAddress());
                            hospitals.add(hos);
                        }
                        Log.e(CLASS_NAME, "HERE");
                    }

                    System.out.println("Before Sorting");


                    System.out.println("After Sorting");

                    Collections.sort(hospitals, new Comparator<Hospital>() {
                        public int compare(Hospital d1, Hospital d2) {
                            float dist1 = distFrom((float) Double.parseDouble(d1.getLat()), (float) Double.parseDouble(d1.getLon()), (float) lat, (float) lon);
                            float dist2 = distFrom((float) Double.parseDouble(d2.getLat()), (float) Double.parseDouble(d2.getLon()), (float) lat, (float) lon);
                            if (dist1 > dist2) {
                                return 1;
                            }
                            if (dist1 < dist2) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });


                    for (int i = 0; i < hospitals.size(); i++) {

                        final TextView grid = new TextView(SearchHospitals.this);
                        if (i % 2 == 0) {
                            grid.setBackgroundColor(Color.rgb(180,180,180));
                        } else {
                            grid.setBackgroundColor(Color.rgb(120,120,120));
                        }
                        grid.setId(i);
                        grid.setTextColor(Color.WHITE);

                        grid.setText("Name - " + hospitals.get(i).getName() );
                        grid.setTextSize(30);

                        grid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(click == 1) {
                                    Intent intent = new Intent(context, Hospital_Page.class);
                                    intent.putExtra("hospital", hospitals.get(grid.getId()).getName() + "#" + hospitals.get(grid.getId()).getUsername() + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                    startActivity(intent);
                                    click = 1;
                                }
                                else {
                                    click++;
                                }
                            }
                        });

                        ll.addView(grid);
                        num_views++;

                        queried = 1;

                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0) + " Said\n");
                    if(sp.getSelectedItem().toString().equals("By Name")){
                        text.setText(result.get(0));
                    }
                    for(int i = 0;i < 4;i++){
                        if(sp.getItemAtPosition(i).toString().toLowerCase().contains(result.get(0))){
                            System.out.println(sp.getItemAtPosition(i).toString());

                            sp.setSelection(i);
                            break;
                        }
                    }
//                    if(result.get(0).matches("Rate")){
//                        sp.setSelection(0);
//                    }
//                    else if(result.get(0).matches("Near")){
//                        sp.setSelection(1);
//                    }
//                    else if(result.get(0).matches("Cheap")){
//                        sp.setSelection(2);
//                    }
//                    else if(result.get(0).matches("Name")){
//                        sp.setSelection(3);
//                    }
                }
                break;
            }

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
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public void populate(){

        //  populate Globals.state here
        final ArrayList<Hospital> hospit = new ArrayList<Hospital>();
        try {
            //To retrieve from the database
            IBMQuery<Hospital> query = IBMQuery.queryForClass(Hospital.class);

            query.find().continueWith(new Continuation<List<Hospital>, Void>() {

                @Override
                public Void then(Task<List<Hospital>> task) throws Exception {
                    ll = (LinearLayout) findViewById(R.id.lii);
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Hospital> objects = task.getResult();
                        System.out.println("After here");
                        for (Hospital hos : objects) {
                            System.out.println("Entered here");
                            Log.e(CLASS_NAME, hos.getName());
                            Log.e(CLASS_NAME, hos.getAddress());
                            hospit.add(hos);
                        }
                        Log.e(CLASS_NAME, "HERE");
                        Globals.hospitals = new String[hospit.size()];
                        for(int i = 0;i < hospit.size();i++){
                            Globals.hospitals[i] = hospit.get(i).getName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter
                                (SearchHospitals.this,android.R.layout.simple_list_item_1,Globals.hospitals);
                        autoHospitalNames.setAdapter(adapter);
                    }

                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }

    }
}