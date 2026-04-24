package com.example.supremegodproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// ===== DATABASE HELPER CLASS =====
// This class manages SQLite database creation and CRUD operations.
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LabExam.db";
    public static final String TABLE_NAME = "data_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "VALUE1";
    public static final String COL_4 = "VALUE2";
    public static final String COL_5 = "VALUE3";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, VALUE1 TEXT, VALUE2 TEXT, VALUE3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERT DATA: Returns true if successful
    public boolean insertData(String name, String v1, String v2, String v3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, v1);
        contentValues.put(COL_4, v2);
        contentValues.put(COL_5, v3);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // UPDATE DATA: Returns number of rows affected
    public int updateData(String id, String name, String v1, String v2, String v3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, v1);
        contentValues.put(COL_4, v2);
        contentValues.put(COL_5, v3);
        // Updates where ID matches
        return db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
    }

    // DELETE DATA: Returns number of rows deleted
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    // READ ALL DATA: Returns Cursor
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }
}
