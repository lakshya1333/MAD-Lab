package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    DatabaseHelper db;
    TextView tvSelectedItems, tvTotalCost;
    Button btnDeleteOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        db = new DatabaseHelper(this);
        tvSelectedItems = findViewById(R.id.tvSelectedItems);
        tvTotalCost = findViewById(R.id.tvTotalCost);
        btnDeleteOrder = findViewById(R.id.btnDeleteOrder);

        Intent intent = getIntent();
        String items = intent.getStringExtra("items");
        double total = intent.getDoubleExtra("total", 0.0);

        tvSelectedItems.setText("Items:\n" + items);
        tvTotalCost.setText("Total: $" + total);

        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOrder();
                Toast.makeText(Activity2.this, "Order Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}