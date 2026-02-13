package com.example.lab1;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView helloText;
    Button changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        helloText = findViewById(R.id.helloText);
        changeBtn = findViewById(R.id.changeBtn);

        // Modify Hello World Text on Button Click
        changeBtn.setOnClickListener(v -> {
            helloText.setText("Text Modified Successfully!");
            helloText.setTextColor(Color.GREEN);
            helloText.setTextSize(28);
        });
    }
}