package com.example.androidmastertemplate;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Page2_Input.java — Main Input Form
 *
 * Components: EditText, Spinner, SeekBar, CheckBox, RadioGroup,
 *             DatePicker, TimePicker dialogs, ImageView placeholder
 *
 * Navigation: Prev → Page1_Auth (or finish())
 *             Next → Page3_Details
 */
public class Page2_Input extends AppCompatActivity {

    // ── UI References ─────────────────────────────────────
    private EditText    etFullName, etAge, etNotes;     // TODO: EXAM_MODIFY — rename fields
    private Spinner     spinnerCategory;                // TODO: EXAM_MODIFY — rename/repopulate
    private SeekBar     seekBarScore;                   // TODO: EXAM_MODIFY — rename
    private TextView    tvSeekBarValue, tvSelectedDate, tvSelectedTime;
    private CheckBox    cbTerms, cbNewsletter;          // TODO: EXAM_MODIFY — rename checkboxes
    private RadioGroup  rgPriority;                     // TODO: EXAM_MODIFY — rename radio group
    private RadioButton rbLow, rbMedium, rbHigh;        // TODO: EXAM_MODIFY — rename radio buttons
    private Button      btnPickDate, btnPickTime, btnNext, btnPrev;
    private ImageView   ivProfileImage;                 // TODO: EXAM_MODIFY — rename

    // ── Helpers ───────────────────────────────────────────
    private ValidationHelper v;

    // ── Data from Page 1 ──────────────────────────────────
    private long   userId;
    private String username;

    // ── Stored Values ─────────────────────────────────────
    private String selectedDate = "";
    private String selectedTime = "";
    private int    currentScore = 0;  // TODO: EXAM_MODIFY — rename variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_input);

        // Receive extras from Page 1
        userId   = getIntent().getLongExtra("USER_ID",  -1);
        username = getIntent().getStringExtra("USERNAME"); // TODO: EXAM_MODIFY — add more extras

        v = new ValidationHelper(this);

        bindViews();
        setupSpinner();
        setupSeekBar();
        setupDateTimePickers();
        setupNavigation();
    }

    private void bindViews() {
        etFullName       = findViewById(R.id.etFullName);
        etAge            = findViewById(R.id.etAge);
        etNotes          = findViewById(R.id.etNotes);
        spinnerCategory  = findViewById(R.id.spinnerCategory);
        seekBarScore     = findViewById(R.id.seekBarScore);
        tvSeekBarValue   = findViewById(R.id.tvSeekBarValue);
        tvSelectedDate   = findViewById(R.id.tvSelectedDate);
        tvSelectedTime   = findViewById(R.id.tvSelectedTime);
        cbTerms          = findViewById(R.id.cbTerms);
        cbNewsletter     = findViewById(R.id.cbNewsletter);
        rgPriority       = findViewById(R.id.rgPriority);
        rbLow            = findViewById(R.id.rbLow);
        rbMedium         = findViewById(R.id.rbMedium);
        rbHigh           = findViewById(R.id.rbHigh);
        btnPickDate      = findViewById(R.id.btnPickDate);
        btnPickTime      = findViewById(R.id.btnPickTime);
        btnNext          = findViewById(R.id.btnNext);
        btnPrev          = findViewById(R.id.btnPrev);
        ivProfileImage   = findViewById(R.id.ivProfileImage);
    }

    // ── Spinner Setup ─────────────────────────────────────
    private void setupSpinner() {
        // TODO: EXAM_MODIFY — Replace these strings with your categories
        String[] categories = {"Select Category", "Category A", "Category B", "Category C", "Category D"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int pos, long id) {
                if (pos > 0) {
                    // TODO: EXAM_MODIFY — React to selection if needed
                    String selected = parent.getItemAtPosition(pos).toString();
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ── SeekBar Setup ─────────────────────────────────────
    private void setupSeekBar() {
        seekBarScore.setMax(100); // TODO: EXAM_MODIFY — Change max value
        seekBarScore.setProgress(0);
        tvSeekBarValue.setText("Score: 0"); // TODO: EXAM_MODIFY — Update label

        seekBarScore.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentScore = progress; // TODO: EXAM_MODIFY — rename variable
                tvSeekBarValue.setText("Score: " + progress); // TODO: EXAM_MODIFY — update label
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // ── Date & Time Pickers ───────────────────────────────
    private void setupDateTimePickers() {
        btnPickDate.setOnClickListener(view -> showDatePicker());
        btnPickTime.setOnClickListener(view -> showTimePicker());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (datePicker, year, month, day) -> {
                    // TODO: EXAM_MODIFY — Adjust date format as needed
                    selectedDate = day + "/" + (month + 1) + "/" + year;
                    tvSelectedDate.setText("Date: " + selectedDate);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(
                this,
                (timePicker, hour, minute) -> {
                    // TODO: EXAM_MODIFY — Adjust time format as needed
                    selectedTime = String.format("%02d:%02d", hour, minute);
                    tvSelectedTime.setText("Time: " + selectedTime);
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true // 24-hour format; set false for AM/PM
        ).show();
    }

    // ── Navigation ────────────────────────────────────────
    private void setupNavigation() {
        btnPrev.setOnClickListener(view -> {
            finish(); // Go back to Page 1
        });

        btnNext.setOnClickListener(view -> {
            if (!validatePage2()) return;

            // Threshold check before proceeding
            // TODO: EXAM_MODIFY — Change threshold (50) and messages
            v.checkThreshold(currentScore, 50,
                    "High Score Warning",               // Dialog title
                    "Score is above 50. Are you sure you want to continue?", // Message
                    this::navigateToPage3               // Proceed callback
            );
        });

        // ── ImageView — tap to set placeholder image ──────
        // TODO: EXAM_MODIFY — Replace with image picker Intent for real use
        ivProfileImage.setOnClickListener(view -> {
            // Quick placeholder — in real exam, launch image picker:
            // Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // startActivityForResult(i, IMAGE_PICK_CODE);
            ivProfileImage.setImageResource(android.R.drawable.ic_menu_gallery);
            v.showToast("Image selected (placeholder)");
        });
    }

    // ── Validation ────────────────────────────────────────
    private boolean validatePage2() {
        if (!v.isNotEmpty(etFullName, "Full Name")) return false;
        if (!v.isNotEmpty(etAge,      "Age"))       return false;
        if (!v.isInRange(etAge, "Age", 1, 120))     return false; // TODO: EXAM_MODIFY — age range

        if (spinnerCategory.getSelectedItemPosition() == 0) {
            v.showToast("Please select a category.");
            return false;
        }
        if (!cbTerms.isChecked()) {  // TODO: EXAM_MODIFY — remove if no terms checkbox
            v.showToast("You must accept the terms.");
            return false;
        }
        if (rgPriority.getCheckedRadioButtonId() == -1) { // TODO: EXAM_MODIFY — remove if no radio
            v.showToast("Please select a priority.");
            return false;
        }
        return true;
    }

    // ── Navigate to Page 3 ────────────────────────────────
    private void navigateToPage3() {
        Intent intent = new Intent(this, Page3_Details.class);

        // Pass everything forward
        intent.putExtra("USER_ID",   userId);
        intent.putExtra("USERNAME",  username);
        intent.putExtra("FULL_NAME", etFullName.getText().toString().trim());
        intent.putExtra("AGE",       etAge.getText().toString().trim());
        intent.putExtra("NOTES",     etNotes.getText().toString().trim());
        intent.putExtra("CATEGORY",  spinnerCategory.getSelectedItem().toString());
        intent.putExtra("SCORE",     currentScore);    // TODO: EXAM_MODIFY — rename key
        intent.putExtra("DATE",      selectedDate);
        intent.putExtra("TIME",      selectedTime);
        intent.putExtra("IS_NEWSLETTER", cbNewsletter.isChecked());

        // Get selected radio button label
        int radioId = rgPriority.getCheckedRadioButtonId();
        RadioButton selected = findViewById(radioId);
        intent.putExtra("PRIORITY", selected != null ? selected.getText().toString() : ""); // TODO: EXAM_MODIFY

        startActivity(intent);
    }
}