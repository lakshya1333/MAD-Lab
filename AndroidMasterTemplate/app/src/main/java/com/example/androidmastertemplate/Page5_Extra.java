package com.example.androidmastertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Page5_Extra.java — Optional 5th Page (Stub)
 *
 * ACTIVATION CHECKLIST (see Quick Guide at end of README):
 *  1. Uncomment <activity> declaration in AndroidManifest.xml
 *  2. Change Page4_Summary's btnNext to navigate here instead of finishing
 *  3. Fill in the layout: activity_page5_extra.xml
 *  4. Add your logic below
 *
 * TODO: EXAM_MODIFY — Rename this file and class to match its purpose
 *   e.g., Page5_Report.java, Page5_Settings.java, Page5_Map.java
 */
public class Page5_Extra extends AppCompatActivity {

    private Button btnPrev, btnFinish;
    private long   userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5_extra);

        userId   = getIntent().getLongExtra("USER_ID",  -1);
        username = getIntent().getStringExtra("USERNAME");

        btnPrev   = findViewById(R.id.btnPrev);
        btnFinish = findViewById(R.id.btnFinish);

        btnPrev.setOnClickListener(v -> finish()); // Back to Page 4

        btnFinish.setOnClickListener(v -> {
            // TODO: EXAM_MODIFY — Navigate to another page OR just finish
            finish();
        });

        // TODO: EXAM_MODIFY — Add your page-specific logic here
    }
}
