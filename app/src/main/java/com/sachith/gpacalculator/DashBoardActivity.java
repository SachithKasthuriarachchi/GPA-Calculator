package com.sachith.gpacalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.adapter.DashBoardAdapter;
import com.sachith.gpacalculator.model.DashboardObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashBoardActivity extends AppCompatActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        ArrayList<DashboardObject> finalResults = getDashBoardInfo();

        if (finalResults.size() > 0) {
            final DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(
                    this, finalResults, getIntent().getStringExtra("Index"));
            final ListView listView = findViewById(R.id.listViewDash);
            listView.setAdapter(dashBoardAdapter);

            final View footer = getLayoutInflater().inflate(R.layout.footer_dashboard, null);
            final PieChartView pieChartView = footer.findViewById(R.id.footerPie);
            final List<SliceValue> pieDatum = new ArrayList<>();
            double overall_gpa = getOverallGPA(finalResults);
            pieDatum.add(new SliceValue((float) overall_gpa, this.getResources().getColor(R.color.toolbar)));
            pieDatum.add(new SliceValue((float) (4.2 - overall_gpa), Color.WHITE));
            final PieChartData data = new PieChartData(pieDatum);
            data.setHasCenterCircle(true).setCenterText1("Overall GPA")
                    .setCenterText1FontSize(20)
                    .setCenterText2(String.valueOf(format.format(overall_gpa)))
                    .setCenterText2FontSize(18);
            pieChartView.setPieChartData(data);

            listView.addFooterView(pieChartView);
        }


    }

    @Override
    /*
     * This will return to the department listing
     */
    public void onBackPressed() {
        Intent intent = new Intent(this, DisplayDeptActivity.class);
        intent.putExtra("Index", getIntent().getStringExtra("Index"));
        startActivity(intent);
        finish();
    }

    /**
     * @return semester results detail.
     */
    private ArrayList<DashboardObject> getDashBoardInfo() {

        Intent intent = getIntent();
        String index = intent.getStringExtra("Index");

        UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER,
                UserReaderDB.UserEntry.COLUMN_NAME_CREDITS,
                UserReaderDB.UserEntry.COLUMN_NAME_GPA
        };

        String selection = UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " =?";
        String[] selectionArgs = {index};
        String sortOrder = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " ASC";
        Cursor cursor = database.query(
                UserReaderDB.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<DashboardObject> objects = new ArrayList<>();

        while (cursor.moveToNext()) {
            int semester = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER)
            );
            double gpa = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_GPA)
            );
            double credits = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS)
            );
            DashboardObject dashboardObject = new DashboardObject(credits, gpa, semester);
            objects.add(dashboardObject);
        }
        cursor.close();

        return objects;
    }

    /**
     * @param results results and credits in each semester
     * @return Overall GPA
     */
    private double getOverallGPA(ArrayList<DashboardObject> results) {
        double numerator = 0;
        for (DashboardObject o : results) {
            numerator += (o.getCredits() * o.getGPA());
        }
        return (numerator / getTotalCredits(results));
    }

    /**
     * @param results results and credits in each semester
     * @return total credits
     */
    private double getTotalCredits(ArrayList<DashboardObject> results) {

        double sum = 0;
        for (DashboardObject o : results) {
            sum += o.getCredits();
        }
        return sum;
    }
}
