package com.example.hospital_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

// This class handles DATABASE (Create, Insert, Update, Delete)
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "HospitalDB", null, 1);
    }

    // Create table when DB is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE patients(" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "gender TEXT, " +
                "dept TEXT, " +
                "datetime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // INSERT DATA
    public void insertData(int id, String name, String gender, String dept, String dt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("gender", gender);
        cv.put("dept", dept);
        cv.put("datetime", dt);

        db.insert("patients", null, cv);
    }

    // UPDATE DATA
    public void updateData(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);

        db.update("patients", cv, "id=?", new String[]{String.valueOf(id)});
    }

    // DELETE DATA
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("patients", "id=?", new String[]{String.valueOf(id)});
    }

    // READ DATA (optional)
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM patients", null);
    }
}