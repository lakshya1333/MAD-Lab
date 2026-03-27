package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity_q3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_q3);

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");

        EditText etEmail = findViewById(R.id.etEmail_q3);
        EditText etCity = findViewById(R.id.etCity_q3);
        Button btnFinish = findViewById(R.id.btnNextTo5_q3);

        btnFinish.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String city = etCity.getText().toString();

            Intent intent = new Intent(FourthActivity_q3.this, FifthActivity_q3.class);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            intent.putExtra("email", email);
            intent.putExtra("city", city);
            startActivity(intent);
        });
    }
}