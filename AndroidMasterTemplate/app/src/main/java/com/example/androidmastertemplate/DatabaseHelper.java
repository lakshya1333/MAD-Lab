package com.example.androidmastertemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper.java — Master Template
 * Supports: Users table + Events/Data table + JOIN queries
 * Pattern: Singleton — call DatabaseHelper.getInstance(context)
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    // ── Database Meta ─────────────────────────────────────
    private static final String DATABASE_NAME = "exam_db";   // TODO: EXAM_MODIFY
    private static final int    DATABASE_VERSION = 1;

    // ── Singleton ─────────────────────────────────────────
    private static DatabaseHelper instance;
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    // =====================================================================
    //  TABLE 1: USERS
    //  TODO: EXAM_MODIFY — Rename table/columns to match your domain
    // =====================================================================
    public static final String TABLE_USERS         = "users";
    public static final String COL_USER_ID         = "user_id";
    public static final String COL_USER_NAME       = "username";
    public static final String COL_USER_EMAIL      = "email";
    public static final String COL_USER_PASSWORD   = "password";
    public static final String COL_USER_CREATED_AT = "created_at";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " ("
                    + COL_USER_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_USER_NAME       + " TEXT NOT NULL UNIQUE, "
                    + COL_USER_EMAIL      + " TEXT NOT NULL, "
                    + COL_USER_PASSWORD   + " TEXT NOT NULL, "
                    + COL_USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";

    // =====================================================================
    //  TABLE 2: EVENTS (rename this to your data entity)
    //  TODO: EXAM_MODIFY — e.g., TABLE_SCORES, TABLE_BOOKINGS, TABLE_PRODUCTS
    // =====================================================================
    public static final String TABLE_EVENTS      = "events";       // TODO: EXAM_MODIFY
    public static final String COL_EVENT_ID      = "event_id";
    public static final String COL_EVENT_TITLE   = "title";        // TODO: EXAM_MODIFY
    public static final String COL_EVENT_DESC    = "description";  // TODO: EXAM_MODIFY
    public static final String COL_EVENT_SCORE   = "score";        // TODO: EXAM_MODIFY — numeric field
    public static final String COL_EVENT_DATE    = "event_date";   // TODO: EXAM_MODIFY
    public static final String COL_EVENT_USER_FK = "user_id";      // Foreign key → TABLE_USERS

    private static final String CREATE_TABLE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + " ("
                    + COL_EVENT_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_EVENT_TITLE   + " TEXT NOT NULL, "
                    + COL_EVENT_DESC    + " TEXT, "
                    + COL_EVENT_SCORE   + " INTEGER DEFAULT 0, "
                    + COL_EVENT_DATE    + " TEXT, "
                    + COL_EVENT_USER_FK + " INTEGER, "
                    + "FOREIGN KEY (" + COL_EVENT_USER_FK + ") REFERENCES "
                    + TABLE_USERS + "(" + COL_USER_ID + ") ON DELETE CASCADE"
                    + ");";

    // =====================================================================
    //  Constructor & Lifecycle
    // =====================================================================
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EVENTS);
        Log.d(TAG, "Tables created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: EXAM_MODIFY — For exams, drop-and-recreate is usually fine
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); // Enable FK enforcement
    }


    // =====================================================================
    //  CRUD — TABLE: USERS
    // =====================================================================

    /** Insert a new user. Returns row ID or -1 on failure. */
    public long insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_NAME,     username);
        cv.put(COL_USER_EMAIL,    email);
        cv.put(COL_USER_PASSWORD, password); // TODO: EXAM_MODIFY — hash password in real apps
        long id = db.insert(TABLE_USERS, null, cv);
        db.close();
        return id;
    }

    /** Check login credentials. Returns user_id if valid, -1 otherwise. */
    public long validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COL_USER_ID},
                COL_USER_NAME + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null
        );
        long userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COL_USER_ID));
            cursor.close();
        }
        db.close();
        return userId;
    }

    /** Check if username already exists. */
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID},
                COL_USER_NAME + "=?", new String[]{username},
                null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    /** Get all users as a Cursor (for debugging / admin views). */
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    /** Update a user's email. Returns rows affected. */
    public int updateUserEmail(long userId, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, newEmail);
        int rows = db.update(TABLE_USERS, cv, COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)});
        db.close();
        return rows;
    }

    /** Delete a user by ID. Returns rows affected. */
    public int deleteUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_USERS, COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)});
        db.close();
        return rows;
    }


    // =====================================================================
    //  CRUD — TABLE: EVENTS
    //  TODO: EXAM_MODIFY — Rename methods/params to your domain entity
    // =====================================================================

    /** Insert a new event linked to a user. Returns row ID or -1. */
    public long insertEvent(String title, String description, int score,
                            String date, long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EVENT_TITLE,   title);
        cv.put(COL_EVENT_DESC,    description);
        cv.put(COL_EVENT_SCORE,   score);       // TODO: EXAM_MODIFY — rename field
        cv.put(COL_EVENT_DATE,    date);
        cv.put(COL_EVENT_USER_FK, userId);
        long id = db.insert(TABLE_EVENTS, null, cv);
        db.close();
        return id;
    }

    /** Get all events for a specific user. */
    public Cursor getEventsByUser(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_EVENTS, null,
                COL_EVENT_USER_FK + "=?",
                new String[]{String.valueOf(userId)},
                null, null, COL_EVENT_DATE + " DESC");
    }

    /** Update an event's score by event ID. */
    public int updateEventScore(long eventId, int newScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EVENT_SCORE, newScore); // TODO: EXAM_MODIFY
        int rows = db.update(TABLE_EVENTS, cv, COL_EVENT_ID + "=?",
                new String[]{String.valueOf(eventId)});
        db.close();
        return rows;
    }

    /** Delete all events for a user. */
    public int deleteEventsByUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_EVENTS, COL_EVENT_USER_FK + "=?",
                new String[]{String.valueOf(userId)});
        db.close();
        return rows;
    }


    // =====================================================================
    //  JOIN QUERY — Users + Events
    //  Returns: username, email, title, score, event_date
    //  TODO: EXAM_MODIFY — Select columns you actually need
    // =====================================================================

    /**
     * Returns a Cursor with joined data: user info + their events.
     * Use this on Page4_Summary to display rich records.
     */
    public Cursor getJoinedUserEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        // TODO: EXAM_MODIFY — adjust selected columns and WHERE/ORDER clauses
        String query =
                "SELECT "
                        + "u." + COL_USER_ID    + " AS user_id, "
                        + "u." + COL_USER_NAME  + " AS username, "
                        + "u." + COL_USER_EMAIL + " AS email, "
                        + "e." + COL_EVENT_ID   + " AS event_id, "
                        + "e." + COL_EVENT_TITLE + " AS title, "
                        + "e." + COL_EVENT_SCORE + " AS score, "  // TODO: EXAM_MODIFY
                        + "e." + COL_EVENT_DATE  + " AS event_date "
                        + "FROM " + TABLE_USERS + " u "
                        + "INNER JOIN " + TABLE_EVENTS + " e "
                        + "ON u." + COL_USER_ID + " = e." + COL_EVENT_USER_FK + " "
                        + "ORDER BY e." + COL_EVENT_DATE + " DESC"; // TODO: EXAM_MODIFY — change sort
        return db.rawQuery(query, null);
    }

    /**
     * Returns joined data filtered by a specific user ID.
     */
    public Cursor getJoinedEventsByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT "
                        + "u." + COL_USER_NAME  + " AS username, "
                        + "e." + COL_EVENT_TITLE + " AS title, "
                        + "e." + COL_EVENT_SCORE + " AS score, "
                        + "e." + COL_EVENT_DATE  + " AS event_date "
                        + "FROM " + TABLE_USERS + " u "
                        + "INNER JOIN " + TABLE_EVENTS + " e "
                        + "ON u." + COL_USER_ID + " = e." + COL_EVENT_USER_FK + " "
                        + "WHERE u." + COL_USER_ID + " = ? "
                        + "ORDER BY e." + COL_EVENT_SCORE + " DESC"; // TODO: EXAM_MODIFY
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    /**
     * Aggregate: Get average score per user (GROUP BY example).
     * TODO: EXAM_MODIFY — change aggregation function (SUM, MAX, COUNT, etc.)
     */
    public Cursor getAverageScorePerUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT u." + COL_USER_NAME + " AS username, "
                        + "AVG(e." + COL_EVENT_SCORE + ") AS avg_score, "  // TODO: EXAM_MODIFY
                        + "COUNT(e." + COL_EVENT_ID + ") AS total_events "
                        + "FROM " + TABLE_USERS + " u "
                        + "LEFT JOIN " + TABLE_EVENTS + " e "
                        + "ON u." + COL_USER_ID + " = e." + COL_EVENT_USER_FK + " "
                        + "GROUP BY u." + COL_USER_ID
                        + " ORDER BY avg_score DESC";
        return db.rawQuery(query, null);
    }

    /**
     * Helper: Convert cursor to ArrayList of String arrays.
     * Useful for populating a ListView adapter directly.
     */
    public List<String[]> cursorToList(Cursor cursor) {
        List<String[]> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int colCount = cursor.getColumnCount();
            do {
                String[] row = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = cursor.getString(i) != null ? cursor.getString(i) : "";
                }
                list.add(row);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}