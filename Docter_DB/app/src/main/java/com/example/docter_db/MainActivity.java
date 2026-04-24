package com.example.docter_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editType, editDeleteId;
    Spinner spinnerDoctor;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btnConfirm, btnViewAll, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editPatientName);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        editType = findViewById(R.id.editConsultationType);
        editDeleteId = findViewById(R.id.editDeleteId);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnDelete = findViewById(R.id.btnDelete);

        // Populate Spinner
        String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Brown"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);

        confirmAppointment();
        viewAll();
        deleteAppointment();
    }

    public void confirmAppointment() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String doctor = spinnerDoctor.getSelectedItem().toString();
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                String time = timePicker.getHour() + ":" + timePicker.getMinute();
                String type = editType.getText().toString();

                if (name.isEmpty() || type.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = myDb.insertData(name, doctor, date, time, type);

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Appointment confirmed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void deleteAppointment() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editDeleteId.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer deletedRows = myDb.deleteData(id);
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
