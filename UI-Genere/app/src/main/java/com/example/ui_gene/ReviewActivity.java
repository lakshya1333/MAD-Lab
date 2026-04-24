package com.example.ui_gene;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    TextView tvReview;
    Button btnGenerateFlow;
    ArrayList<ScreenConfig> screenConfigs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        tvReview = findViewById(R.id.tvReview);
        btnGenerateFlow = findViewById(R.id.btnGenerateFlow);

        screenConfigs = (ArrayList<ScreenConfig>) getIntent().getSerializableExtra("CONFIG_LIST");

        StringBuilder sb = new StringBuilder();
        for (ScreenConfig config : screenConfigs) {
            sb.append("--- ").append(config.getScreenName()).append(" ---\n");
            sb.append("Purpose: ").append(config.getPurpose()).append("\n");
            sb.append("Layout: ").append(config.getLayoutType()).append("\n");
            sb.append("Components:\n");
            for (ComponentInfo info : config.getComponents()) {
                sb.append("  - ").append(info.getType())
                  .append(" (x").append(info.getQuantity()).append("): ")
                  .append(info.getLabel()).append("\n");
            }
            sb.append("\n");
        }
        tvReview.setText(sb.toString());

        btnGenerateFlow.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlowActivity.class);
            intent.putExtra("CONFIG_LIST", screenConfigs);
            startActivity(intent);
        });
    }
}
