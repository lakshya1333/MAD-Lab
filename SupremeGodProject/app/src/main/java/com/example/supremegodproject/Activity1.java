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
 * ALL-IN-ONE TEMPLATE ACTIVITY
 * Copy this code to Activity2, Activity3, etc., and change class name.
 * To reduce activities, simply remove Intent navigation logic.
 */
public class Activity1 extends AppCompatActivity {

    // ===== 1. DECLARE ALL COMPONENTS =====
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
        setContentView(R.layout.activity1); // Using the universal layout

        // Initialize Database
        dbHelper = new DBHelper(this);

        // ===== 2. INITIALIZE ALL COMPONENTS =====
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

        tvHeader.setText("ACTIVITY 1"); // Change this for each activity

        // ===== 3. SPINNER SETUP =====
        String[] items = {"Option 1", "Option 2", "Option 3"};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinnerSample.setAdapter(spinAdapter);

        // ===== 4. DATE PICKER =====
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> 
                tvDisplay.setText("Date: " + day + "/" + (month + 1) + "/" + year), 
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // ===== 5. TIME PICKER =====
        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hr, min) -> 
                tvDisplay.setText("Time: " + hr + ":" + min), 
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
        });

        // ===== 6. DATABASE: INSERT =====
        btnInsert.setOnClickListener(v -> {
            boolean isInserted = dbHelper.insertData(etName.getText().toString(), etValue1.getText().toString(), "Extra1", "Extra2");
            if(isInserted) Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
        });

        // ===== 7. DATABASE: UPDATE =====
        btnUpdate.setOnClickListener(v -> {
            // Provide ID in etID field to update that specific record
            int affectedRows = dbHelper.updateData(etID.getText().toString(), etName.getText().toString(), etValue1.getText().toString(), "Updated", "Updated");
            if(affectedRows > 0) Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Update Failed - Check ID", Toast.LENGTH_SHORT).show();
        });

        // ===== 8. DATABASE: DELETE =====
        btnDelete.setOnClickListener(v -> {
            Integer deletedRows = dbHelper.deleteData(etID.getText().toString());
            if(deletedRows > 0) Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Deletion Failed - Check ID", Toast.LENGTH_SHORT).show();
        });

        // ===== 9. DATABASE: VIEW ALL =====
        btnView.setOnClickListener(v -> loadData());

        // ===== 10. RESET FIELDS =====
        btnReset.setOnClickListener(v -> {
            etID.setText(""); etName.setText(""); etValue1.setText("");
            cbSample.setChecked(false); switchSample.setChecked(false);
            rgSample.clearCheck(); tvDisplay.setText("Reset Successful");
        });

        // ===== 11. POPUP MENU =====
        btnPopup.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnPopup);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(this, "Popup Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });

        // ===== 12. NAVIGATION (INTENTS) =====
        btnNext.setOnClickListener(v -> {
            // Change Activity2.class to whatever the next activity is
            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra("key", etName.getText().toString()); // Passing data
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish()); // Go back

        // ===== 13. CONTEXT MENU REGISTRATION =====
        registerForContextMenu(listView);
    }

    // Load Data into ListView
    private void loadData() {
        dataList = new ArrayList<>();
        Cursor res = dbHelper.getAllData();
        if (res.getCount() == 0) {
            dataList.add("Database is Empty");
        } else {
            while (res.moveToNext()) {
                dataList.add("ID: " + res.getString(0) + " | Name: " + res.getString(1));
            }
        }
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(listAdapter);
    }

    // ===== 14. OPTIONS MENU =====
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    // ===== 15. CONTEXT MENU =====
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(this, item.getTitle() + " on item at " + info.position, Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
