package com.example.supremegodproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * ACTIVITY 4 - UNIVERSAL TEMPLATE
 */
public class Activity4 extends AppCompatActivity {

    EditText etID, etName, etValue1;
    CheckBox cbSample;
    Switch switchSample;
    RadioGroup rgSample;
    Spinner spinnerSample;
    Button btnDate, btnTime, btnInsert, btnUpdate, btnDelete, btnView, btnReset, btnPopup, btnNext, btnBack;
    TextView tvDisplay, tvHeader;
    ListView listView;

    DBHelper dbHelper;
    ArrayList<String> dataList;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);

        dbHelper = new DBHelper(this);

        tvHeader = findViewById(R.id.tvHeader);
        etID = findViewById(R.id.etID);
        etName = findViewById(R.id.etName);
        etValue1 = findViewById(R.id.etValue1);
        cbSample = findViewById(R.id.cbSample);
        switchSample = findViewById(R.id.switchSample);
        rgSample = findViewById(R.id.rgSample);
        spinnerSample = findViewById(R.id.spinnerSample);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        btnReset = findViewById(R.id.btnReset);
        btnPopup = findViewById(R.id.btnPopup);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        tvDisplay = findViewById(R.id.tvDisplay);
        listView = findViewById(R.id.listView);

        tvHeader.setText("ACTIVITY 4");

        // Spinner Setup
        String[] items = {"Item A", "Item B", "Item C"};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinnerSample.setAdapter(spinAdapter);

        // Date Picker
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> tvDisplay.setText("Date: " + day + "/" + (month + 1) + "/" + year), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Time Picker
        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hr, min) -> tvDisplay.setText("Time: " + hr + ":" + min), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
        });

        // Insert
        btnInsert.setOnClickListener(v -> {
            boolean isInserted = dbHelper.insertData(etName.getText().toString(), etValue1.getText().toString(), "Act4", "Act4");
            Toast.makeText(this, isInserted ? "Inserted" : "Failed", Toast.LENGTH_SHORT).show();
        });

        // Update
        btnUpdate.setOnClickListener(v -> {
            int rows = dbHelper.updateData(etID.getText().toString(), etName.getText().toString(), etValue1.getText().toString(), "U4", "U4");
            Toast.makeText(this, rows > 0 ? "Updated" : "Check ID", Toast.LENGTH_SHORT).show();
        });

        // Delete
        btnDelete.setOnClickListener(v -> {
            Integer rows = dbHelper.deleteData(etID.getText().toString());
            Toast.makeText(this, rows > 0 ? "Deleted" : "Check ID", Toast.LENGTH_SHORT).show();
        });

        // View
        btnView.setOnClickListener(v -> loadData());

        // Reset
        btnReset.setOnClickListener(v -> {
            etID.setText(""); etName.setText(""); etValue1.setText("");
            cbSample.setChecked(false); switchSample.setChecked(false);
            rgSample.clearCheck(); tvDisplay.setText("Reset");
        });

        // Popup
        btnPopup.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnPopup);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
            popup.show();
        });

        // Navigation
        btnNext.setOnClickListener(v -> startActivity(new Intent(this, Activity5.class)));
        btnBack.setOnClickListener(v -> finish());

        registerForContextMenu(listView);
    }

    private void loadData() {
        dataList = new ArrayList<>();
        Cursor res = dbHelper.getAllData();
        while (res.moveToNext()) dataList.add("ID: " + res.getString(0) + " | " + res.getString(1));
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.main_menu, menu); return true; }
    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) { super.onCreateContextMenu(menu, v, info); getMenuInflater().inflate(R.menu.context_menu, menu); }
}
