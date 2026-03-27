package com.example.god;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FifthActivity_q3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_q3);

        TextView tvSummary = findViewById(R.id.tvSummary_q3);
        Button btnBack = findViewById(R.id.btnBackToHome_q3);

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String email = getIntent().getStringExtra("email");
        String city = getIntent().getStringExtra("city");

        String summary = "Name: " + name + "\n" +
                         "Age: " + age + "\n" +
                         "Email: " + email + "\n" +
                         "City: " + city;

        tvSummary.setText(summary);

        btnBack.setOnClickListener(v -> finish());
    }
}