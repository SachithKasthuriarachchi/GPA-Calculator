package com.sachith.gpacalculator.department.cpe;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.adapter.SemesterAdapter;

import java.util.ArrayList;

public class DisplaySemesterChemical extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.display_semester_entc);

        ArrayList<String> semesters = getAllSemesters();
        String department = getIntent().getStringExtra("Department");
        SemesterAdapter semesterAdapter = new SemesterAdapter(this, semesters, department);
        ListView listView = (ListView) findViewById(R.id.semsterList);
        listView.setAdapter(semesterAdapter);
    }

    private ArrayList<String> getAllSemesters() {

        ArrayList<String> semesters = new ArrayList<String>();
        semesters.add("SEMESTER 2");
        semesters.add("SEMESTER 3");
        semesters.add("SEMESTER 4");
        semesters.add("SEMESTER 5");
        semesters.add("SEMESTER 6");
        semesters.add("SEMESTER 7");
        semesters.add("SEMESTER 8");

        return semesters;
    }
}
