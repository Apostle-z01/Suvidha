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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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


public class SearchResults extends ActionBarActivity {

    LinearLayout ll;
    List<String> list;
    List<String> list1;
    List<String> search_type;
    ArrayList<Doctor> doctors = new ArrayList<Doctor>();
    public static final String CLASS_NAME = "Search_Results";
    Spinner sp;
    AutoCompleteTextView text;
    AutoCompleteTextView autoDoctorNames;
    String pat_user_name = new String();
    int num_doctors;
    int num_views = 0;
    int queried = 0;
    int click = 1;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        ll = (LinearLayout) findViewById(R.id.li);

        num_doctors = 3;

        final Context context = this;

        search_type = new ArrayList<String>();
        search_type.add("Top Rated Doctors");
        search_type.add("Nearest First");
        search_type.add("Cheapest First");
        search_type.add("By Name");

        Intent intent = getIntent();
        String message = intent.getStringExtra("search");
        String[] lat_lon = message.split(" ", 4);
        final double lat = Double.parseDouble(lat_lon[0]);
        final double lon = Double.parseDouble(lat_lon[1]);
        final String area = lat_lon[2];
        pat_user_name = lat_lon[3];


        sp = (Spinner) findViewById(R.id.spinner2);

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

                        final TextView textview = new TextView(SearchResults.this);
                        textview.setText("Search Name");

                        textview.setTextColor(Color.BLACK);
                        ll.addView(textview);
                        num_views++;

                        autoDoctorNames = new AutoCompleteTextView(SearchResults.this);
                        populate();
                        autoDoctorNames.setText("Type Name here");

                        autoDoctorNames.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                autoDoctorNames.setText("");
                                return false;
                            }
                        });

                        autoDoctorNames.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                autoDoctorNames.setText("");
                            }
                        });
                        text = autoDoctorNames;


                        ll.addView(autoDoctorNames);
                        num_views++;

                        Button button = new Button(SearchResults.this);
                        button.setText("Go");
                        button.setBackgroundColor(Color.YELLOW);
                        button.setHeight(100);
                        button.setWidth(200);
                        button.setLayoutParams(new ActionBar.LayoutParams(200, 100));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final ArrayList<Doctor> new_doctors = new ArrayList<Doctor>();
                                System.out.println("Size = " + doctors.size());
                                for (int i = 0; i < doctors.size(); i++) {
                                    System.out.println(doctors.get(i).getName() + " " + autoDoctorNames.getText());
                                    if (doctors.get(i).getName().toLowerCase().matches("(.*)" + autoDoctorNames.getText().toString().toLowerCase() + "(.*)")) {
                                        new_doctors.add(doctors.get(i));
                                    }
                                }

                                autoDoctorNames.setText("Type Name here");
                                Collections.sort(new_doctors, new Comparator<Doctor>() {
                                    public int compare(Doctor d1, Doctor d2) {
                                        if (Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating()))
                                            return -1;
                                        if (Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating()))
                                            return 1;
                                        else return 0;
                                    }
                                });


                                System.out.println("Checking now" + num_views);
                                for (int i = num_views; i > 3; i--) {
                                    System.out.println("Checking again " + i);
                                    ll.removeViewAt(i);
                                }

                                num_views = 3;
                                for (int i = 0; i < new_doctors.size(); i++) {

                                    final TextView grid = new TextView(SearchResults.this);
                                    if (i % 2 == 0) {
                                        grid.setBackgroundColor(Color.rgb(180,180,180));
                                    } else {
                                        grid.setBackgroundColor(Color.rgb(120,120,120));
                                    }
                                    grid.setId(i);
                                    grid.setTextColor(Color.WHITE);
                                    grid.setText("Name - " + new_doctors.get(i).getName() + " Fees - " + new_doctors.get(i).getFees());

                                    grid.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(click == 1) {
                                                Intent intent = new Intent(context, Doctor_Page.class);
                                                intent.putExtra("doctor", new_doctors.get(grid.getId()).getName() + "#" + new_doctors.get(grid.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
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

                                    LinearLayout lil = new LinearLayout(SearchResults.this);
                                    lil.setOrientation(LinearLayout.HORIZONTAL);

                                    final RatingBar rating = new RatingBar(SearchResults.this);

                                    rating.setNumStars(5);
                                    rating.setStepSize((float) 0.5);
                                    rating.setRating((float) Double.parseDouble(new_doctors.get(i).getRating()));
                                    rating.setId(i);
                                    rating.setIsIndicator(true);

                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                                            ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);

                                    rating.setLayoutParams(params);

                                    if (i % 2 == 0) {
                                        rating.setBackgroundColor(Color.rgb(180,180,180));
                                    } else {
                                        rating.setBackgroundColor(Color.rgb(120,120,120));
                                    }
                                    lil.addView(rating);

                                    rating.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(click == 1) {
                                                Intent intent = new Intent(context, Doctor_Page.class);
                                                intent.putExtra("doctor", new_doctors.get(rating.getId()).getName() + "#" + new_doctors.get(rating.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                startActivity(intent);
                                                click = 1;
                                            }
                                            else {
                                                click++;
                                            }
                                        }
                                    });

                                    final TextView textviews1 = new TextView(SearchResults.this);
                                    if (i % 2 == 0) {
                                        textviews1.setBackgroundColor(Color.rgb(180,180,180));
                                    } else {
                                        textviews1.setBackgroundColor(Color.rgb(120,120,120));
                                    }
                                    textviews1.setWidth(420);
                                    textviews1.setHeight(115);
                                    textviews1.setId(i);

                                    textviews1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(click == 1) {
                                                Intent intent = new Intent(context, Doctor_Page.class);
                                                intent.putExtra("doctor", new_doctors.get(textviews1.getId()).getName() + "#" + new_doctors.get(textviews1.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                startActivity(intent);
                                                click = 1;
                                            }
                                            else {
                                                click++;
                                            }
                                        }
                                    });

                                    lil.addView(textviews1);

                                    final TextView textviews = new TextView(SearchResults.this);
                                    if (i % 2 == 0) {
                                        textviews.setBackgroundColor(Color.rgb(180,180,180));
                                    } else {
                                        textviews.setBackgroundColor(Color.rgb(120,120,120));
                                    }
                                    textviews.setWidth(300);
                                    textviews.setHeight(115);
                                    textviews.setId(i);
                                    lil.addView(textviews);

                                    textviews.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(click == 1) {
                                                Intent intent = new Intent(context, Doctor_Page.class);
                                                intent.putExtra("doctor", new_doctors.get(textviews.getId()).getName() + "#" + new_doctors.get(textviews.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                                startActivity(intent);
                                                click = 1;
                                            }
                                            else {
                                                click++;
                                            }
                                        }
                                    });

                                    ll.addView(lil);
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
                            Collections.sort(doctors, new Comparator<Doctor>() {
                                public int compare(Doctor d1, Doctor d2) {
                                    float dist1 = distFrom((float) Double.parseDouble(d1.getLat()), (float) Double.parseDouble(d1.getLon()), (float) lat, (float) lon);
                                    float dist2 = distFrom((float) Double.parseDouble(d2.getLat()), (float) Double.parseDouble(d2.getLon()), (float) lat, (float) lon);
                                    if (dist1 > dist2) {
                                        return 1;
                                    }
                                    if (dist1 < dist2) {
                                        return -1;
                                    } else {
                                        if (Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating())) {
                                            return -1;
                                        }
                                        if (Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating())) {
                                            return 1;
                                        } else {
                                            return 0;
                                        }
                                    }
                                }
                            });

                        } else if (search_type.get(arg2).equals("Cheapest First")) {
                            Collections.sort(doctors, new Comparator<Doctor>() {
                                public int compare(Doctor d1, Doctor d2) {
                                    if (Integer.parseInt(d1.getFees()) > Integer.parseInt(d2.getFees())) {
                                        return 1;
                                    }
                                    if (Integer.parseInt(d1.getFees()) < Integer.parseInt(d2.getFees())) {
                                        return -1;
                                    } else {
                                        if (Double.parseDouble(d1.getRating()) > Double.parseDouble(d2.getRating())) {
                                            return -1;
                                        }
                                        if (Double.parseDouble(d1.getRating()) < Double.parseDouble(d2.getRating())) {
                                            return 1;
                                        } else {
                                            return 0;
                                        }
                                    }
                                }
                            });

                        } else if (search_type.get(arg2).equals("Top Rated Doctors")) {
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

                        for (int i = 0; i < doctors.size(); i++) {

                            final TextView grid = new TextView(SearchResults.this);
                            if (i % 2 == 0) {
                                grid.setBackgroundColor(Color.rgb(180,180,180));
                            } else {
                                grid.setBackgroundColor(Color.rgb(120,120,120));
                            }
                            grid.setId(i);
                            grid.setTextColor(Color.WHITE);
                            grid.setText("Name - " + doctors.get(i).getName() + " Fees - " + doctors.get(i).getFees());

                            grid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(click == 1) {
                                        Intent intent = new Intent(context, Doctor_Page.class);
                                        intent.putExtra("doctor", doctors.get(grid.getId()).getName() + "#" + doctors.get(grid.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
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

                            LinearLayout lil = new LinearLayout(SearchResults.this);
                            lil.setOrientation(LinearLayout.HORIZONTAL);

                            final RatingBar rating = new RatingBar(SearchResults.this);

                            rating.setNumStars(5);
                            rating.setStepSize((float) 0.5);
                            rating.setRating((float) Double.parseDouble(doctors.get(i).getRating()));
                            rating.setId(i);
                            rating.setIsIndicator(true);

                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                                    ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);

                            rating.setLayoutParams(params);

                            if (i % 2 == 0) {
                                rating.setBackgroundColor(Color.rgb(180,180,180));
                            } else {
                                rating.setBackgroundColor(Color.rgb(120,120,120));
                            }
                            lil.addView(rating);

                            rating.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(click == 1) {
                                        Intent intent = new Intent(context, Doctor_Page.class);
                                        intent.putExtra("doctor", doctors.get(rating.getId()).getName() + "#" + doctors.get(rating.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                        startActivity(intent);
                                        click = 1;
                                    }
                                    else {
                                        click++;
                                    }
                                }
                            });

                            final TextView textviews1 = new TextView(SearchResults.this);
                            if (i % 2 == 0) {
                                textviews1.setBackgroundColor(Color.rgb(180,180,180));
                            } else {
                                textviews1.setBackgroundColor(Color.rgb(120,120,120));
                            }
                            textviews1.setWidth(420);
                            textviews1.setHeight(115);
                            textviews1.setId(i);

                            textviews1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(click == 1) {
                                        Intent intent = new Intent(context, Doctor_Page.class);
                                        intent.putExtra("doctor", doctors.get(textviews1.getId()).getName() + "#" + doctors.get(textviews1.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                        startActivity(intent);
                                        click = 1;
                                    }
                                    else {
                                        click++;
                                    }
                                }
                            });

                            lil.addView(textviews1);

                            final TextView textviews = new TextView(SearchResults.this);
                            if (i % 2 == 0) {
                                textviews.setBackgroundColor(Color.rgb(180,180,180));
                            } else {
                                textviews.setBackgroundColor(Color.rgb(120,120,120));
                            }
                            textviews.setWidth(300);
                            textviews.setHeight(115);
                            textviews.setId(i);
                            lil.addView(textviews);

                            textviews.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(click == 1) {
                                        Intent intent = new Intent(context, Doctor_Page.class);
                                        intent.putExtra("doctor", doctors.get(textviews.getId()).getName() + "#" + doctors.get(textviews.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                        startActivity(intent);
                                        click = 1;
                                    }
                                    else {
                                        click++;
                                    }
                                }
                            });

                            ll.addView(lil);
                            num_views++;

                        }
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);

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
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    ll = (LinearLayout) findViewById(R.id.li);
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Doctor> objects = task.getResult();
                        System.out.println("After here");
                        for (Doctor doc : objects) {
                            System.out.println("Entered here");
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME, doc.getArea());
                            Log.e(CLASS_NAME, doc.getAddress());
                            Log.e(CLASS_NAME, doc.getRating());
                            if (doc.getArea().matches(area)) {
                                doctors.add(doc);
                            }
                        }
                        Log.e(CLASS_NAME, "HERE");
                    }

                    System.out.println("Before Sorting");

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

                        final TextView grid = new TextView(SearchResults.this);
                        if (i % 2 == 0) {
                            grid.setBackgroundColor(Color.rgb(180,180,180));
                        } else {
                            grid.setBackgroundColor(Color.rgb(120,120,120));
                        }
                        grid.setId(i);
                        grid.setTextColor(Color.WHITE);

                        grid.setText("Name - " + doctors.get(i).getName() + " Fees - " + doctors.get(i).getFees());

                        grid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (click == 1) {
                                    Intent intent = new Intent(context, Doctor_Page.class);
                                    intent.putExtra("doctor", doctors.get(grid.getId()).getName() + "#" + doctors.get(grid.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                    startActivity(intent);
                                    click = 1;
                                } else {
                                    click++;
                                }
                            }
                        });

                        ll.addView(grid);
                        num_views++;

                        LinearLayout lil = new LinearLayout(SearchResults.this);
                        lil.setOrientation(LinearLayout.HORIZONTAL);

                        final RatingBar rating = new RatingBar(SearchResults.this);

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
                            rating.setBackgroundColor(Color.rgb(180,180,180));
                        } else {
                            rating.setBackgroundColor(Color.rgb(120,120,120));
                        }
                        lil.addView(rating);

                        rating.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(click == 1) {
                                    Intent intent = new Intent(context, Doctor_Page.class);
                                    intent.putExtra("doctor", doctors.get(rating.getId()).getName() + "#" + doctors.get(rating.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                    startActivity(intent);
                                    click = 1;
                                }
                                else {
                                    click++;
                                }
                            }
                        });

                        final TextView textviews1 = new TextView(SearchResults.this);
                        if (i % 2 == 0) {
                            textviews1.setBackgroundColor(Color.rgb(180,180,180));
                        } else {
                            textviews1.setBackgroundColor(Color.rgb(120,120,120));
                        }
                        textviews1.setWidth(420);
                        textviews1.setHeight(115);
                        textviews1.setId(i);

                        textviews1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(click == 1) {
                                    Intent intent = new Intent(context, Doctor_Page.class);
                                    intent.putExtra("doctor", doctors.get(textviews1.getId()).getName() + "#" + doctors.get(textviews1.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                    startActivity(intent);
                                    click = 1;
                                }
                                else {
                                    click++;
                                }
                            }
                        });

                        lil.addView(textviews1);

                        final TextView textviews = new TextView(SearchResults.this);
                        if (i % 2 == 0) {
                            textviews.setBackgroundColor(Color.rgb(180,180,180));
                        } else {
                            textviews.setBackgroundColor(Color.rgb(120,120,120));
                        }
                        textviews.setWidth(300);
                        textviews.setHeight(115);
                        textviews.setId(i);
                        lil.addView(textviews);

                        textviews.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(click == 1) {
                                    Intent intent = new Intent(context, Doctor_Page.class);
                                    intent.putExtra("doctor", doctors.get(textviews.getId()).getName() + "#" + doctors.get(textviews.getId()).getUsername() + "#" + area + "#" + String.valueOf(lat) + "#" + String.valueOf(lon) + "#" + pat_user_name);
                                    startActivity(intent);
                                    click = 1;
                                }
                                else {
                                    click++;
                                }
                            }
                        });

                        ll.addView(lil);
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
        final ArrayList<Doctor> doct = new ArrayList<Doctor>();
        try {
            //To retrieve from the database
            IBMQuery<Doctor> query = IBMQuery.queryForClass(Doctor.class);

            query.find().continueWith(new Continuation<List<Doctor>, Void>() {

                @Override
                public Void then(Task<List<Doctor>> task) throws Exception {
                    ll = (LinearLayout) findViewById(R.id.li);
                    if (task.isFaulted()) {
                        // Handle errors
                        System.out.println("Entered");
                    } else {
                        // do more work
                        System.out.println("Here");
                        final List<Doctor> objects = task.getResult();
                        System.out.println("After here");
                        for (Doctor doc : objects) {
                            Log.e(CLASS_NAME, doc.getName());
                            Log.e(CLASS_NAME, doc.getAddress());
                            doct.add(doc);
                        }
                        Log.e(CLASS_NAME, "HERE");
                        Globals.doctors = new String[doct.size()];
                        Log.e(String.valueOf(doct.size()),"cause fuck you, you don't know async");
                        for(int i = 0;i < doct.size();i++){
                            Globals.doctors[i] = doct.get(i).getName();
                        }
                        ArrayAdapter adapter = new ArrayAdapter
                                (SearchResults.this,android.R.layout.simple_list_item_1,Globals.doctors);
                        autoDoctorNames.setAdapter(adapter);
                    }

                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        } catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }

    }
}