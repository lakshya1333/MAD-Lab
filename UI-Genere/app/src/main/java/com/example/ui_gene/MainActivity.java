package com.example.ui_gene;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLifecycle";
    EditText etNumScreens, etAppPurpose;
    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called");

        etAppPurpose = findViewById(R.id.etAppPurpose);
        etNumScreens = findViewById(R.id.etNumScreens);
        btnProceed = findViewById(R.id.btnProceed);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purpose = etAppPurpose.getText().toString();
                String numStr = etNumScreens.getText().toString();

                if (purpose.isEmpty() || numStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numScreens = Integer.parseInt(numStr);
                if (numScreens <= 0) {
                    Toast.makeText(MainActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Explicit Intent: Moving within the app
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                intent.putExtra("TOTAL_SCREENS", numScreens);
                intent.putExtra("CURRENT_SCREEN_INDEX", 1);
                intent.putExtra("APP_PURPOSE", purpose);
                startActivity(intent);
            }
        });
    }

    // --- Lifecycle Methods Demo ---
    @Override
    protected void onStart() { super.onStart(); Log.d(TAG, "onStart called"); }
    @Override
    protected void onResume() { super.onResume(); Log.d(TAG, "onResume called"); }
    @Override
    protected void onPause() { super.onPause(); Log.d(TAG, "onPause called"); }
    @Override
    protected void onStop() { super.onStop(); Log.d(TAG, "onStop called"); }
    @Override
    protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called"); }

    // --- Options Menu Demo ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Syllabus Info");
        menu.add(0, 2, 0, "About App");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            Toast.makeText(this, "Syllabus: Intents, Layouts, Widgets, SQLite...", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == 2) {
            Toast.makeText(this, "MAD Exam Helper v2.0", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
