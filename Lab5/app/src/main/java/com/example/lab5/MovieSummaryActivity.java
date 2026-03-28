package com.example.lab5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MovieSummaryActivity extends AppCompatActivity {

    TextView tv, tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_summary);

        tv = findViewById(R.id.tvMovieDetails);
        tvHistory = findViewById(R.id.tvBookingHistory);

        String movie = getIntent().getStringExtra("movie");
        String theatre = getIntent().getStringExtra("theatre");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String type = getIntent().getStringExtra("type");

        // Fake seat generation
        int seats = new Random().nextInt(50) + 1;

        String result = "Movie Ticket\n\n" +
                "Movie: " + movie +
                "\nTheatre: " + theatre +
                "\nDate: " + date +
                "\nTime: " + time +
                "\nTicket: " + type +
                "\nAvailable Seats: " + seats;

        tv.setText(result);

        // Load History from SharedPreferences
        SharedPreferences pref = getSharedPreferences("MovieHistory", MODE_PRIVATE);
        String history = pref.getString("bookings", "No history available");
        tvHistory.setText(history);
    }
}