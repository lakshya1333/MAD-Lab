package com.example.ticket_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class handles all SQLite database operations: Create, Insert, Update, Display
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database name and Table name constants
    public static final String DATABASE_NAME = "TicketBooking.db";
    public static final String TABLE_NAME = "tickets";
    
    // Column name constants for easy access
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TICKET_COUNT";
    public static final String COL_4 = "TYPE";
    public static final String COL_5 = "DATE";
    public static final String COL_6 = "PREFERENCE";
    public static final String COL_7 = "TOTAL_COST";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // This method runs when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the 'tickets' table with columns
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TICKET_COUNT INTEGER, TYPE TEXT, DATE TEXT, PREFERENCE TEXT, TOTAL_COST DOUBLE)");
    }

    // This method runs when the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing table if it exists and create it again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Function to INSERT a new ticket booking record into the database
    public long insertData(String name, int tickets, String type, String date, String pref, double cost) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get write access to database
        ContentValues contentValues = new ContentValues();
        // Putting data into ContentValues object
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, tickets);
        contentValues.put(COL_4, type);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, pref);
        contentValues.put(COL_7, cost);
        // Insert record and return the generated ID
        return db.insert(TABLE_NAME, null, contentValues);
    }

    // Function to DISPLAY/FETCH the latest data (can be modified to get specific record)
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Fetching the most recent entry
        return db.rawQuery("select * from " + TABLE_NAME + " ORDER BY ID DESC LIMIT 1", null);
    }

    // Function to UPDATE an existing ticket record by its ID
    public boolean updateData(String id, String name, int tickets, String type, String date, String pref, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Assigning new values to update
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, tickets);
        contentValues.put(COL_4, type);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, pref);
        contentValues.put(COL_7, cost);
        // Updating record where ID matches
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }
}