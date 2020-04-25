package com.sachith.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.adapter.SemesterAdapter;

import java.util.ArrayList;

public class DisplaySemesters extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.display_semester_entc);

        ArrayList<String> semesters = getAllSemesters();
        String department = getIntent().getStringExtra("Department");
        String index = getIntent().getStringExtra("Index");
        SemesterAdapter semesterAdapter = new SemesterAdapter(this, semesters, department, index);
        ListView listView = findViewById(R.id.semsterList);
        listView.setAdapter(semesterAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DisplayDeptActivity.class);
        intent.putExtra("Index", getIntent().getStringExtra("Index"));
        startActivity(intent);
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
