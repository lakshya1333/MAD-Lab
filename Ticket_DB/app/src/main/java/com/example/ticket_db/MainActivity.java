package com.example.ticket_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaring UI components
    EditText etName, etTickets;
    Spinner spinnerType;
    DatePicker datePicker;
    RadioGroup rgSeat;
    Button btnBook;
    DatabaseHelper dbHelper; // Database helper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Setting the layout for this activity

        // Initializing UI components by linking them to their XML IDs
        etName = findViewById(R.id.etName);
        etTickets = findViewById(R.id.etTickets);
        spinnerType = findViewById(R.id.spinnerType);
        datePicker = findViewById(R.id.datePicker);
        rgSeat = findViewById(R.id.rgSeat);
        btnBook = findViewById(R.id.btnBook);
        
        // Initializing the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Setting up the Spinner with Ticket Types (Bus and Train)
        String[] types = {"Bus", "Train"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Setting a click listener for the 'Book' button
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTicket(); // Calling the booking function
            }
        });
    }

    // Function to handle the ticket booking logic
    private void bookTicket() {
        // Fetching user input from EditTexts
        String name = etName.getText().toString();
        String ticketsStr = etTickets.getText().toString();

        // Validation: Check if name or number of tickets is empty
        if (name.isEmpty() || ticketsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parsing the number of tickets to an integer
        int tickets = Integer.parseInt(ticketsStr);
        // Getting selected ticket type from Spinner
        String type = spinnerType.getSelectedItem().toString();
        // Getting selected date from DatePicker
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();

        // Getting the ID of the selected RadioButton for seat preference
        int selectedId = rgSeat.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Select seat preference", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rb = findViewById(selectedId);
        String pref = rb.getText().toString(); // "Window" or "Aisle"

        // Logic for total cost calculation based on seat preference
        // If Window: 50 per ticket, else (Aisle): 30 per ticket
        double prefCost = pref.equals("Window") ? 50.0 : 30.0;
        double totalCost = tickets * prefCost;

        // Inserting the booking data into the SQLite database
        long id = dbHelper.insertData(name, tickets, type, date, pref, totalCost);

        // If insertion is successful, show a Toast and move to the Summary page
        if (id != -1) {
            Toast.makeText(this, "Total Cost: " + totalCost, Toast.LENGTH_LONG).show();
            
            // Intent to navigate to SummaryActivity
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            // Passing the ID of the newly created booking to the next activity
            intent.putExtra("ID", String.valueOf(id));
            startActivity(intent);
        } else {
            // Error handling for database insertion
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_SHORT).show();
        }
    }
}