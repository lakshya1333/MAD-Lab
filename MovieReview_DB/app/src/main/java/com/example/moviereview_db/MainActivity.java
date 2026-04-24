package com.example.moviereview_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etName, etYear, etRating;
    Button btnSave, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etYear = findViewById(R.id.etYear);
        etRating = findViewById(R.id.etRating);
        btnSave = findViewById(R.id.btnSave);
        btnViewAll = findViewById(R.id.btnViewAll);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String yearStr = etYear.getText().toString();
                String ratingStr = etRating.getText().toString();

                if (name.isEmpty() || yearStr.isEmpty() || ratingStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearStr);
                int rating = Integer.parseInt(ratingStr);

                if (rating < 1 || rating > 5) {
                    Toast.makeText(MainActivity.this, "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.insertData(name, year, rating)) {
                    Toast.makeText(MainActivity.this, "Review Saved", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etYear.setText("");
                    etRating.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewReviewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
