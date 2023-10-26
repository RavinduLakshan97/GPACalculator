package com.example.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GPACalculator gpaCalculator;
    private EditText subjectEditText;
    private Spinner gradeSpinner;
    private EditText creditsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpaCalculator = new GPACalculator(this);

        subjectEditText = findViewById(R.id.subjectEditText);
        gradeSpinner = findViewById(R.id.gradeSpinner);
        creditsEditText = findViewById(R.id.creditsEditText);
        final Button addButton = findViewById(R.id.addButton);
        final Button calculateButton = findViewById(R.id.calculateButton);
        final Button resetButton = findViewById(R.id.resetButton);
        final Button viewButton = findViewById(R.id.viewButton);
        final TextView gpaTextView = findViewById(R.id.gpaTextView);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectEditText.getText().toString();
                String grade = gradeSpinner.getSelectedItem().toString();
                String credits = creditsEditText.getText().toString();
                if (!subject.isEmpty() && !grade.isEmpty() && !credits.isEmpty()) {
                    gpaCalculator.insertGrade(subject, grade, Integer.parseInt(credits));
                    subjectEditText.setText("");
                    gradeSpinner.setSelection(0);
                    creditsEditText.setText("");
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

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpaCalculator.resetGrades();
                gpaTextView.setText("GPA: 0.0");
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewGradesActivity.class);
                startActivity(intent);
            }
        });
    }
}