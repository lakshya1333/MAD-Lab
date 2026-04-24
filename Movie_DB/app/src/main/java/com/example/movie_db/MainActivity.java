package com.example.movie_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMovie, spinnerTheatre;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox cbStandard, cbPremium;
    Button btnBook, btnViewHistory;
    DatabaseHelper db;

    String[] movies = {"Inception", "Interstellar", "The Dark Knight", "Avatar", "Avengers"};
    String[] theatres = {"PVR Cinemas", "Cinepolis", "INOX", "IMAX"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerTheatre = findViewById(R.id.spinnerTheatre);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        cbStandard = findViewById(R.id.cbStandard);
        cbPremium = findViewById(R.id.cbPremium);
        btnBook = findViewById(R.id.btnBook);
        btnViewHistory = findViewById(R.id.btnViewHistory);

        // Logic to make checkboxes behave like radio buttons (optional but often desired for types)
        cbStandard.setOnClickListener(v -> {
            if (cbStandard.isChecked()) cbPremium.setChecked(false);
        });
        cbPremium.setOnClickListener(v -> {
            if (cbPremium.isChecked()) cbStandard.setChecked(false);
        });

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movies);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovie.setAdapter(movieAdapter);

        ArrayAdapter<String> theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatres);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheatre.setAdapter(theatreAdapter);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTicket();
            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void bookTicket() {
        String movie = spinnerMovie.getSelectedItem().toString();
        String theatre = spinnerTheatre.getSelectedItem().toString();
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String time = hour + ":" + (minute < 10 ? "0" + minute : minute);

        String type = "";
        if (cbStandard.isChecked() && cbPremium.isChecked()) {
            type = "Standard & Premium";
        } else if (cbStandard.isChecked()) {
            type = "Standard";
        } else if (cbPremium.isChecked()) {
            type = "Premium";
        } else {
            Toast.makeText(this, "Please select a ticket type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Condition: Premium tickets allowed only after 12 PM
        if (type.contains("Premium") && hour < 12) {
            Toast.makeText(this, "Premium tickets are only available after 12 PM", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = db.insertData(movie, theatre, date, time, type);
        if (isInserted) {
            Toast.makeText(MainActivity.this, "Booking Confirmed for " + movie, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, BookingsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenu().add("View All Bookings");
        popup.getMenu().add("Clear History");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("View All Bookings")) {
                    startActivity(new Intent(MainActivity.this, BookingsActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Clear History feature coming soon!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popup.show();
    }
}
