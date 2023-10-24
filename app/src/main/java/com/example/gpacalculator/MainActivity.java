package com.example.gpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GPACalculator gpaCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpaCalculator = new GPACalculator(this);

        final EditText subjectEditText = findViewById(R.id.subjectEditText);
        final EditText gradeEditText = findViewById(R.id.gradeEditText);
        final EditText creditsEditText = findViewById(R.id.creditsEditText);
        final Button addButton = findViewById(R.id.addButton);
        final TextView gpaTextView = findViewById(R.id.gpaTextView);
        final Button resetButton = findViewById(R.id.resetButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectEditText.getText().toString();
                String grade = gradeEditText.getText().toString().toUpperCase();
                String creditsString = creditsEditText.getText().toString();
                if (!subject.isEmpty() && !grade.isEmpty() && !creditsString.isEmpty()) {
                    int credits = Integer.parseInt(creditsString);
                    gpaCalculator.insertGrade(subject, grade, credits);
                    subjectEditText.setText("");
                    gradeEditText.setText("");
                    creditsEditText.setText("");
                }
            }
        });

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double gpa = gpaCalculator.calculateGPA();
                // Now you can use the gpa value as needed
                // For example, display it in a TextView
                TextView textViewGPA = findViewById(R.id.gpaTextView);
                textViewGPA.setText("GPA: " + String.format("%.2f", gpa));
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear GPA value from TextView
                gpaTextView.setText("GPA: 0.0");

                // Clear all data from grades table in database
                gpaCalculator.resetGrades();
            }
        });

    }
}