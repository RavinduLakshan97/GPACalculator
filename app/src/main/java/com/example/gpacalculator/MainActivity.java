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
        final Button addButton = findViewById(R.id.addButton);
        final Button calculateButton = findViewById(R.id.calculateButton);
        final TextView gpaTextView = findViewById(R.id.gpaTextView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectEditText.getText().toString();
                String gradeStr = gradeEditText.getText().toString();
                if (!subject.isEmpty() && !gradeStr.isEmpty()) {
                    double grade = Double.parseDouble(gradeStr);
                    gpaCalculator.insertGrade(subject, grade);
                    subjectEditText.setText("");
                    gradeEditText.setText("");
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double gpa = gpaCalculator.calculateGPA();
                gpaTextView.setText("GPA: " + gpa);
            }
        });
    }
}