package com.example.lab2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    EditText urlInput;
    Button visitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        urlInput = findViewById(R.id.urlInput);
        visitBtn = findViewById(R.id.visitBtn);

        visitBtn.setOnClickListener(v -> {

            String url = urlInput.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(MainActivity4.this,
                        "Please enter a URL!", Toast.LENGTH_SHORT).show();
                return;
            }

            // If user types without https:// add it
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            // Open URL in Browser
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            startActivity(intent);
        });
    }
}

