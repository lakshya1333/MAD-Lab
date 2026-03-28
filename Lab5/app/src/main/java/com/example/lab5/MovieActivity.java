package com.example.lab5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MovieActivity extends AppCompatActivity {

    Spinner movie, theatre;
    DatePicker datePicker;
    TimePicker timePicker;
    ToggleButton toggle;
    Button book, reset;

    String[] movies = {"Avengers", "Inception", "Interstellar", "Jawan"};
    String[] theatres = {"PVR", "INOX", "Cinepolis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movie = findViewById(R.id.spinnerMovie);
        theatre = findViewById(R.id.spinnerTheatre);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        toggle = findViewById(R.id.toggleTicket);
        book = findViewById(R.id.btnBook);
        reset = findViewById(R.id.btnReset);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, movies);

        ArrayAdapter<String> tAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, theatres);

        movie.setAdapter(mAdapter);
        theatre.setAdapter(tAdapter);

        // Disable past dates
        Calendar cal = Calendar.getInstance();
        datePicker.setMinDate(cal.getTimeInMillis());

        // 🔒 Premium logic
        toggle.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                if (hour < 12) {
                    book.setEnabled(false);
                    Toast.makeText(this,
                            "Premium booking allowed only after 12 PM",
                            Toast.LENGTH_SHORT).show();
                } else {
                    book.setEnabled(true);
                }
            } else {
                book.setEnabled(true);
            }
        });

        // ✅ Book Button
        book.setOnClickListener(v -> {

            String m = movie.getSelectedItem().toString();
            String t = theatre.getSelectedItem().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            int hour = timePicker.getHour();
            int min = timePicker.getMinute();

            String ticketType = toggle.isChecked() ? "Premium" : "Standard";

            String bookingDetails = m + " | " + t + " | " + day + "/" + month + "/" + year + " | " + hour + ":" + String.format("%02d", min) + " | " + ticketType;

            // Save to History
            SharedPreferences pref = getSharedPreferences("MovieHistory", MODE_PRIVATE);
            String history = pref.getString("bookings", "");
            history = bookingDetails + "\n" + history;
            pref.edit().putString("bookings", history).apply();

            Intent intent = new Intent(this, MovieSummaryActivity.class);
            intent.putExtra("movie", m);
            intent.putExtra("theatre", t);
            intent.putExtra("date", day + "/" + month + "/" + year);
            intent.putExtra("time", hour + ":" + String.format("%02d", min));
            intent.putExtra("type", ticketType);

            startActivity(intent);
        });

        // 🔄 Reset
        reset.setOnClickListener(v -> {
            movie.setSelection(0);
            theatre.setSelection(0);
            Calendar c = Calendar.getInstance();
            datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            toggle.setChecked(false);
            book.setEnabled(true);
        });
    }
}