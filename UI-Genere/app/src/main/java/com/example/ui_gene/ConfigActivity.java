package com.example.ui_gene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {

    TextView tvConfigTitle, tvAddedList, tvAppTitleHeader;
    EditText etScreenPurpose, etQuantity, etComponentLabel, etComponentOptions;
    Spinner spLayoutType, spComponentType;
    Button btnAddToList, btnConfigNext, btnConfigBack;

    int currentScreenIndex, totalScreens;
    String appPurpose;
    ArrayList<ScreenConfig> screenConfigs;
    ArrayList<ComponentInfo> currentComponents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        tvAppTitleHeader = findViewById(R.id.tvAppTitleHeader);
        tvConfigTitle = findViewById(R.id.tvConfigTitle);
        tvAddedList = findViewById(R.id.tvAddedList);
        etScreenPurpose = findViewById(R.id.etScreenPurpose);
        etQuantity = findViewById(R.id.etQuantity);
        etComponentLabel = findViewById(R.id.etComponentLabel);
        etComponentOptions = findViewById(R.id.etComponentOptions);
        spLayoutType = findViewById(R.id.spLayoutType);
        spComponentType = findViewById(R.id.spComponentType);
        btnAddToList = findViewById(R.id.btnAddToList);
        btnConfigNext = findViewById(R.id.btnConfigNext);
        btnConfigBack = findViewById(R.id.btnConfigBack);

        totalScreens = getIntent().getIntExtra("TOTAL_SCREENS", 1);
        currentScreenIndex = getIntent().getIntExtra("CURRENT_SCREEN_INDEX", 1);
        appPurpose = getIntent().getStringExtra("APP_PURPOSE");
        screenConfigs = (ArrayList<ScreenConfig>) getIntent().getSerializableExtra("CONFIG_LIST");
        
        if (screenConfigs == null) screenConfigs = new ArrayList<>();

        tvAppTitleHeader.setText("Project: " + appPurpose);
        tvConfigTitle.setText("Configure Screen " + currentScreenIndex);

        // Layout Spinner
        String[] layouts = {"LinearLayout", "RelativeLayout", "ConstraintLayout", "TableLayout"};
        spLayoutType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, layouts));

        // Component Spinner
        String[] components = {"TextView", "EditText", "Button", "Spinner", "RadioGroup", "CheckBox", "Switch", "ListView", "SeekBar", "DatePicker", "TimePicker", "ToggleButton"};
        spComponentType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, components));

        spComponentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = components[position];
                if (selected.equals("Spinner") || selected.equals("RadioGroup") || selected.equals("ListView")) {
                    etComponentOptions.setVisibility(View.VISIBLE);
                } else {
                    etComponentOptions.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnConfigBack.setOnClickListener(v -> finish());

        btnAddToList.setOnClickListener(v -> {
            String type = spComponentType.getSelectedItem().toString();
            String label = etComponentLabel.getText().toString();
            String qtyStr = etQuantity.getText().toString();
            String options = etComponentOptions.getText().toString();

            if (label.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(this, "Label/Qty required", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty = Integer.parseInt(qtyStr);
            currentComponents.add(new ComponentInfo(type, label, qty, options));
            updateList();
            etComponentLabel.setText("");
            etComponentOptions.setText("");
        });

        btnConfigNext.setOnClickListener(v -> {
            if (currentComponents.isEmpty()) {
                Toast.makeText(this, "Add components first", Toast.LENGTH_SHORT).show();
                return;
            }
            String screenPurpose = etScreenPurpose.getText().toString();
            String layout = spLayoutType.getSelectedItem().toString();
            
            screenConfigs.add(new ScreenConfig("Screen " + currentScreenIndex, screenPurpose, layout, new ArrayList<>(currentComponents)));

            if (currentScreenIndex < totalScreens) {
                Intent i = new Intent(this, ConfigActivity.class);
                i.putExtra("TOTAL_SCREENS", totalScreens);
                i.putExtra("CURRENT_SCREEN_INDEX", currentScreenIndex + 1);
                i.putExtra("APP_PURPOSE", appPurpose);
                i.putExtra("CONFIG_LIST", screenConfigs);
                startActivity(i);
            } else {
                Intent i = new Intent(this, ReviewActivity.class);
                i.putExtra("CONFIG_LIST", screenConfigs);
                startActivity(i);
            }
        });
    }

    private void updateList() {
        StringBuilder sb = new StringBuilder();
        for (ComponentInfo c : currentComponents) {
            sb.append("• ").append(c.getType()).append(": ").append(c.getLabel()).append("\n");
        }
        tvAddedList.setText(sb.toString());
    }
}
