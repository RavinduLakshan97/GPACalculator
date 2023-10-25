package com.example.gpacalculator;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewGradesActivity extends AppCompatActivity {

    private GPACalculator gpaCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grades);

        gpaCalculator = new GPACalculator(this);

        TableLayout gradesTable = findViewById(R.id.gradesTable);
        TextView totalGpaTextView = findViewById(R.id.totalGpaTextView);

        List<Grade> grades = gpaCalculator.getAllGrades();
        double totalGpa = 0.0;
        int totalCredits = 0;

        // Add table headers
        TableRow headers = new TableRow(this);
        headers.addView(createTextView("Subject"));
        headers.addView(createTextView("Grade"));
        headers.addView(createTextView("Credits"));
        headers.addView(createTextView("PV"));
        gradesTable.addView(headers);

        // Add grades to table
        for (Grade grade : grades) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(grade.getSubject()));
            row.addView(createTextView(String.valueOf(grade.getGpa())));
            row.addView(createTextView(String.valueOf(grade.getCredits())));
            double pv = grade.getGpa() * grade.getCredits();
            row.addView(createTextView(String.format("%.2f", pv)));
            gradesTable.addView(row);

            totalGpa += pv;
            totalCredits += grade.getCredits();
        }

        // Calculate and display total GPA
        double gpa = totalCredits > 0 ? totalGpa / totalCredits : 0.0;
        totalGpaTextView.setText("Total GPA: " + String.format("%.2f", gpa));
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(16, 16, 16, 16);
        return textView;
    }
}