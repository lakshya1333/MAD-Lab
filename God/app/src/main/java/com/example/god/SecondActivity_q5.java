package com.example.god;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Calendar;

public class SecondActivity_q5 extends AppCompatActivity {

    private TextView tvSelectedItems;
    private DatePicker datePicker;
    private ArrayList<String> items;
    private String stay, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_q5);

        Toolbar toolbar = findViewById(R.id.toolbar_q5_second);
        setSupportActionBar(toolbar);

        tvSelectedItems = findViewById(R.id.tvSelectedItems_q5);
        datePicker = findViewById(R.id.datePicker_q5);

        items = getIntent().getStringArrayListExtra("items");
        stay = getIntent().getStringExtra("stay");
        gender = getIntent().getStringExtra("gender");

        StringBuilder sb = new StringBuilder();
        if (items != null) {
            for (String item : items) {
                sb.append(item).append("\n");
            }
        }
        tvSelectedItems.setText(sb.toString());

        // Date constraint: greater than today's date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Tomorrow
        datePicker.setMinDate(calendar.getTimeInMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_q5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_date_q5) {
            showSummary();
            return true;
        } else if (id == R.id.action_back_q5) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSummary() {
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        String summary = "Stay: " + stay + "\n" +
                         "Gender: " + gender + "\n" +
                         "Selected: " + items.toString() + "\n" +
                         "Date: " + date;
        
        Toast.makeText(this, "Order Summary:\n" + summary, Toast.LENGTH_LONG).show();
    }
}