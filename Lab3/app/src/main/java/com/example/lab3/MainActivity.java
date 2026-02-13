package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnListView = findViewById(R.id.btnListView);
        Button btnGridView = findViewById(R.id.btnGridView);
        Button btnTabLayout = findViewById(R.id.btnTabLayout);
        Button btnTableLayout = findViewById(R.id.btnTableLayout);

        btnListView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SportsActivity.class)));
        btnGridView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GalleryActivity.class)));
        btnTabLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NewsActivity.class)));
        btnTableLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MenuActivity.class)));
    }
}
