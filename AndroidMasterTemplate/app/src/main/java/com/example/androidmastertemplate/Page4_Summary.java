package com.example.androidmastertemplate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Page4_Summary.java — Final Summary / Results Screen
 *
 * Displays joined database results in a ListView.
 * Provides Prev navigation and a Restart/Logout option.
 *
 * TODO: EXAM_MODIFY — Change what columns are displayed in the list
 */
public class Page4_Summary extends AppCompatActivity {

    // ── UI References ─────────────────────────────────────
    private TextView tvWelcome, tvStats;  // TODO: EXAM_MODIFY — rename
    private ListView lvResults;           // Displays JOIN query results
    private Button   btnPrev, btnRestart, btnLogout;

    // ── Helpers ───────────────────────────────────────────
    private DatabaseHelper  db;
    private ValidationHelper v;

    // ── Data received ─────────────────────────────────────
    private long   userId;
    private String username;
    private long   eventId;   // TODO: EXAM_MODIFY — may not need this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4_summary);

        db = DatabaseHelper.getInstance(this);
        v  = new ValidationHelper(this);

        userId   = getIntent().getLongExtra("USER_ID",  -1);
        username = getIntent().getStringExtra("USERNAME");
        eventId  = getIntent().getLongExtra("EVENT_ID", -1);

        bindViews();
        loadAndDisplayData();
        setupNavigation();
    }

    private void bindViews() {
        tvWelcome  = findViewById(R.id.tvWelcome);
        tvStats    = findViewById(R.id.tvStats);
        lvResults  = findViewById(R.id.lvResults);
        btnPrev    = findViewById(R.id.btnPrev);
        btnRestart = findViewById(R.id.btnRestart);
        btnLogout  = findViewById(R.id.btnLogout);
    }

    // ── Load Data from DB (JOIN query) ────────────────────
    private void loadAndDisplayData() {
        tvWelcome.setText("Summary for: " + username); // TODO: EXAM_MODIFY — greeting text

        // ── Option A: Show ALL users' joined data ─────────
        // Cursor cursor = db.getJoinedUserEvents();

        // ── Option B: Show only THIS user's events ────────
        // TODO: EXAM_MODIFY — choose Option A or B, or use aggregate
        Cursor cursor = db.getJoinedEventsByUserId(userId);

        List<String> displayList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // TODO: EXAM_MODIFY — Change which columns to show and formatting
                String username_col  = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String title_col     = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String score_col     = cursor.getString(cursor.getColumnIndexOrThrow("score"));
                String date_col      = cursor.getString(cursor.getColumnIndexOrThrow("event_date"));

                // Format each list row — TODO: EXAM_MODIFY the format string
                String row = "📌 " + title_col
                        + "\n   Score: " + score_col
                        + " | Date: " + date_col;
                displayList.add(row);
            } while (cursor.moveToNext());
            cursor.close();
        }

        if (displayList.isEmpty()) {
            displayList.add("No records found for this user.");
        }

        // ── Populate ListView ─────────────────────────────
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,  // TODO: EXAM_MODIFY — use custom layout for richer rows
                displayList
        );
        lvResults.setAdapter(adapter);

        // ── Aggregate Stats (optional) ────────────────────
        loadStats();
    }

    // ── Load Aggregate Stats ──────────────────────────────
    private void loadStats() {
        // TODO: EXAM_MODIFY — Change this to show relevant stats
        Cursor statsCursor = db.getAverageScorePerUser();
        StringBuilder statsText = new StringBuilder("Stats:\n");

        if (statsCursor != null && statsCursor.moveToFirst()) {
            do {
                String user     = statsCursor.getString(statsCursor.getColumnIndexOrThrow("username"));
                String avgScore = statsCursor.getString(statsCursor.getColumnIndexOrThrow("avg_score"));
                String total    = statsCursor.getString(statsCursor.getColumnIndexOrThrow("total_events"));
                statsText.append(user)
                        .append(": avg=").append(avgScore)
                        .append(", total=").append(total).append("\n");
            } while (statsCursor.moveToNext());
            statsCursor.close();
        }
        tvStats.setText(statsText.toString());
    }

    // ── Navigation ────────────────────────────────────────
    private void setupNavigation() {
        btnPrev.setOnClickListener(view -> finish()); // Back to Page 3

        btnRestart.setOnClickListener(view ->
                v.showConfirmDialog(
                        "Restart",
                        "Start a new entry? Current data will be kept in DB.",
                        this::restartFromPage2
                )
        );

        btnLogout.setOnClickListener(view ->
                v.showConfirmDialog(
                        "Logout",
                        "Are you sure you want to logout?",
                        this::logout
                )
        );

        // ── ListView item click (optional detail view) ────
        lvResults.setOnItemClickListener((parent, view, position, id) -> {
            // TODO: EXAM_MODIFY — Navigate to Page5 or show detail dialog
            String item = (String) parent.getItemAtPosition(position);
            v.showInfoDialog("Record Detail", item);
        });
    }

    private void restartFromPage2() {
        Intent intent = new Intent(this, Page2_Input.class);
        intent.putExtra("USER_ID",  userId);
        intent.putExtra("USERNAME", username);
        // Clear back stack so Prev from Page2 doesn't loop back to Summary
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, Page1_Auth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Prevent accidental back — show confirmation
        // TODO: EXAM_MODIFY — Remove this override if back navigation is acceptable
        v.showConfirmDialog("Go Back", "Return to the previous page?",
                super::onBackPressed);
    }
}
