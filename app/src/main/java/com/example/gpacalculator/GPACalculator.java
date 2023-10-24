package com.example.gpacalculator;

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
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_GRADES + " (" +
                        DatabaseHelper.COLUMN_SUBJECT + ", " +
                        DatabaseHelper.COLUMN_GRADE + ", " +
                        DatabaseHelper.COLUMN_CREDITS + ") VALUES (?, ?, ?)",
                new Object[]{subject, grade, credits});
        db.close();
    }

    public double calculateGPA() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_GRADES, null);
        double totalPoints = 0;
        int totalCredits = 0;
        while (cursor.moveToNext()) {
            String grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE));
            int credits = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREDITS));
            totalPoints += convertGradeToPoints(grade) * credits;
            totalCredits += credits;
        }
        cursor.close();
        db.close();
        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }

    private double convertGradeToPoints(String grade) {
        // First, try to convert the grade to a number
        try {
            double numericGrade = Double.parseDouble(grade);
            // Convert numeric grade to letter grade
            if (numericGrade >= 90) {
                grade = "A+";

            } else if (numericGrade >= 80) {
                grade = "A";

            } else if (numericGrade >= 75) {
                grade = "A-";

            } else if (numericGrade >= 70) {
                grade = "B+";

            } else if (numericGrade >= 65) {
                grade = "B";

            } else if (numericGrade >= 60) {
                grade = "B-";

            } else if (numericGrade >= 55) {
                grade = "C+";

            } else if (numericGrade >= 45) {
                grade = "C";

            } else if (numericGrade >= 35) {
                grade = "C-";

            } else if (numericGrade >= 30) {
                grade = "D+";

            } else if (numericGrade >= 25) {
                grade = "D";

            } else {
                grade = "E";
            }
        } catch (NumberFormatException e) {
            // If the grade is not a number, do nothing and proceed with the original grade value
        }

        // Convert letter grade to grade points
        if (grade.equals("A+") || grade.equals("A")) {
            return 4.0;
        } else if (grade.equals("A-")) {
            return 3.7;
        } else if (grade.equals("B+")) {
            return 3.3;
        } else if (grade.equals("B")) {
            return 3.0;
        } else if (grade.equals("B-")) {
            return 2.7;
        } else if (grade.equals("C+"))  {
            return 2.3;
        } else if (grade.equals("C")) {
            return 2.0;
        } else if (grade.equals("C-")) {
            return 1.7;
        } else if (grade.equals("D+")) {
            return 1.3;
        } else if (grade.equals("D")) {
            return 1.0;
        } else if (grade.equals("E")) {
            return 0.0;
        } else {
            throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    public void resetGrades() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_GRADES);
        db.close();
    }
}