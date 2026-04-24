package com.example.hospital_db;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

/**
 * Main activity for the Hospital App.
 * Handles patient registration, scheduling, and database operations.
 */
public class MainActivity extends AppCompatActivity {

    // UI COMPONENTS
    private EditText nameInput;
    private RadioGroup genderGroup;
    private Spinner deptSpinner;
    private ToggleButton appointmentToggle;
    private TextView dateDisplay;
    private Button datePickerBtn, clearBtn, proceedBtn, editBtn;

    // DATABASE HELPER
    private DBHelper dbHelper;

    // APP STATE
    private int currentPatientId = -1; 
    private String selectedDateTime = "";

    // DEPARTMENT LISTS
    private final String[] group1Depts = {"Orthopedics", "Cardiology", "ENT"};
    private final String[] group2Depts = {"Neurology", "Dermatology", "Pediatrics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INITIALIZE UI COMPONENTS
        nameInput = findViewById(R.id.name);
        genderGroup = findViewById(R.id.genderGroup);
        deptSpinner = findViewById(R.id.spinner);
        appointmentToggle = findViewById(R.id.toggle);
        dateDisplay = findViewById(R.id.dateText);

        datePickerBtn = findViewById(R.id.dateBtn);
        clearBtn = findViewById(R.id.clearBtn);
        proceedBtn = findViewById(R.id.proceedBtn);
        editBtn = findViewById(R.id.editBtn);

        dbHelper = new DBHelper(this);

        // DATE AND TIME SELECTION LOGIC
        datePickerBtn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            // REQUIREMENT: If Appointment → allow only after 10 days from now
            if (appointmentToggle.isChecked()) {
                calendar.add(Calendar.DAY_OF_MONTH, 10);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);

                // REQUIREMENT: Spinner departments based on day of week
                // Mon (2), Tue (3), Wed (4) -> Group 1
                if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.WEDNESDAY) {
                    deptSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, group1Depts));
                } 
                // Thu (5), Fri (6), Sat (7) or Sun (1) -> Group 2
                else {
                    deptSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, group2Depts));
                }

                // TIME PICKER LOGIC
                new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                    // REQUIREMENT: Time must be before 5 PM (17:00)
                    if (hourOfDay >= 17) {
                        Toast.makeText(this, "Hospital closes at 5 PM. Please select an earlier time.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    selectedDateTime = dayOfMonth + "/" + (month + 1) + "/" + year + " " + String.format("%02d:%02d", hourOfDay, minute);
                    dateDisplay.setText(selectedDateTime);

                }, 12, 0, true).show();

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            // Set minimum selectable date based on toggle (today for OPD, +10 days for Appointment)
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });

        // CLEAR ALL FIELDS LOGIC
        clearBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear All Data")
                    .setMessage("Are you sure you want to reset all fields?")
                    .setPositiveButton("Yes", (dialog, which) -> resetFields())
                    .setNegativeButton("No", null)
                    .show();
        });

        // REGISTER PROCEED BUTTON FOR CONTEXT MENU
        registerForContextMenu(proceedBtn);

        // EDIT DATA LOGIC (Allows updating name for the current patient session)
        editBtn.setOnClickListener(v -> {
            if (currentPatientId == -1) {
                Toast.makeText(this, "Please generate a Patient ID first.", Toast.LENGTH_SHORT).show();
                return;
            }

            String newName = nameInput.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.updateData(currentPatientId, newName);
            Toast.makeText(this, "Patient data updated in database!", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Resets all UI fields to their default states.
     */
    private void resetFields() {
        nameInput.setText("");
        genderGroup.clearCheck();
        deptSpinner.setAdapter(null);
        appointmentToggle.setChecked(false);
        dateDisplay.setText("Select Date");
        selectedDateTime = "";
        currentPatientId = -1;
    }

    // CONTEXT MENU CREATION
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        menu.setHeaderTitle("Select Action");
        menu.add(0, 1, 0, "Generate Patient ID");
        menu.add(0, 2, 0, "Save Data to Database");
        menu.add(0, 3, 0, "Next (View Summary)");
        menu.add(0, 4, 0, "Delete Patient Record"); // Additional CRUD functionality
    }

    // CONTEXT MENU SELECTION HANDLING
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: // GENERATE RANDOM ID
                currentPatientId = new Random().nextInt(9000) + 1000; // 4-digit ID
                Toast.makeText(this, "Generated Patient ID: " + currentPatientId, Toast.LENGTH_LONG).show();
                return true;

            case 2: // SAVE DATA
                if (validateInputs()) {
                    String name = nameInput.getText().toString();
                    RadioButton selectedGender = findViewById(genderGroup.getCheckedRadioButtonId());
                    String gender = selectedGender.getText().toString();
                    String dept = deptSpinner.getSelectedItem().toString();

                    dbHelper.insertData(currentPatientId, name, gender, dept, selectedDateTime);
                    Toast.makeText(this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                }
                return true;

            case 3: // NEXT ACTIVITY
                if (currentPatientId == -1 || selectedDateTime.isEmpty()) {
                    Toast.makeText(this, "Please complete selection and save data first.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("id", currentPatientId);
                intent.putExtra("name", nameInput.getText().toString());
                intent.putExtra("date", selectedDateTime);
                startActivity(intent);
                return true;

            case 4: // DELETE DATA
                if (currentPatientId == -1) {
                    Toast.makeText(this, "No active patient session to delete.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                dbHelper.deleteData(currentPatientId);
                Toast.makeText(this, "Patient record deleted.", Toast.LENGTH_SHORT).show();
                resetFields();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Validates that all required information is provided.
     */
    private boolean validateInputs() {
        if (currentPatientId == -1) {
            Toast.makeText(this, "Generate Patient ID first.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nameInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Patient Name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select Gender.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedDateTime.isEmpty()) {
            Toast.makeText(this, "Select Date and Time.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (deptSpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Select Department.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
