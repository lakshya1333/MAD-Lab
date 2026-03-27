package com.example.god;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SecondActivity_q2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_q2);

        TextView tvOrderDetails = findViewById(R.id.tvOrderDetails_q2);
        TextView tvDeliveryDate = findViewById(R.id.tvDeliveryDate_q2);

        ArrayList<String> orders = getIntent().getStringArrayListExtra("orders");
        if (orders != null && !orders.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String order : orders) {
                sb.append(order).append("\n");
            }
            tvOrderDetails.setText(sb.toString());
        } else {
            tvOrderDetails.setText("No items selected");
        }

        // Calculate current date + 10 days
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String deliveryDate = sdf.format(calendar.getTime());

        tvDeliveryDate.setText("Estimated Delivery Date: " + deliveryDate);
    }
}