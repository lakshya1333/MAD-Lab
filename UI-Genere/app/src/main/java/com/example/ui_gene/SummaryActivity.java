package com.example.ui_gene;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    TextView tvSummary;
    Button btnRestart, btnEdit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSummary = findViewById(R.id.tvSummary);
        btnRestart = findViewById(R.id.btnRestart);
        btnEdit = findViewById(R.id.btnEdit);
        db = new DatabaseHelper(this);

        displayData();

        btnEdit.setOnClickListener(v -> {
            // Goes back to screen config
            finish();
        });

        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void displayData() {
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            tvSummary.setText("No data saved yet.");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        String currentScreen = "";
        
        while (res.moveToNext()) {
            String screen = res.getString(1); // screen_name
            if (!screen.equals(currentScreen)) {
                buffer.append("\n▶ SCREEN: ").append(screen.toUpperCase()).append("\n");
                currentScreen = screen;
            }
            buffer.append("   • ").append(res.getString(2)) // component_type
                  .append(" ➔ ").append(res.getString(3)) // value
                  .append("\n");
        }
        tvSummary.setText(buffer.toString());
    }
}
