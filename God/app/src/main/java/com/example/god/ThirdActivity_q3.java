package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity_q3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_q3);

        EditText etName = findViewById(R.id.etName_q3);
        EditText etAge = findViewById(R.id.etAge_q3);
        Button btnNext = findViewById(R.id.btnNextTo4_q3);

        btnNext.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String age = etAge.getText().toString();

            Intent intent = new Intent(ThirdActivity_q3.this, FourthActivity_q3.class);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            startActivity(intent);
        });
    }
}