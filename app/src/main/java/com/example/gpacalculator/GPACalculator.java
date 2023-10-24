package com.example.gpacalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GPACalculator {

    private DatabaseHelper dbHelper;

    public GPACalculator(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertGrade(String subject, String grade, int credits) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SUBJECT, subject);
        values.put(DatabaseHelper.COLUMN_GRADE, grade);
        db.insert(DatabaseHelper.TABLE_GRADES, null, values);
        db.close();
    }

    public double calculateGPA() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_GRADES, new String[]{DatabaseHelper.COLUMN_GRADE}, null, null, null, null, null);
        double totalGPA = 0.0;
        int count = 0;
        while (cursor.moveToNext()) {
            String grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE));
            totalGPA += dbHelper.convertGradeToGPA(grade);
            count++;
        }
        cursor.close();
        db.close();
        return count > 0 ? totalGPA / count : 0.0;
    }

    public String getGradesList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_GRADES, null);
        StringBuilder gradesList = new StringBuilder();
        while (cursor.moveToNext()) {
            String subject = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBJECT));
            String grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE));
            gradesList.append(subject).append(": ").append(grade).append("\n");
        }
        cursor.close();
        db.close();
        return gradesList.toString();
    }

    public void resetGrades() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_GRADES, null, null);
        db.close();
    }
}