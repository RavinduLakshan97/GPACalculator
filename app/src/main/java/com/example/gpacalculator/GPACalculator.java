package com.example.gpacalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        values.put(DatabaseHelper.COLUMN_CREDITS, credits);
        db.insert(DatabaseHelper.TABLE_GRADES, null, values);
        db.close();
    }

    public double calculateGPA() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_GRADES, new String[]{DatabaseHelper.COLUMN_GRADE, DatabaseHelper.COLUMN_CREDITS}, null, null, null, null, null);
        double totalGPA = 0.0;
        int totalCredits = 0;
        while (cursor.moveToNext()) {
            String grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE));
            int credits = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREDITS));
            totalGPA += convertGradeToGPA(grade) * credits;
            totalCredits += credits;
        }
        cursor.close();
        db.close();
        return totalCredits > 0 ? totalGPA / totalCredits : 0.0;
    }

    public List<Grade> getAllGrades() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_GRADES, null);
        List<Grade> grades = new ArrayList<>();
        while (cursor.moveToNext()) {
            String subject = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBJECT));
            String grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE));
            int credits = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREDITS));
            double gpa = convertGradeToGPA(grade);
            grades.add(new Grade(subject, grade, credits, gpa));
        }
        cursor.close();
        db.close();
        return grades;
    }

    public void resetGrades() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_GRADES, null, null);
        db.close();
    }

    public double convertGradeToGPA(String grade) {
        switch (grade) {
            case "A+":
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }
}