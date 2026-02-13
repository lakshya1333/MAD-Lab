package com.example.lab4;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AndroidVersionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_versions);

        findViewById(R.id.btnOreo)
                .setOnClickListener(v ->
                        Toast.makeText(this,"Android Oreo",Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnPie)
                .setOnClickListener(v ->
                        Toast.makeText(this,"Android Pie",Toast.LENGTH_SHORT).show());
    }
}
