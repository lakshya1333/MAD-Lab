package com.example.hospital_db;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Result Activity displays the summary of the registered patient.
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultView = findViewById(R.id.resultText);
        Button backBtn = findViewById(R.id.backBtn);

        // GET DATA FROM INTENT
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");

        // DISPLAY SUMMARY
        String summary = "Registration Successful!\n\n" +
                        "Patient ID: " + id + "\n" +
                        "Name: " + name + "\n" +
                        "Appointment Date: " + date;
        
        resultView.setText(summary);

        // REQUIREMENT: Back button to return to home page
        backBtn.setOnClickListener(v -> {
            // Finish this activity and return to MainActivity
            finish();
        });
    }
}
