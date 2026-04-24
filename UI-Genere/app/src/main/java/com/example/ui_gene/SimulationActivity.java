package com.example.ui_gene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SimulationActivity extends AppCompatActivity {

    LinearLayout dynamicContainer;
    TextView tvSimTitle;
    Button btnNext, btnBack;
    ArrayList<ScreenConfig> configs;
    int index;
    DatabaseHelper db;
    ArrayList<View> inputViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        dynamicContainer = findViewById(R.id.dynamicContainer);
        tvSimTitle = findViewById(R.id.tvSimTitle);
        btnNext = findViewById(R.id.btnSubmitSim);
        db = new DatabaseHelper(this);

        configs = (ArrayList<ScreenConfig>) getIntent().getSerializableExtra("CONFIG_LIST");
        index = getIntent().getIntExtra("SIM_INDEX", 0);
        ScreenConfig current = configs.get(index);

        tvSimTitle.setText(current.getScreenName() + ": " + current.getPurpose());

        for (ComponentInfo c : current.getComponents()) {
            for (int i = 0; i < c.getQuantity(); i++) {
                addWidget(c);
            }
        }

        btnNext.setOnClickListener(v -> {
            saveToDb(current);
            if (index < configs.size() - 1) {
                Intent i = new Intent(this, SimulationActivity.class);
                i.putExtra("CONFIG_LIST", configs);
                i.putExtra("SIM_INDEX", index + 1);
                startActivity(i);
            } else {
                startActivity(new Intent(this, SummaryActivity.class));
            }
        });
    }

    private void addWidget(ComponentInfo c) {
        TextView label = new TextView(this);
        label.setText(c.getLabel());
        label.setPadding(0, 10, 0, 5);
        dynamicContainer.addView(label);

        View view = null;
        String[] opts = c.getOptions().split(",");

        switch (c.getType()) {
            case "EditText":
                view = new EditText(this);
                ((EditText)view).setHint("Enter value...");
                break;
            case "Button":
                view = new Button(this);
                ((Button)view).setText(c.getLabel());
                break;
            case "CheckBox":
                view = new CheckBox(this);
                ((CheckBox)view).setText("Yes/No");
                break;
            case "Switch":
                view = new Switch(this);
                break;
            case "SeekBar":
                view = new SeekBar(this);
                break;
            case "DatePicker":
                view = new DatePicker(this);
                break;
            case "TimePicker":
                view = new TimePicker(this);
                break;
            case "ToggleButton":
                view = new ToggleButton(this);
                break;
            case "Spinner":
                Spinner s = new Spinner(this);
                s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opts));
                view = s;
                break;
            case "RadioGroup":
                RadioGroup rg = new RadioGroup(this);
                for (String o : opts) {
                    RadioButton rb = new RadioButton(this);
                    rb.setText(o.trim());
                    rg.addView(rb);
                }
                view = rg;
                break;
            case "ListView":
                ListView lv = new ListView(this);
                lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opts));
                lv.setLayoutParams(new LinearLayout.LayoutParams(-1, 300));
                view = lv;
                break;
        }

        if (view != null) {
            view.setTag(c.getType() + ":" + c.getLabel());
            dynamicContainer.addView(view);
            inputViews.add(view);
        }
    }

    private void saveToDb(ScreenConfig sc) {
        for (View v : inputViews) {
            String tag = (String) v.getTag();
            String val = "Interacted";
            if (v instanceof EditText) val = ((EditText)v).getText().toString();
            else if (v instanceof Spinner) val = ((Spinner)v).getSelectedItem().toString();
            else if (v instanceof CheckBox) val = ((CheckBox)v).isChecked() ? "Yes" : "No";
            else if (v instanceof RadioGroup) {
                int id = ((RadioGroup)v).getCheckedRadioButtonId();
                if (id != -1) val = ((RadioButton)v.findViewById(id)).getText().toString();
            }
            db.insertData(sc.getScreenName(), tag, val);
        }
    }
}
