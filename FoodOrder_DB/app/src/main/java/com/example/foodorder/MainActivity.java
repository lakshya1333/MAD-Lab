package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    CheckBox cbPizza, cbBurger, cbPasta;
    EditText etQuantity;
    Button btnSubmit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbPasta = findViewById(R.id.cbPasta);
        etQuantity = findViewById(R.id.etQuantity);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder result = new StringBuilder();
                double total = 0;
                int quantity = 1;

                String qStr = etQuantity.getText().toString();
                if (!qStr.isEmpty()) {
                    quantity = Integer.parseInt(qStr);
                }

                if (cbPizza.isChecked()) {
                    result.append("Pizza ");
                    total += 10;
                }
                if (cbBurger.isChecked()) {
                    result.append("Burger ");
                    total += 5;
                }
                if (cbPasta.isChecked()) {
                    result.append("Pasta ");
                    total += 8;
                }

                if (result.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                    return;
                }

                total = total * quantity;
                String selectedItems = result.toString().trim();

                boolean isInserted = db.insertOrder(selectedItems, total);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                    
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putExtra("items", selectedItems);
                    intent.putExtra("total", total);
                    startActivity(intent);
                    
                    // Constraint: user cannot modify selection after submit
                    // We can finish this activity or disable buttons. Finishing is cleaner for a "flow".
                    // finish(); 
                } else {
                    Toast.makeText(MainActivity.this, "Order Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}