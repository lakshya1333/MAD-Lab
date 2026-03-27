package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.ToggleButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;

public class MainActivity_q4 extends AppCompatActivity {

    private DatePicker datePicker;
    private ToggleButton toggleConfirm;
    private Spinner spinnerZone;
    private Button btnBookSlot;
    private String selectedZone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_q4);

        Toolbar toolbar = findViewById(R.id.toolbar_q4);
        setSupportActionBar(toolbar);

        datePicker = findViewById(R.id.datePicker_q4);
        toggleConfirm = findViewById(R.id.toggleConfirm_q4);
        spinnerZone = findViewById(R.id.spinnerZone_q4);
        btnBookSlot = findViewById(R.id.btnBookSlot_q4);

        // Date constraints
        Calendar calendar = Calendar.getInstance();
        datePicker.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_YEAR, 20);
        datePicker.setMaxDate(calendar.getTimeInMillis());

        // Spinner setup
        String[] zones = {"Select Zone", "North", "South", "East", "West"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(adapter);

        spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String choice = zones[position];
                    new AlertDialog.Builder(MainActivity_q4.this)
                            .setTitle("Confirm Choice")
                            .setMessage("Are you sure you want to select " + choice + " zone?")
                            .setPositiveButton("Yes", (dialog, which) -> selectedZone = choice)
                            .setNegativeButton("No", (dialog, which) -> spinnerZone.setSelection(0))
                            .show();
                } else {
                    selectedZone = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Toggle button logic
        toggleConfirm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnBookSlot.setEnabled(isChecked);
        });

        btnBookSlot.setOnClickListener(v -> {
            if (selectedZone.isEmpty()) {
                new AlertDialog.Builder(this).setMessage("Please select a zone").show();
                return;
            }
            String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
            Intent intent = new Intent(MainActivity_q4.this, SecondActivity_q4.class);
            intent.putExtra("date", date);
            intent.putExtra("zone", selectedZone);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_q4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh_q4) {
            refreshAll();
            return true;
        } else if (id == R.id.action_info_q4) {
            showStationInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshAll() {
        toggleConfirm.setChecked(false);
        spinnerZone.setSelection(0);
        Calendar now = Calendar.getInstance();
        datePicker.updateDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        selectedZone = "";
    }

    private void showStationInfo() {
        String info = "North Zone: 50 people\nSouth Zone: 30 people\nEast Zone: 45 people\nWest Zone: 20 people";
        new AlertDialog.Builder(this)
                .setTitle("Station Information")
                .setMessage(info)
                .setPositiveButton("OK", null)
                .show();
    }
}