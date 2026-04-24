package com.example.docter_db;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView textAllData;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        myDb = new DatabaseHelper(this);
        textAllData = findViewById(R.id.textAllData);
        btnBack = findViewById(R.id.btnBack);

        displayData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            textAllData.setText("No appointments found.");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("ID: ").append(res.getString(0)).append("\n");
            buffer.append("Patient: ").append(res.getString(1)).append("\n");
            buffer.append("Doctor: ").append(res.getString(2)).append("\n");
            buffer.append("Date: ").append(res.getString(3)).append("\n");
            buffer.append("Time: ").append(res.getString(4)).append("\n");
            buffer.append("Type: ").append(res.getString(5)).append("\n\n");
            buffer.append("---------------------------\n\n");
        }
        textAllData.setText(buffer.toString());
    }
}
