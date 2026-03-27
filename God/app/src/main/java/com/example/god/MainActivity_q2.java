package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity_q2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_q2);

        CheckBox cbPizza = findViewById(R.id.cbPizza_q2);
        CheckBox cbBurger = findViewById(R.id.cbBurger_q2);
        CheckBox cbPasta = findViewById(R.id.cbPasta_q2);

        EditText etPizzaQty = findViewById(R.id.etPizzaQty_q2);
        EditText etBurgerQty = findViewById(R.id.etBurgerQty_q2);
        EditText etPastaQty = findViewById(R.id.etPastaQty_q2);

        Button btnOrder = findViewById(R.id.btnOrder_q2);

        btnOrder.setOnClickListener(v -> {
            ArrayList<String> orders = new ArrayList<>();

            if (cbPizza.isChecked()) {
                String qty = etPizzaQty.getText().toString();
                if (qty.isEmpty()) qty = "1";
                orders.add("Pizza: " + qty);
            }

            if (cbBurger.isChecked()) {
                String qty = etBurgerQty.getText().toString();
                if (qty.isEmpty()) qty = "1";
                orders.add("Burger: " + qty);
            }

            if (cbPasta.isChecked()) {
                String qty = etPastaQty.getText().toString();
                if (qty.isEmpty()) qty = "1";
                orders.add("Pasta: " + qty);
            }

            Intent intent = new Intent(MainActivity_q2.this, SecondActivity_q2.class);
            intent.putStringArrayListExtra("orders", orders);
            startActivity(intent);
        });
    }
}