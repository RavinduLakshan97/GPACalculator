package com.example.gpacalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.gpacalculator.DatabaseHelper;

public class GPACalculator {

    private DatabaseHelper dbHelper;

    public GPACalculator(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertGrade(String subject, double grade) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SUBJECT, subject);
        values.put(DatabaseHelper.COLUMN_GRADE, grade);

        long id = db.insert(DatabaseHelper.TABLE_GRADES, null, values);
        db.close();

        return id;
    }

    public double calculateGPA() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT AVG(" + DatabaseHelper.COLUMN_GRADE + ") FROM " + DatabaseHelper.TABLE_GRADES;

        Cursor c = db.rawQuery(selectQuery, null);

        double gpa = 0;
        if (c.moveToFirst()) {
            gpa = c.getDouble(0);
        }

        c.close();
        db.close();

        return gpa;
    }
}