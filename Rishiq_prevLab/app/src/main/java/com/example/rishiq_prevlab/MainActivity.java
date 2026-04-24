package com.example.rishiq_prevlab;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvSelectedDate;
    private ListView lvOrders;
    private OrderAdapter adapter;
    private DBHelper dbHelper;
    private String selectedDate = "";
    private static final int EDIT_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        lvOrders = findViewById(R.id.lvOrders);
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnAddOrder = findViewById(R.id.btnAddOrder);

        adapter = new OrderAdapter(this, new ArrayList<>());
        lvOrders.setAdapter(adapter);

        loadAllData();

        btnDatePicker.setOnClickListener(v -> showDatePicker());

        btnEdit.setOnClickListener(v -> {
            Order selectedOrder = adapter.getSelectedOrder();
            if (selectedOrder == null) {
                Toast.makeText(this, "Select an order", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("order", selectedOrder);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        btnAddOrder.setOnClickListener(v -> {
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Select a date first", Toast.LENGTH_SHORT).show();
                return;
            }
            // Just for demonstration, let's add a quick order for the selected date
            dbHelper.insertOrderForDate(selectedDate);
            fetchOrders();
            Toast.makeText(this, "Sample Order Added for " + selectedDate, Toast.LENGTH_SHORT).show();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate = String.format(Locale.US, "%d-%02d-%02d", year1, (month1 + 1), dayOfMonth);
            tvSelectedDate.setText("Date: " + selectedDate);
            fetchOrders();
        }, year, month, day);
        datePickerDialog.show();
    }

    private void fetchOrders() {
        if (!selectedDate.isEmpty()) {
            List<Order> orders = dbHelper.getOrdersByDate(selectedDate);
            adapter.updateData(orders);
        } else {
            loadAllData();
        }
    }

    private void loadAllData() {
        List<Order> orders = dbHelper.getAllOrders();
        adapter.updateData(orders);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_populate) {
            fetchOrders();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            fetchOrders();
        }
    }
}
