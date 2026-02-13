package com.example.lab4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);

        CheckBox pizza = findViewById(R.id.cbPizza);
        CheckBox burger = findViewById(R.id.cbBurger);
        Button submit = findViewById(R.id.btnSubmit);

        submit.setOnClickListener(v -> {
            int total = 0;
            String order = "";

            if (pizza.isChecked()) {
                total += 100;
                order += "Pizza ₹100\n";
                pizza.setEnabled(false);
            }
            if (burger.isChecked()) {
                total += 60;
                order += "Burger ₹60\n";
                burger.setEnabled(false);
            }

            Toast.makeText(this,
                    order + "Total: ₹" + total,
                    Toast.LENGTH_LONG).show();

            submit.setEnabled(false);
        });
    }
}

