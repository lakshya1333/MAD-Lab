package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SportsActivity extends AppCompatActivity {
    String[] sports = {"Football", "Cricket", "Basketball", "Tennis",
            "Hockey", "Badminton", "Swimming", "Athletics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        ListView listView = findViewById(R.id.sportsListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sports);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSport = sports[position];

                Toast.makeText(SportsActivity.this,
                        "You selected: " + selectedSport, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
