package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    EditText etVehicle, etRC;
    Button btnSubmit;

    String[] vehicleTypes = {"Car", "Bike", "Truck", "Bus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerVehicle);
        etVehicle = findViewById(R.id.etVehicleNumber);
        etRC = findViewById(R.id.etRCNumber);
        btnSubmit = findViewById(R.id.btnSubmit);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                vehicleTypes);

        spinner.setAdapter(adapter);

        btnSubmit.setOnClickListener(view -> {

            String vehicleType = spinner.getSelectedItem().toString();
            String vehicleNumber = etVehicle.getText().toString();
            String rcNumber = etRC.getText().toString();

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("type", vehicleType);
            intent.putExtra("vehicle", vehicleNumber);
            intent.putExtra("rc", rcNumber);

            startActivity(intent);
        });
    }
}