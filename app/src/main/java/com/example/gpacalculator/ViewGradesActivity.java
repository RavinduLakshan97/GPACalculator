package com.example.gpacalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewGradesActivity extends AppCompatActivity {

    private GPACalculator gpaCalculator;
    private Spinner gradeSpinner;
    private Button totalGpaButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grades);

        gpaCalculator = new GPACalculator(this);

        TableLayout gradesTable = findViewById(R.id.gradesTable);
        totalGpaButton = findViewById(R.id.totalGpaButton);
        gradeSpinner = findViewById(R.id.gradeSpinner);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);

        List<Grade> grades = gpaCalculator.getAllGrades();
        double totalGpa = 0.0;
        int totalCredits = 0;

        TableRow headers = new TableRow(this);
        headers.addView(createTextView("Subject"));
        headers.addView(createTextView("Grade"));
        headers.addView(createTextView("Credits"));
        headers.addView(createTextView("PV"));
        gradesTable.addView(headers);

        if (grades != null) {
            Log.d("ViewGradesActivity", "Grades size: " + grades.size());
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
        } else {
            Log.d("ViewGradesActivity", "Grades is null");
        }

        double gpa = totalCredits > 0 ? totalGpa / totalCredits : 0.0;
        totalGpaButton.setText("Total GPA: " + String.format("%.2f", gpa));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(16, 16, 16, 16);
        textView.setBackgroundResource(R.drawable.field_border);
        return textView;
    }
}
