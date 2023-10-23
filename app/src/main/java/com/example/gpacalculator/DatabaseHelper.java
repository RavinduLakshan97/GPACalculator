package com.example.gpacalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_GRADES = "grades";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gpa_calculator.db";

    // Table Names

    // Column names
    private static final String COLUMN_ID = "id";
    static final String COLUMN_SUBJECT = "subject";
    static final String COLUMN_GRADE = "grade";

    // Table create statement
    private static final String CREATE_TABLE_GRADES = "CREATE TABLE "
            + TABLE_GRADES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_GRADE + " REAL" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_GRADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);

        // create new tables
        onCreate(db);
    }
}