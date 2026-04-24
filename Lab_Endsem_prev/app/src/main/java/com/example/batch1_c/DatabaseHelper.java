package com.example.batch1_c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EventManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Student table
    public static final String TABLE_STUDENTS = "students";
    public static final String COL_S_ID = "id";
    public static final String COL_S_NAME = "name";
    public static final String COL_S_ROLL = "roll_no";

    // Event table
    public static final String TABLE_EVENTS = "events";
    public static final String COL_E_ID = "id";
    public static final String COL_E_NAME = "event_name";
    public static final String COL_E_POINTS = "max_points";

    // Registration table
    public static final String TABLE_REGISTRATIONS = "registrations";
    public static final String COL_R_ID = "id";
    public static final String COL_R_S_ID = "student_id";
    public static final String COL_R_E_ID = "event_id";
    public static final String COL_R_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_S_NAME + " TEXT, " +
                COL_S_ROLL + " TEXT UNIQUE)");

        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " (" +
                COL_E_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_E_NAME + " TEXT, " +
                COL_E_POINTS + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_REGISTRATIONS + " (" +
                COL_R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_R_S_ID + " INTEGER, " +
                COL_R_E_ID + " INTEGER, " +
                COL_R_SCORE + " INTEGER, " +
                "FOREIGN KEY(" + COL_R_S_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COL_S_ID + "), " +
                "FOREIGN KEY(" + COL_R_E_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COL_E_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATIONS);
        onCreate(db);
    }

    public long insertStudent(String name, String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_S_NAME, name);
        values.put(COL_S_ROLL, roll);
        return db.insert(TABLE_STUDENTS, null, values);
    }

    public long insertEvent(String name, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_E_NAME, name);
        values.put(COL_E_POINTS, points);
        return db.insert(TABLE_EVENTS, null, values);
    }

    public long registerStudentToEvent(int studentId, int eventId, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_R_S_ID, studentId);
        values.put(COL_R_E_ID, eventId);
        values.put(COL_R_SCORE, score);
        return db.insert(TABLE_REGISTRATIONS, null, values);
    }

    public Cursor searchStudent(String roll) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENTS, null, COL_S_ROLL + "=?", new String[]{roll}, null, null, null);
    }

    public Cursor getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENTS, null, COL_S_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_EVENTS, null, null, null, null, null, null);
    }

    public Cursor getStudentSummary(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT e." + COL_E_NAME + ", r." + COL_R_SCORE + 
                " FROM " + TABLE_EVENTS + " e " +
                " JOIN " + TABLE_REGISTRATIONS + " r ON e." + COL_E_ID + " = r." + COL_R_E_ID +
                " WHERE r." + COL_R_S_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(studentId)});
    }

    public int getTotalScore(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COL_R_SCORE + ") FROM " + TABLE_REGISTRATIONS + " WHERE " + COL_R_S_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(studentId)});
        int total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        return total;
    }
}
