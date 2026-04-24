package com.example.batch1_c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {
    EditText etEventName;
    TextView tvPoints;
    SeekBar sbPoints;
    Button btnRegisterEvent, btnGoToJoin;
    DatabaseHelper db;
    int maxPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        db = new DatabaseHelper(this);
        etEventName = findViewById(R.id.etEventName);
        tvPoints = findViewById(R.id.tvPointsLabel);
        sbPoints = findViewById(R.id.sbPoints);
        btnRegisterEvent = findViewById(R.id.btnRegisterEvent);
        btnGoToJoin = findViewById(R.id.btnGoToJoin);

        sbPoints.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxPoints = progress;
                tvPoints.setText("Max Points: " + maxPoints);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnRegisterEvent.setOnClickListener(v -> {
            String name = etEventName.getText().toString();
            if (!name.isEmpty()) {
                long id = db.insertEvent(name, maxPoints);
                if (id != -1) {
                    Toast.makeText(this, "Event Registered Successfully", Toast.LENGTH_SHORT).show();
                    etEventName.setText("");
                    sbPoints.setProgress(0);
                } else {
                    Toast.makeText(this, "Error registering event", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter event name", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToJoin.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, JoinActivity.class);
            startActivity(intent);
        });
    }
}
