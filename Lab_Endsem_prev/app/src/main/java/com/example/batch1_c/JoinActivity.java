package com.example.batch1_c;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    EditText etSearchRoll, etScore;
    TextView tvStudentDetails;
    Spinner spEvents;
    Button btnSearch, btnRegister, btnGoToSummary;
    DatabaseHelper db;

    int selectedStudentId = -1;
    Map<Integer, Integer> eventMap = new HashMap<>(); // Spinner position -> Event ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        db = new DatabaseHelper(this);
        etSearchRoll = findViewById(R.id.etSearchRoll);
        etScore = findViewById(R.id.etScore);
        tvStudentDetails = findViewById(R.id.tvStudentDetails);
        spEvents = findViewById(R.id.spEvents);
        btnSearch = findViewById(R.id.btnSearchStudent);
        btnRegister = findViewById(R.id.btnRegisterToEvent);
        btnGoToSummary = findViewById(R.id.btnGoToSummary);

        loadEvents();

        btnSearch.setOnClickListener(v -> {
            String roll = etSearchRoll.getText().toString();
            Cursor cursor = db.searchStudent(roll);
            if (cursor.moveToFirst()) {
                selectedStudentId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_S_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_S_NAME));
                tvStudentDetails.setText("Name: " + name + " (ID: " + selectedStudentId + ")");
            } else {
                selectedStudentId = -1;
                tvStudentDetails.setText("Student not found");
            }
            cursor.close();
        });

        btnRegister.setOnClickListener(v -> {
            if (selectedStudentId == -1) {
                Toast.makeText(this, "Please search for a student first", Toast.LENGTH_SHORT).show();
                return;
            }

            int spinnerPos = spEvents.getSelectedItemPosition();
            if (spinnerPos == Spinner.INVALID_POSITION || eventMap.get(spinnerPos) == null) {
                Toast.makeText(this, "Please select an event", Toast.LENGTH_SHORT).show();
                return;
            }

            String scoreStr = etScore.getText().toString();
            if (scoreStr.isEmpty()) {
                Toast.makeText(this, "Please enter score", Toast.LENGTH_SHORT).show();
                return;
            }

            int eventId = eventMap.get(spinnerPos);
            int score = Integer.parseInt(scoreStr);

            long id = db.registerStudentToEvent(selectedStudentId, eventId, score);
            if (id != -1) {
                Toast.makeText(this, "Registered to event successfully", Toast.LENGTH_SHORT).show();
                etScore.setText("");
            } else {
                Toast.makeText(this, "Error registering to event", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToSummary.setOnClickListener(v -> {
            if (selectedStudentId == -1) {
                Toast.makeText(this, "Please search for a student first", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(JoinActivity.this, SummaryActivity.class);
            intent.putExtra("student_id", selectedStudentId);
            startActivity(intent);
        });
    }

    private void loadEvents() {
        Cursor cursor = db.getAllEvents();
        List<String> eventNames = new ArrayList<>();
        int i = 0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_E_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_E_NAME));
            eventNames.add(name);
            eventMap.put(i, id);
            i++;
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEvents.setAdapter(adapter);
    }
}
