package com.sachith.gpacalculator;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private static final String SQL_CREATE_ENTRIES_MODULE =
            "CREATE TABLE IF NOT EXISTS " + UserReaderDB.UserEntry.TABLE_NAME_MODULE + " (" +
                    UserReaderDB.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " TEXT," +
                    UserReaderDB.UserEntry.COLUMN_NAME_DEPARTMENT + " TEXT," +
                    UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " INTEGER," +
                    UserReaderDB.UserEntry.COLUMN_NAME_MODULE_CODE + " TEXT," +
                    UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME + " TEXT," +
                    UserReaderDB.UserEntry.COLUMN_NAME_CREDITS + " REAL)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL(SQL_CREATE_ENTRIES_MODULE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.editName);
        final CardView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String index = name.getText().toString();
                if (!index.trim().equals("")) {
                    Intent intent = new Intent(v.getContext(), DisplayDeptActivity.class);
                    intent.putExtra("Index", index.toUpperCase());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
