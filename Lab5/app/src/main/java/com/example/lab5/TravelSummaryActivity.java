package com.example.lab5;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TravelSummaryActivity extends AppCompatActivity {

    TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);

        tvSummary = findViewById(R.id.tvSummary);

        String src = getIntent().getStringExtra("src");
        String dest = getIntent().getStringExtra("dest");
        String date = getIntent().getStringExtra("date");
        String trip = getIntent().getStringExtra("trip");

        String result = "Travel Details\n\n" +
                "From: " + src +
                "\nTo: " + dest +
                "\nDate: " + date +
                "\nTrip Type: " + trip;

        tvSummary.setText(result);
    }
}
