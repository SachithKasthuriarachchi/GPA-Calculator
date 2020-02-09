package com.sachith.gpacalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowResult extends AppCompatActivity {

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result);

        TextView gpa = findViewById(R.id.gpa);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myGPA = format.format(bundle.getDouble("GPA"));
        double credits = bundle.getDouble("Credits");
        String semDept = intent.getStringExtra("dept");
        int semester = Integer.parseInt(semDept.substring(semDept.length() - 1));

        updateDB(intent.getStringExtra("Index"), semester, Double.parseDouble(myGPA),
                credits);

        gpa.setText(myGPA);

        RelativeLayout relativeLayout = findViewById(R.id.showResults);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), DashBoardActivity.class);
                intent1.putExtra("Index", intent.getStringExtra("Index"));
                startActivity(intent1);
            }
        });

    }

    private boolean searchForQuery(String index, int semester, UserDBHelper dbHelper) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER
        };

        String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " =?";
        String[] selectionArgs = {String.valueOf(semester), index};

        Cursor cursor = database.query(
                UserReaderDB.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<Integer> semIDs = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER));
            semIDs.add(itemId);
        }
        cursor.close();

        return semIDs.size() != 0;

    }

    private void updateDB(String index, int semester, double gpa, double credits) {

        UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if (searchForQuery(index, semester, dbHelper)) {

            ContentValues values = new ContentValues();
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS, credits);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_GPA, gpa);

            String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " LIKE ?";
            String[] selectionArgs = {String.valueOf(semester)};

            database.update(
                    UserReaderDB.UserEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );
        } else {

            ContentValues values = new ContentValues();
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_INDEX, index);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS, credits);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_GPA, gpa);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER, semester);
            database.insert(UserReaderDB.UserEntry.TABLE_NAME, null, values);
        }
    }
}
