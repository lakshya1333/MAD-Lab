package com.example.moviereview_db;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewReviewsActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    TextView tvDetailName, tvDetailYear, tvDetailRating;
    ArrayList<String> movieNames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailYear = findViewById(R.id.tvDetailYear);
        tvDetailRating = findViewById(R.id.tvDetailRating);

        movieNames = new ArrayList<>();
        loadMovieNames();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMovie = movieNames.get(position);
                displayMovieDetails(selectedMovie);
            }
        });
    }

    private void loadMovieNames() {
        movieNames.clear();
        Cursor cursor = db.getAllData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                movieNames.add(cursor.getString(1)); // Column 1 is Name
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieNames);
        listView.setAdapter(adapter);
    }

    private void displayMovieDetails(String name) {
        Cursor cursor = db.getMovieData(name);
        if (cursor.moveToFirst()) {
            tvDetailName.setText(cursor.getString(1));
            tvDetailYear.setText(String.valueOf(cursor.getInt(2)));
            tvDetailRating.setText(String.valueOf(cursor.getInt(3)));
        }
        cursor.close();
    }
}
