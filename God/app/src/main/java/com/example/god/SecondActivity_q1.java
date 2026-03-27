package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity_q1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_q1);

        EditText etString = findViewById(R.id.etString_q1);
        Button btnCount = findViewById(R.id.btnCount_q1);
        TextView tvVowels = findViewById(R.id.tvVowels_q1);
        TextView tvConsonants = findViewById(R.id.tvConsonants_q1);
        Button btnPrev = findViewById(R.id.btnPrev2_q1);
        Button btnNext = findViewById(R.id.btnNext2_q1);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etString.getText().toString().toLowerCase();
                int vowels = 0, consonants = 0;
                for (int i = 0; i < input.length(); i++) {
                    char ch = input.charAt(i);
                    if (ch >= 'a' && ch <= 'z') {
                        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                            vowels++;
                        } else {
                            consonants++;
                        }
                    }
                }
                tvVowels.setText("Vowels: " + vowels);
                tvConsonants.setText("Consonants: " + consonants);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity_q1.this, ThirdActivity_q1.class);
                startActivity(intent);
            }
        });
    }
}