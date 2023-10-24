package com.example.gpacalculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewGradesActivity extends AppCompatActivity {

    private GPACalculator gpaCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grades);

        gpaCalculator = new GPACalculator(this);

        TextView gradesTextView = findViewById(R.id.gradesTextView);
        String gradesList = gpaCalculator.getGradesList();
        gradesTextView.setText(gradesList);
    }
}