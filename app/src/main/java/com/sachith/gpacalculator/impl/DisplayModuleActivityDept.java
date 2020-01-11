package com.sachith.gpacalculator.impl;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.DatabaseHelper;
import com.sachith.gpacalculator.DisplayCalculatorActivity;
import com.sachith.gpacalculator.DisplayModules;
import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.adapter.ModuleAdapter;
import com.sachith.gpacalculator.model.Module;

import java.util.ArrayList;

public class DisplayModuleActivityDept extends AppCompatActivity implements DisplayModules {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_modules);
        Intent intent = getIntent();
        String tableName = intent.getStringExtra("Department") + intent.getStringExtra("Semester");
        ArrayList<Module> allModules = getAllModules("MODULES.db", tableName);
        final ModuleAdapter moduleAdapter = new ModuleAdapter(this, allModules);
        ListView listView = (ListView) findViewById(R.id.listViewMod);

        /**
         * Adding the "NEXT" button to module pages
         */
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT
        );

        Button button = new Button(this);
        button.setLayoutParams(params);
        button.setText(getResources().getString(R.string.nextButton));
        button.setTextColor(getResources().getColor(R.color.colorText));
        button.setClickable(true);
        button.setFocusable(true);

        TypedArray selectableItemBG = this.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setForeground(selectableItemBG.getDrawable(0));
        }
        selectableItemBG.recycle();

        button.setBackgroundColor(getResources().getColor(R.color.colorSemList));
        listView.addFooterView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Module> selectedModules = moduleAdapter.getSelectedModules();

                if (selectedModules.size()>0) {

                    Intent intent = new Intent(v.getContext(), DisplayCalculatorActivity.class);
                    intent.putParcelableArrayListExtra("Sel_Modules", selectedModules);
                    startActivity(intent);
                }
            }
        });

        listView.setAdapter(moduleAdapter);
    }

    @Override
    public ArrayList<Module> getAllModules(String dbName, String tableName) {

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
        return allModules;
    }
}
