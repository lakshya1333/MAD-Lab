package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnTestApp)
                .setOnClickListener(v -> startActivity(
                        new Intent(this, TestAppActivity.class)));

        findViewById(R.id.btnAndroidVersions)
                .setOnClickListener(v -> startActivity(
                        new Intent(this, AndroidVersionActivity.class)));

        findViewById(R.id.btnModeSwitch)
                .setOnClickListener(v -> startActivity(
                        new Intent(this, ModeSwitchActivity.class)));

        findViewById(R.id.btnFoodOrder)
                .setOnClickListener(v -> startActivity(
                        new Intent(this, FoodOrderActivity.class)));
    }
}
