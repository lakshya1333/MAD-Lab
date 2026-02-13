package com.example.lab2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView lifecycleText;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lifecycleText = findViewById(R.id.lifecycleText);

        message += "onCreate() Called\n";
        lifecycleText.setText(message);

        Log.d("LifecycleEvent", "onCreate() Triggered");
    }

    @Override
    protected void onStart() {
        super.onStart();

        message += "onStart() Called\n";
        lifecycleText.setText(message);

        Log.d("LifecycleEvent", "onStart() Triggered");
    }

    @Override
    protected void onResume() {
        super.onResume();

        message += "onResume() Called\n";
        lifecycleText.setText(message);

        Log.d("LifecycleEvent", "onResume() Triggered");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("LifecycleEvent", "onPause() Triggered");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("LifecycleEvent", "onStop() Triggered");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("LifecycleEvent", "onDestroy() Triggered");
    }
}