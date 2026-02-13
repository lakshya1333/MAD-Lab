package com.example.lab4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModeSwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_switch);

        ToggleButton toggle = findViewById(R.id.toggleMode);
        Button btn = findViewById(R.id.btnChange);

        btn.setOnClickListener(v ->
                Toast.makeText(this,
                        toggle.isChecked() ? "Wi-Fi Mode" : "Mobile Data Mode",
                        Toast.LENGTH_SHORT).show());
    }
}

