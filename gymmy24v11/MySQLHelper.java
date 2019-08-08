package com.example.vaibhavchopda.gymmy24v11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper {
    public MySQLHelper(Context context) {
        super(context, "GraphDatabase", null, 1);
    }


    // Creates table for graph values
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table GraphTable (xValues INTEGER, yValues INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // method for inserting data into the database
    public void insertData(int x, int y) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("xValues", x);
        contentValues.put("yValues", y);

        db.insert("GraphTable", null, contentValues);
    }
}
