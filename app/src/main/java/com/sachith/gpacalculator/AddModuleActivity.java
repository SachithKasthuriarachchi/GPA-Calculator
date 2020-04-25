package com.sachith.gpacalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.impl.DisplayModuleActivityDept;
import com.sachith.gpacalculator.impl.DisplayModuleActivityFreshers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_module);

        final EditText moduleCode = findViewById(R.id.editModCode);
        final EditText moduleName = findViewById(R.id.editModName);
        final EditText moduleCredit = findViewById(R.id.editModCredit);

        CardView done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mod_code = moduleCode.getText().toString().toUpperCase().trim();
                String mod_name = moduleName.getText().toString().toUpperCase().trim();
                String mod_credit = moduleCredit.getText().toString().trim();

                if (!mod_code.equals("") && !mod_name.equals("") && isNumberValid(mod_credit)) {
                    Intent prevIntent = getIntent();
                    String sem = prevIntent.getStringExtra("Semester");
                    String index = prevIntent.getStringExtra("Index");
                    String department = prevIntent.getStringExtra("Department");
                    int semester = Integer.parseInt(Objects.requireNonNull(sem.substring(
                            sem.length() - 1
                    )));
                    double credits = Double.parseDouble(mod_credit);
                    String prev_class = prevIntent.getStringExtra("Class");
                    updateDB(index, department, semester, mod_code, mod_name, credits);

                    Intent intent;
                    if (prev_class.equals("DisplayModuleActivityDept")) {
                        intent = new Intent(v.getContext(), DisplayModuleActivityDept.class);
                    } else {
                        intent = new Intent(v.getContext(), DisplayModuleActivityFreshers.class);
                    }
                    intent.putExtra("Semester", sem);
                    intent.putExtra("Department", getIntent().getStringExtra("Department"));
                    intent.putExtra("Index", getIntent().getStringExtra("Index"));
                    startActivity(intent);
                }
            }
        });


    }

    private boolean isNumber(String num) {
        if (num == null) {
            return false;
        }
        try {
            double n = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the provided credits is positive
     *
     * @param num Provided credit
     * @return
     */
    private boolean isNumberValid(String num) {
        if (isNumber(num)) {
            double n = Double.parseDouble(num);
            return n > 0;
        }
        return false;
    }

    private boolean searchForQuery(String index, String department, int semester, String moduleCode, UserDBHelper dbHelper) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                UserReaderDB.UserEntry.COLUMN_NAME_CREDITS
        };

        String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_DEPARTMENT + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " =?";
        String[] selectionArgs = {String.valueOf(semester), moduleCode, department, index};

        Cursor cursor = database.query(
                UserReaderDB.UserEntry.TABLE_NAME_MODULE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<Integer> moduleNameIDs = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS));
            moduleNameIDs.add(itemId);
        }
        cursor.close();

        return moduleNameIDs.size() != 0;

    }

    private void updateDB(String index, String department, int semester, String moduleCode, String moduleName, double credits) {

        UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if (searchForQuery(index, department, semester, moduleCode, dbHelper)) {

            ContentValues values = new ContentValues();
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS, credits);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME, moduleName);

            String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " = ? AND " +
                    UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " = ? AND " +
                    UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE + " =?";
            String[] selectionArgs = {String.valueOf(semester), index, moduleCode};

            database.update(
                    UserReaderDB.UserEntry.TABLE_NAME_MODULE,
                    values,
                    selection,
                    selectionArgs
            );
        } else {

            ContentValues values = new ContentValues();
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_INDEX, index);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_DEPARTMENT, department);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS, credits);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME, moduleName);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE, moduleCode);
            values.put(UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER, semester);
            database.insert(UserReaderDB.UserEntry.TABLE_NAME_MODULE, null, values);
        }
    }

}
