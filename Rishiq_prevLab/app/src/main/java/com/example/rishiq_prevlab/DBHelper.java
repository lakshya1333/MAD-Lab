package com.example.rishiq_prevlab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OrdersDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_ITEM1 = "item1";
    private static final String COLUMN_ITEM2 = "item2";
    private static final String COLUMN_COST1 = "cost1";
    private static final String COLUMN_COST2 = "cost2";
    private static final String COLUMN_QTY1 = "qty1";
    private static final String COLUMN_QTY2 = "qty2";
    private static final String COLUMN_TOTAL = "total";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_ITEM1 + " TEXT,"
                + COLUMN_ITEM2 + " TEXT,"
                + COLUMN_COST1 + " INTEGER,"
                + COLUMN_COST2 + " INTEGER,"
                + COLUMN_QTY1 + " INTEGER,"
                + COLUMN_QTY2 + " INTEGER,"
                + COLUMN_TOTAL + " INTEGER" + ")";
        db.execSQL(createTable);
        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        insertOrder(db, "2023-11-01", "Burger", "Fries", 50, 30, 1, 2, 110);
        insertOrder(db, "2023-11-01", "Pizza", "Coke", 200, 40, 1, 1, 240);
        insertOrder(db, "2023-11-02", "Pasta", "Garlic Bread", 150, 60, 2, 1, 360);
    }

    public void insertOrderForDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertOrder(db, date, "New Item 1", "New Item 2", 100, 50, 1, 1, 150);
    }

    private void insertOrder(SQLiteDatabase db, String date, String item1, String item2, int c1, int c2, int q1, int q2, int total) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_ITEM1, item1);
        values.put(COLUMN_ITEM2, item2);
        values.put(COLUMN_COST1, c1);
        values.put(COLUMN_COST2, c2);
        values.put(COLUMN_QTY1, q1);
        values.put(COLUMN_QTY2, q2);
        values.put(COLUMN_TOTAL, total);
        db.insert(TABLE_ORDERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public List<Order> getOrdersByDate(String date) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, COLUMN_DATE + "=?", new String[]{date}, null, null, null);
        fillListFromCursor(cursor, orders);
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, null);
        fillListFromCursor(cursor, orders);
        return orders;
    }

    private void fillListFromCursor(Cursor cursor, List<Order> orders) {
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM2)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COST1)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COST2)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QTY1)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QTY2)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void updateOrder(int id, int qty1, int qty2, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QTY1, qty1);
        values.put(COLUMN_QTY2, qty2);
        values.put(COLUMN_TOTAL, total);
        db.update(TABLE_ORDERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
