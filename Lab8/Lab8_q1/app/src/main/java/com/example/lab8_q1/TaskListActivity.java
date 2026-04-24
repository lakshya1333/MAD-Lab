package com.example.lab8_q1;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    ListView listView;
    DBHelper db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_task_list);

        listView = findViewById(R.id.listView);
        db = new DBHelper(this);

        loadTasks();
    }

    private void loadTasks() {
        ArrayList<Task> list = db.getAllTasks();
        TaskAdapter adapter = new TaskAdapter(this, list, db);
        listView.setAdapter(adapter);
    }
}