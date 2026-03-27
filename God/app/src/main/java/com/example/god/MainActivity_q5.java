package com.example.god;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity_q5 extends AppCompatActivity {

    private Spinner spinnerStay;
    private CheckBox cbCRA, cbOE, cbLab, cbMess;
    private RadioGroup rgGender;
    private Button btnOk, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_q5);

        Toolbar toolbar = findViewById(R.id.toolbar_q5);
        setSupportActionBar(toolbar);

        spinnerStay = findViewById(R.id.spinnerStay_q5);
        cbCRA = findViewById(R.id.cbCRA_q5);
        cbOE = findViewById(R.id.cbOE_q5);
        cbLab = findViewById(R.id.cbLab_q5);
        cbMess = findViewById(R.id.cbMess_q5);
        rgGender = findViewById(R.id.rgGender_q5);
        btnOk = findViewById(R.id.btnOk_q5);
        btnClear = findViewById(R.id.btnClear_q5);

        String[] stayOptions = {"ON CAMPUS", "OFF CAMPUS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stayOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStay.setAdapter(adapter);

        spinnerStay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stayOptions[position].equals("ON CAMPUS")) {
                    cbMess.setChecked(true);
                } else {
                    cbMess.setChecked(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cbCRA.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) cbOE.setChecked(false);
        });

        cbOE.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) cbCRA.setChecked(false);
        });

        btnOk.setOnClickListener(v -> {
            ArrayList<String> selectedItems = new ArrayList<>();
            if (cbCRA.isChecked()) selectedItems.add("CRA");
            if (cbOE.isChecked()) selectedItems.add("OE");
            if (cbLab.isChecked()) selectedItems.add("LAB");
            if (cbMess.isChecked()) selectedItems.add("MESS SUBSCRIPTION");

            int genderId = rgGender.getCheckedRadioButtonId();
            String gender = "";
            if (genderId != -1) {
                gender = ((RadioButton) findViewById(genderId)).getText().toString();
            }

            Intent intent = new Intent(MainActivity_q5.this, SecondActivity_q5.class);
            intent.putStringArrayListExtra("items", selectedItems);
            intent.putExtra("stay", spinnerStay.getSelectedItem().toString());
            intent.putExtra("gender", gender);
            startActivity(intent);
        });

        btnClear.setOnClickListener(v -> {
            cbCRA.setChecked(false);
            cbOE.setChecked(false);
            cbLab.setChecked(false);
            cbMess.setChecked(false);
            rgGender.clearCheck();
            spinnerStay.setSelection(0);
        });
    }
}