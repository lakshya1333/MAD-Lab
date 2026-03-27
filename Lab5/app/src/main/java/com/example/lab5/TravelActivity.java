package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TravelActivity extends AppCompatActivity {

    Spinner source, destination;
    DatePicker datePicker;
    ToggleButton toggleTrip;
    Button submit, reset;

    String[] cities = {"Mumbai", "Delhi", "Bangalore", "Chennai", "Pune"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        source = findViewById(R.id.spinnerSource);
        destination = findViewById(R.id.spinnerDestination);
        datePicker = findViewById(R.id.datePicker);
        toggleTrip = findViewById(R.id.toggleTrip);
        submit = findViewById(R.id.btnSubmit);
        reset = findViewById(R.id.btnReset);

        Calendar cal = Calendar.getInstance();
        datePicker.setMinDate(cal.getTimeInMillis());

//        cal.add(Calendar.DAY_OF_MONTH, 20);
//        datePicker.setMaxDate(cal.getTimeInMillis());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, cities);

        source.setAdapter(adapter);
        destination.setAdapter(adapter);

        submit.setOnClickListener(v -> {

            String src = source.getSelectedItem().toString();
            String dest = destination.getSelectedItem().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            String tripType = toggleTrip.isChecked() ? "Round Trip" : "One Way";

            Intent intent = new Intent(this, TravelSummaryActivity.class);
            intent.putExtra("src", src);
            intent.putExtra("dest", dest);
            intent.putExtra("date", day + "/" + month + "/" + year);
            intent.putExtra("trip", tripType);

            startActivity(intent);
        });

        reset.setOnClickListener(v -> {

            source.setSelection(0);
            destination.setSelection(0);

//            Calendar cal = Calendar.getInstance();
            datePicker.updateDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );

            toggleTrip.setChecked(false);
        });
    }
}