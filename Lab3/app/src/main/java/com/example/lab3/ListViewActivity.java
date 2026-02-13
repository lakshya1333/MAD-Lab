package com.example.lab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {
    String[] countries = {"American Samoa", "El Salvador", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = findViewById(R.id.simpleListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, countries);
        listView.setAdapter(adapter);
    }
}
