package com.example.ui_gene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FlowActivity extends AppCompatActivity {

    TextView tvFlow;
    Button btnRunSimulation;
    ArrayList<ScreenConfig> screenConfigs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);

        tvFlow = findViewById(R.id.tvFlow);
        btnRunSimulation = findViewById(R.id.btnRunSimulation);

        screenConfigs = (ArrayList<ScreenConfig>) getIntent().getSerializableExtra("CONFIG_LIST");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < screenConfigs.size(); i++) {
            sb.append(screenConfigs.get(i).getScreenName());
            if (i < screenConfigs.size() - 1) {
                sb.append(" ➔ ");
            }
        }
        tvFlow.setText(sb.toString());

        btnRunSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear old data for new simulation
                DatabaseHelper db = new DatabaseHelper(FlowActivity.this);
                db.clearDatabase();

                Intent intent = new Intent(FlowActivity.this, SimulationActivity.class);
                intent.putExtra("CONFIG_LIST", screenConfigs);
                intent.putExtra("SIM_INDEX", 0);
                startActivity(intent);
            }
        });
    }
}
