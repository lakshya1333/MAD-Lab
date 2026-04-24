package com.example.batch1_c;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    TextView tvSummary, tvTotal, tvStudentName;
    Button btnBack;
    DatabaseHelper db;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        db = new DatabaseHelper(this);
        tvSummary = findViewById(R.id.tvSummaryContent);
        tvTotal = findViewById(R.id.tvTotalScore);
        tvStudentName = findViewById(R.id.tvStudentNameDisplay);
        btnBack = findViewById(R.id.btnBackToMain);

        studentId = getIntent().getIntExtra("student_id", -1);

        if (studentId != -1) {
            displayStudentInfo();
            displaySummary();
        } else {
            tvSummary.setText("Invalid Student ID");
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void displayStudentInfo() {
        Cursor cursor = db.getStudentById(studentId);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_S_NAME));
            String roll = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_S_ROLL));
            tvStudentName.setText("Student: " + name + " (" + roll + ")");
        }
        cursor.close();
    }

    private void displaySummary() {
        Cursor cursor = db.getStudentSummary(studentId);
        StringBuilder summary = new StringBuilder();
        if (cursor.getCount() == 0) {
            summary.append("No events registered yet.");
        } else {
            while (cursor.moveToNext()) {
                String eventName = cursor.getString(0);
                int score = cursor.getInt(1);
                summary.append("Event: ").append(eventName).append("\nScore: ").append(score).append("\n\n");
            }
        }
        cursor.close();
        tvSummary.setText(summary.toString());

        int totalScore = db.getTotalScore(studentId);
        tvTotal.setText("Total Score: " + totalScore);

        if (totalScore > 50) {
            Toast.makeText(this, "Excellent performance! Score > 50", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Keep participating to score more!", Toast.LENGTH_LONG).show();
        }
    }
}
