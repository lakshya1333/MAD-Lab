package com.example.androidmastertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Page3_Details.java — Secondary Details / Confirmation
 *
 * Receives data from Page 2, allows extra input,
 * saves everything to SQLite, then navigates to Page 4.
 *
 * TODO: EXAM_MODIFY — Add any extra fields your exam requires here
 */
public class Page3_Details extends AppCompatActivity {

    // ── UI References ─────────────────────────────────────
    private TextView tvSummaryPreview;        // Show what was entered on Page 2
    private EditText etEventTitle, etEventDesc; // TODO: EXAM_MODIFY — rename to match domain
    private Button   btnSave, btnPrev, btnNext;

    // ── Helpers ───────────────────────────────────────────
    private DatabaseHelper  db;
    private ValidationHelper v;

    // ── Data from previous pages ──────────────────────────
    private long   userId;
    private String username, fullName, age, notes, category, date, time, priority;
    private int    score;
    private boolean isNewsletter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3_details);

        db = DatabaseHelper.getInstance(this);
        v  = new ValidationHelper(this);

        receiveIntentData();
        bindViews();
        populateSummaryPreview();
        setupNavigation();
    }

    // ── Receive all extras from Page 2 ───────────────────
    private void receiveIntentData() {
        Intent i  = getIntent();
        userId       = i.getLongExtra("USER_ID",   -1);
        username     = i.getStringExtra("USERNAME");
        fullName     = i.getStringExtra("FULL_NAME");
        age          = i.getStringExtra("AGE");
        notes        = i.getStringExtra("NOTES");
        category     = i.getStringExtra("CATEGORY");
        score        = i.getIntExtra("SCORE", 0);        // TODO: EXAM_MODIFY
        date         = i.getStringExtra("DATE");
        time         = i.getStringExtra("TIME");
        priority     = i.getStringExtra("PRIORITY");
        isNewsletter = i.getBooleanExtra("IS_NEWSLETTER", false);
    }

    private void bindViews() {
        tvSummaryPreview = findViewById(R.id.tvSummaryPreview);
        etEventTitle     = findViewById(R.id.etEventTitle);   // TODO: EXAM_MODIFY — rename
        etEventDesc      = findViewById(R.id.etEventDesc);    // TODO: EXAM_MODIFY — rename
        btnSave          = findViewById(R.id.btnSave);
        btnPrev          = findViewById(R.id.btnPrev);
        btnNext          = findViewById(R.id.btnNext);
    }

    // ── Show carried-over data for confirmation ───────────
    private void populateSummaryPreview() {
        // TODO: EXAM_MODIFY — Format this summary however you like
        String preview =
                "User:     " + username   + "\n" +
                        "Name:     " + fullName   + "\n" +
                        "Age:      " + age        + "\n" +
                        "Category: " + category   + "\n" +
                        "Score:    " + score      + "\n" +  // TODO: EXAM_MODIFY
                        "Date:     " + date       + "\n" +
                        "Time:     " + time       + "\n" +
                        "Priority: " + priority;
        tvSummaryPreview.setText(preview);
    }

    private void setupNavigation() {
        btnPrev.setOnClickListener(view -> finish()); // Back to Page 2

        btnSave.setOnClickListener(view -> saveToDatabase());

        btnNext.setOnClickListener(view -> {
            if (!validatePage3()) return;
            saveToDatabase(); // Auto-save on Next
        });
    }

    // ── Validation ────────────────────────────────────────
    private boolean validatePage3() {
        if (!v.isNotEmpty(etEventTitle, "Title")) return false; // TODO: EXAM_MODIFY
        return true;
    }

    // ── Database Save ─────────────────────────────────────
    private void saveToDatabase() {
        if (!validatePage3()) return;

        String title = etEventTitle.getText().toString().trim(); // TODO: EXAM_MODIFY
        String desc  = etEventDesc.getText().toString().trim();  // TODO: EXAM_MODIFY

        // Combine date and time if needed, or pass separately
        String fullDate = date + (time.isEmpty() ? "" : " " + time);

        long eventId = db.insertEvent(title, desc, score, fullDate, userId); // TODO: EXAM_MODIFY — adjust params

        if (eventId == -1) {
            v.showToast("Save failed. Please try again.");
            return;
        }

        v.showToast("Saved successfully!");
        navigateToPage4(eventId);
    }

    // ── Navigate to Page 4 ────────────────────────────────
    private void navigateToPage4(long eventId) {
        Intent intent = new Intent(this, Page4_Summary.class);
        intent.putExtra("USER_ID",   userId);   // TODO: EXAM_MODIFY — pass what Page4 needs
        intent.putExtra("USERNAME",  username);
        intent.putExtra("EVENT_ID",  eventId);
        startActivity(intent);
    }
}
