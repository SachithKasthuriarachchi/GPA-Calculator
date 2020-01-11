package com.sachith.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ShowResult extends AppCompatActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result);

        TextView gpa = findViewById(R.id.gpa);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myGPA = format.format(bundle.getDouble("GPA"));

        gpa.setText(myGPA);

    }
}
