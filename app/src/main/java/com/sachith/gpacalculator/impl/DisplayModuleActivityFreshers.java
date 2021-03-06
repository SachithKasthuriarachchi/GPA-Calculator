package com.sachith.gpacalculator.impl;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.AddModuleActivity;
import com.sachith.gpacalculator.DatabaseHelper;
import com.sachith.gpacalculator.DisplayCalculatorActivity;
import com.sachith.gpacalculator.DisplayDeptActivity;
import com.sachith.gpacalculator.DisplayModules;
import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.UserDBHelper;
import com.sachith.gpacalculator.UserReaderDB;
import com.sachith.gpacalculator.adapter.ModuleAdapter;
import com.sachith.gpacalculator.model.Module;

import java.util.ArrayList;
import java.util.HashSet;

public class DisplayModuleActivityFreshers extends AppCompatActivity implements DisplayModules {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_modules);

        ArrayList<Module> allModules = getAllModules("MODULES.db", "FIRSTSEM", 1);
        final ModuleAdapter moduleAdapter = new ModuleAdapter(this, allModules,
                "1",
                getIntent().getStringExtra("Index"),
                "Fresher");
        ListView listView = (ListView) findViewById(R.id.listViewMod);

        /**
         * Adding the "NEXT" button to module pages
         */
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT
        );

        CardView addModule = findViewById(R.id.addMod);
        addModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), AddModuleActivity.class);
                intent1.putExtra("Department", "Fresher");
                intent1.putExtra("Semester", "1");
                intent1.putExtra("Index", getIntent().getStringExtra("Index"));
                intent1.putExtra("Class", "DisplayModuleActivityFreshers");
                startActivity(intent1);
            }
        });

        Button button = new Button(this);
        button.setLayoutParams(params);
        button.setText(getResources().getString(R.string.nextButton));
        button.setTextColor(getResources().getColor(R.color.colorText));
        button.setClickable(true);
        button.setFocusable(true);

        float density = this.getResources().getDisplayMetrics().density;
        button.setTop((int) (10 * density));
        TypedArray selectableItemBG = this.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setForeground(selectableItemBG.getDrawable(0));
        }
        selectableItemBG.recycle();

        button.setBackgroundColor(getResources().getColor(R.color.colorSemList));
        listView.addFooterView(button);
        listView.setAdapter(moduleAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Module> selectedModules = moduleAdapter.getSelectedModules();
                Intent intent = new Intent(v.getContext(), DisplayCalculatorActivity.class);
                intent.putParcelableArrayListExtra("Sel_Modules", selectedModules);
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                intent.putExtra("dept", "SEMESTER_1");
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, DisplayDeptActivity.class);
        intent.putExtra("Index", getIntent().getStringExtra("Index"));
        startActivity(intent);
    }

    @Override
    public ArrayList<Module> getAllModules(String dbName, String tableName, int semester) {

        DatabaseHelper helper = new DatabaseHelper(this, dbName);
        SQLiteDatabase database = helper.getReadableDatabase();
        ArrayList<Module> allModules = new ArrayList<Module>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {

                Module module = new Module(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2));

                allModules.add(module);
            }
            cursor.close();
            database.close();
            helper.close();
        }
        return add(getAddedModules(), allModules);
    }

    private ArrayList<Module> add(ArrayList<Module> addedModules, ArrayList<Module> dbModules) {

        for (int i = 0; i < addedModules.size(); i++) {
            for (int j = 0; j < dbModules.size(); j++) {
                if (addedModules.get(i).getName().equals(dbModules.get(j).getName())) {
                    dbModules.remove(j);
                }
            }
        }
        addedModules.addAll(dbModules);
        ArrayList<Module> result = new ArrayList<Module>(new HashSet<Module>(addedModules));
        return result;
    }

    private ArrayList<Module> getAddedModules() {
        Intent intent = getIntent();
        String index = intent.getStringExtra("Index");
        String department = "Fresher";

        UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME,
                UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE,
                UserReaderDB.UserEntry.COLUMN_NAME_CREDITS
        };

        String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_DEPARTMENT + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " =?";
        String[] selectionArgs = {String.valueOf(1), department, index};
        String sortOrder = UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME + " DESC";

        Cursor cursor = database.query(
                UserReaderDB.UserEntry.TABLE_NAME_MODULE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        ArrayList<Module> addedModules = new ArrayList<>();

        while (cursor.moveToNext()) {
            String moduleName = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME)
            );

            String moduleCode = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE)
            );

            double credits = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS)
            );
            Module module = new Module(moduleCode, moduleName, credits);
            addedModules.add(module);
        }
        cursor.close();
        return addedModules;
    }
}
