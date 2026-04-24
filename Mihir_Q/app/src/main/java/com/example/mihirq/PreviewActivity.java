package com.example.mihirq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PreviewActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnBack;
    private DBHelper dbHelper;
    private StudentAdapter adapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);

        loadData();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadData() {
        studentList = dbHelper.getAllStudents();
        adapter = new StudentAdapter(this, studentList, dbHelper);
        listView.setAdapter(adapter);
    }
}
