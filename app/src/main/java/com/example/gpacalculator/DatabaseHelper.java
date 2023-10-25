package com.example.gpacalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String TABLE_GRADES = "grades";


    private static final int DATABASE_VERSION = 2;


    private static final String DATABASE_NAME = "gpa_calculator.db";


    private static final String COLUMN_ID = "id";
    static final String COLUMN_SUBJECT = "subject";
    static final String COLUMN_GRADE = "grade";
    static final String COLUMN_CREDITS = "credits";


    private static final String CREATE_TABLE_GRADES = "CREATE TABLE "
            + TABLE_GRADES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_GRADE + " TEXT," + COLUMN_CREDITS + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GRADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        onCreate(db);
    }


    public double convertGradeToGPA(String grade) {
        switch (grade) {
            case "A+":
                return 4.0;
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


    public double calculateGPA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GRADES, new String[]{COLUMN_GRADE, COLUMN_CREDITS}, null, null, null, null, null);
        double totalGPA = 0.0;
        int totalCredits = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String grade = cursor.getString(cursor.getColumnIndex(COLUMN_GRADE));
            @SuppressLint("Range") int credits = cursor.getInt(cursor.getColumnIndex(COLUMN_CREDITS));
            totalGPA += convertGradeToGPA(grade) * credits;
            totalCredits += credits;
        }
        cursor.close();
        return totalCredits > 0 ? totalGPA / totalCredits : 0.0;
    }
}