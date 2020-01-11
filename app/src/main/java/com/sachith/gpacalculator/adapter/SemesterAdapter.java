package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.impl.DisplayModuleActivityDept;

import java.util.ArrayList;

public class SemesterAdapter extends ArrayAdapter<String> {

    private String departmentName;

    public SemesterAdapter(Context context, ArrayList<String> semesters, String department) {
        super(context, 0, semesters);
        this.departmentName = department;
    }

    public SemesterAdapter(Context context, ArrayList<String> semesters) {
        super(context, 0, semesters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String semName = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.semestercard_view, parent, false);
        }

        final TextView textView = convertView.findViewById(R.id.semName);
        textView.setText(semName);
        CardView cardView = (CardView) convertView.findViewById(R.id.semsterCard);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayModuleActivityDept.class);
                intent.putExtra("Department", departmentName);
                intent.putExtra("Semester", getSemesterName(semName));
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private String getSemesterName(String semesterName) {

        String semester = semesterName.substring(semesterName.length() - 1);
        return "_SEMESTER_" + semester;
    }

}
