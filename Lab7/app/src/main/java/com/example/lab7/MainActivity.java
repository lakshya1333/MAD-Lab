package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnQ1 = findViewById(R.id.btnQ1);
        Button btnQ2 = findViewById(R.id.btnQ2);

        btnQ1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppListActivity.class);
            startActivity(intent);
        });

        btnQ2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyMenuActivity.class);
            startActivity(intent);
        });
    }
}
