package com.example.batch1_c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etName, etRoll;
    Button btnRegister, btnGoToEvent;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.etStudentName);
        etRoll = findViewById(R.id.etRollNo);
        btnRegister = findViewById(R.id.btnRegisterStudent);
        btnGoToEvent = findViewById(R.id.btnGoToEventReg);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String roll = etRoll.getText().toString().trim();
            if (!name.isEmpty() && !roll.isEmpty()) {
                long id = db.insertStudent(name, roll);
                if (id != -1) {
                    Toast.makeText(this, "Student Registered Successfully", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etRoll.setText("");
                } else {
                    Toast.makeText(this, "Error or Roll No already exists", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToEvent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        });
    }
}
