package com.example.rishiq_prevlab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class EditActivity extends AppCompatActivity {
    private Order order;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DBHelper(this);
        order = (Order) getIntent().getSerializableExtra("order");

        TextView tvEditItem1 = findViewById(R.id.tvEditItem1);
        TextView tvEditItem2 = findViewById(R.id.tvEditItem2);
        EditText etQty1 = findViewById(R.id.etQty1);
        EditText etQty2 = findViewById(R.id.etQty2);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        if (order != null) {
            tvEditItem1.setText(order.getItem1());
            tvEditItem2.setText(order.getItem2());
            etQty1.setText(String.valueOf(order.getQty1()));
            etQty2.setText(String.valueOf(order.getQty2()));
        }

        btnSubmit.setOnClickListener(v -> {
            String q1Str = etQty1.getText().toString();
            String q2Str = etQty2.getText().toString();

            if (q1Str.isEmpty() || q2Str.isEmpty()) {
                Toast.makeText(this, "Enter quantities", Toast.LENGTH_SHORT).show();
                return;
            }

            int q1 = Integer.parseInt(q1Str);
            int q2 = Integer.parseInt(q2Str);
            int total = (order.getCost1() * q1) + (order.getCost2() * q2);

            dbHelper.updateOrder(order.getId(), q1, q2, total);

            int token = new Random().nextInt(9000) + 1000;
            Toast.makeText(this, "Token Generated: " + token, Toast.LENGTH_LONG).show();

            setResult(RESULT_OK);
            finish();
        });
    }
}
