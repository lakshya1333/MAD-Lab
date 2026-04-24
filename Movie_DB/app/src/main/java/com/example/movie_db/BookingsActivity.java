package com.example.movie_db;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity {

    ListView listViewBookings;
    DatabaseHelper db;
    ArrayList<String> bookingList;
    ArrayList<String> bookingIds;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        listViewBookings = findViewById(R.id.listViewBookings);
        db = new DatabaseHelper(this);
        bookingList = new ArrayList<>();
        bookingIds = new ArrayList<>();

        loadData();

        registerForContextMenu(listViewBookings);
    }

    private void loadData() {
        bookingList.clear();
        bookingIds.clear();
        Cursor cursor = db.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                bookingIds.add(cursor.getString(0));
                String details = "Movie: " + cursor.getString(1) + 
                                 "\nAt: " + cursor.getString(2) + 
                                 "\nDate: " + cursor.getString(3) + 
                                 "\nTime: " + cursor.getString(4) + 
                                 "\nType: " + cursor.getString(5);
                bookingList.add(details);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingList);
        listViewBookings.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Delete Booking");
        menu.add(0, v.getId(), 0, "Cancel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle().equals("Delete Booking")) {
            String id = bookingIds.get(info.position);
            Integer deletedRows = db.deleteData(id);
            if (deletedRows > 0) {
                Toast.makeText(this, "Booking Deleted", Toast.LENGTH_SHORT).show();
                loadData();
            } else {
                Toast.makeText(this, "Error Deleting", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
