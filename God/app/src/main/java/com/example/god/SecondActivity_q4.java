package com.example.god;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity_q4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_q4);

        TextView tvDetails = findViewById(R.id.tvConfirmationDetails_q4);
        Button btnBack = findViewById(R.id.btnBackToBook_q4);

        String date = getIntent().getStringExtra("date");
        String zone = getIntent().getStringExtra("zone");

        String display = "Booked Date: " + date + "\nSelected Zone: " + zone;
        tvDetails.setText(display);

        btnBack.setOnClickListener(v -> finish());
    }
}