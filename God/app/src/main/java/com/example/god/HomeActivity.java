package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnQ1 = findViewById(R.id.btnQ1);
        Button btnQ2 = findViewById(R.id.btnQ2);
        Button btnQ3 = findViewById(R.id.btnQ3);
        Button btnQ4 = findViewById(R.id.btnQ4);
        Button btnQ5 = findViewById(R.id.btnQ5);

        btnQ1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_q1.class);
            startActivity(intent);
        });

        btnQ2.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_q2.class);
            startActivity(intent);
        });

        btnQ3.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_q3.class);
            startActivity(intent);
        });

        btnQ4.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_q4.class);
            startActivity(intent);
        });

        btnQ5.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_q5.class);
            startActivity(intent);
        });
    }
}