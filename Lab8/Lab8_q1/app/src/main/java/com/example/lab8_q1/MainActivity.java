package com.example.lab8_q1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    EditText name, date;
    Spinner priority;
    Button save, view;
    DBHelper db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        priority = findViewById(R.id.priority);
        save = findViewById(R.id.save);
        view = findViewById(R.id.view);

        db = new DBHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"High", "Medium", "Low"}
        );
        priority.setAdapter(adapter);

        save.setOnClickListener(v -> {
            Task t = new Task(
                    name.getText().toString(),
                    date.getText().toString(),
                    priority.getSelectedItem().toString()
            );
            db.insertTask(t);
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
        });

        view.setOnClickListener(v -> {
            startActivity(new Intent(this, TaskListActivity.class));
        });
    }
}