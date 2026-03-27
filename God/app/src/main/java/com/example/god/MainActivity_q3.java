package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_q3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_q3);

        Button btn2 = findViewById(R.id.btnGoToActivity2_q3);
        Button btn3 = findViewById(R.id.btnGoToActivity3_q3);

        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_q3.this, SecondActivity_q3.class);
            startActivity(intent);
        });

        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_q3.this, ThirdActivity_q3.class);
            startActivity(intent);
        });
    }
}