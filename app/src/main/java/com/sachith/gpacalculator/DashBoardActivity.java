package com.sachith.gpacalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.adapter.DashBoardAdapter;
import com.sachith.gpacalculator.model.DashboardObject;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        ArrayList<DashboardObject> finalResults = getDashBoardInfo();

        if (finalResults.size() > 0) {
            final DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(this, finalResults);
            ListView listView = findViewById(R.id.listViewDash);
            listView.setAdapter(dashBoardAdapter);
        }


    }

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
            System.out.println("###################################################" + semester);
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
}
