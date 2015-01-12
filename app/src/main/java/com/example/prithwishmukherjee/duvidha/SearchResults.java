package com.example.prithwishmukherjee.duvidha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchResults extends ActionBarActivity {

    LinearLayout ll;
    List<String> list;
    List<String> list1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        ll=(LinearLayout) findViewById(R.id.li);

        list1 = new ArrayList<String>();

        list1.add("List of Doctors for " );



        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
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

        for(int i = 0;i < 4;i++) {
            GridView gridview = new GridView(SearchResults.this);
            gridview.setNumColumns(1);
            gridview.setAdapter(new ImageAdapter(this));

            if(i % 2 == 0) {
                gridview.setBackgroundColor(Color.RED);
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

            list = new ArrayList<String>();

            list.add("Doctor - " + i + "\n Address - Plot No. " + i + "\n");

            ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list);
            GridView grid = new GridView(SearchResults.this);
            grid.setNumColumns(1);
            if(i % 2 == 0) {
                grid.setBackgroundColor(Color.RED);
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

            RatingBar rating = new RatingBar(SearchResults.this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) ViewGroup.LayoutParams.WRAP_CONTENT, (int) ViewGroup.LayoutParams.WRAP_CONTENT);

            rating.setLayoutParams(params);
            if(i % 2 == 0) {
                rating.setBackgroundColor(Color.RED);
            }
            else {
                rating.setBackgroundColor(Color.YELLOW);
            }
            ll.addView(rating);

            rating.setNumStars(10);
            rating.setStepSize((float) 0.5);
            rating.setRating(i * 2);

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
}
