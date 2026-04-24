package com.example.lab8_q1;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "TaskDB";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, priority TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public void insertTask(Task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", t.name);
        cv.put("date", t.date);
        cv.put("priority", t.priority);
        db.insert("tasks", null, cv);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tasks", null);

        while (c.moveToNext()) {
            list.add(new Task(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            ));
        }
        return list;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id=?", new String[]{String.valueOf(id)});
    }

    public void updateTask(Task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", t.name);
        cv.put("date", t.date);
        cv.put("priority", t.priority);

        db.update("tasks", cv, "id=?", new String[]{String.valueOf(t.id)});
    }
}
