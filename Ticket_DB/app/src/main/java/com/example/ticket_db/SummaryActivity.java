package com.example.ticket_db;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// This activity displays the booking summary and allows for editing/updating details
public class SummaryActivity extends AppCompatActivity {

    // UI components
    EditText etSumName;
    TextView tvDetails;
    Button btnEditUpdate, btnSubmitFinal;
    DatabaseHelper dbHelper;
    String ticketId; // To store the ID of the record being displayed

    // Variables to store data fetched from database
    int tickets;
    String type, date, pref;
    double cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary); // Link layout

        // Initialize UI components
        etSumName = findViewById(R.id.etSumName);
        tvDetails = findViewById(R.id.tvDetails);
        btnEditUpdate = findViewById(R.id.btnEditUpdate);
        btnSubmitFinal = findViewById(R.id.btnSubmitFinal);
        dbHelper = new DatabaseHelper(this);

        // Get the ticket ID passed from MainActivity
        ticketId = getIntent().getStringExtra("ID");
        
        // Load data from database using the ID
        loadData();

        // Click listener for Update button
        btnEditUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated name from the EditText
                String newName = etSumName.getText().toString();
                
                // Update the record in the database
                boolean isUpdated = dbHelper.updateData(ticketId, newName, tickets, type, date, pref, cost);
                
                if (isUpdated) {
                    Toast.makeText(SummaryActivity.this, "Data Updated in Database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SummaryActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Click listener for Final Submit button
        btnSubmitFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation message and close activity
                Toast.makeText(SummaryActivity.this, "Final Submission Successful", Toast.LENGTH_LONG).show();
                finish(); // Close this activity
            }
        });
    }

    // Function to fetch data from database using the ticketId and display it
    private void loadData() {
        android.database.sqlite.SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Query to find the specific record with the given ID
        Cursor res = db.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " WHERE ID = ?", new String[]{ticketId});

        // If data is found in the cursor
        if (res.moveToFirst()) {
            // Fetch name and display in editable field
            etSumName.setText(res.getString(1));
            
            // Fetch other details to store in variables
            tickets = res.getInt(2);
            type = res.getString(3);
            date = res.getString(4);
            pref = res.getString(5);
            cost = res.getDouble(6);

            // Construct details string for display
            StringBuilder details = new StringBuilder();
            details.append("Tickets: ").append(tickets).append("\n");
            details.append("Type: ").append(type).append("\n");
            details.append("Date: ").append(date).append("\n");
            details.append("Preference: ").append(pref).append("\n");
            details.append("Total Cost: ").append(cost);
            
            // Set details text to the TextView
            tvDetails.setText(details.toString());
        }
        res.close(); // Close cursor to free memory
    }
}