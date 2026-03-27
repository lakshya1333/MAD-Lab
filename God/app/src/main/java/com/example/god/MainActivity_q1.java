package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_q1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_q1);

        EditText etNumber = findViewById(R.id.etNumber_q1);
        RadioGroup rgChecker = findViewById(R.id.rgChecker_q1);
        RadioButton rbPrime = findViewById(R.id.rbPrime_q1);
        Button btnCheck = findViewById(R.id.btnCheck_q1);
        TextView tvResult = findViewById(R.id.tvResult1_q1);
        Button btnNext = findViewById(R.id.btnNext1_q1);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNumber.getText().toString();
                if (input.isEmpty()) {
                    tvResult.setText("Please enter a number");
                    return;
                }
                int num = Integer.parseInt(input);
                if (rbPrime.isChecked()) {
                    if (isPrime(num)) tvResult.setText(num + " is a Prime number");
                    else tvResult.setText(num + " is not a Prime number");
                } else {
                    if (isPerfectSquare(num)) tvResult.setText(num + " is a Perfect Square");
                    else tvResult.setText(num + " is not a Perfect Square");
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_q1.this, SecondActivity_q1.class);
                startActivity(intent);
            }
        });
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private boolean isPerfectSquare(int n) {
        if (n < 0) return false;
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }
}