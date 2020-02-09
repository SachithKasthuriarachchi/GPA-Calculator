package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.DashBoardActivity;
import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.UserDBHelper;
import com.sachith.gpacalculator.UserReaderDB;
import com.sachith.gpacalculator.model.DashboardObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DashBoardAdapter extends ArrayAdapter<DashboardObject> {

    private String index;

    public DashBoardAdapter(Context context, ArrayList<DashboardObject> result, String index) {
        super(context, 0, result);
        this.index = index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DashboardObject result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.piechart_view, parent, false);
        }

        /*
         * Displaying the current semester in dashboard.
         */
        TextView semester = convertView.findViewById(R.id.semValue);
        semester.setText(String.valueOf(result.getSemester()));

        /*
         * Displaying the GPA in dashboard
         */
        TextView gpa = convertView.findViewById(R.id.gpaVal);
        gpa.setText(String.valueOf(result.getGPA()));

        /*
          Displaying the total credits for a semester
         */
        TextView credits = convertView.findViewById(R.id.creditsVal);
        credits.setText(String.valueOf(result.getCredits()));

        /*
          PieChart for each semester
         */
        PieChartView pieChartView = convertView.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue((float) result.getGPA(), getContext().getResources().getColor(R.color.toolbar)));
        pieData.add(new SliceValue((float) (4.2 - result.getGPA()), Color.WHITE));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true);
        pieChartView.setPieChartData(pieChartData);

        CardView cardView = convertView.findViewById(R.id.piechartCard);

        /*
           The semester record will entirely be deleted from the database once the
           dashboard semester item is long pressed.
         */
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(result);
                UserDBHelper helper = new UserDBHelper(getContext());
                SQLiteDatabase database = helper.getWritableDatabase();
                String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " LIKE ?";
                String[] selectionArgs = {String.valueOf(result.getSemester())};
                database.delete(UserReaderDB.UserEntry.TABLE_NAME, selection, selectionArgs);
                Intent intent = new Intent(v.getContext(), DashBoardActivity.class);
                intent.putExtra("Index", index);
                v.getContext().startActivity(intent);
                return true;
            }
        });

        return convertView;
    }
}
