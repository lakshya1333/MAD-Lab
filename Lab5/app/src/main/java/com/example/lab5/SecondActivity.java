package com.example.lab5;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    TextView tvDetails;
    Button btnConfirm, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDetails = findViewById(R.id.tvDetails);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnEdit = findViewById(R.id.btnEdit);

        String type = getIntent().getStringExtra("type");
        String vehicle = getIntent().getStringExtra("vehicle");
        String rc = getIntent().getStringExtra("rc");

        String details = "Vehicle Type: " + type +
                "\nVehicle Number: " + vehicle +
                "\nRC Number: " + rc;

        tvDetails.setText(details);

        btnConfirm.setOnClickListener(v -> {
            int serial = new Random().nextInt(10000);

            Toast.makeText(this,
                    "Parking Confirmed! Serial No: " + serial,
                    Toast.LENGTH_LONG).show();
        });

        btnEdit.setOnClickListener(v -> {
            finish(); // go back to edit
        });
    }
}