package com.example.lab2_exam_calc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.resultText);

        String result = getIntent().getStringExtra("result");
        if (result != null) {
            resultText.setText(result);
        }
    }

    public void goBack(View view) {
        finish();
    }
}
