package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.model.DashboardObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashBoardAdapter extends ArrayAdapter<DashboardObject> {

    public DashBoardAdapter(Context context, ArrayList<DashboardObject> result) {
        super(context, 0, result);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DashboardObject result = getItem(position);
        System.out.println("You Are Here");
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.piechart_view, parent, false);
        }

        TextView semester = convertView.findViewById(R.id.semValue);
        semester.setText(String.valueOf(result.getSemester()));

        TextView gpa = convertView.findViewById(R.id.gpaVal);
        gpa.setText(String.valueOf(result.getGPA()));

        TextView credits = convertView.findViewById(R.id.creditsVal);
        credits.setText(String.valueOf(result.getCredits()));

        PieChartView pieChartView = convertView.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue((float) result.getGPA(), Color.BLUE));
        pieData.add(new SliceValue((float) (4.2 - result.getGPA()), Color.WHITE));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true);
        pieChartView.setPieChartData(pieChartData);

        return convertView;
    }
}
