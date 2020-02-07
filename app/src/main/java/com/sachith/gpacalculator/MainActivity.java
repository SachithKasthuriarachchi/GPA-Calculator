package com.sachith.gpacalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.editName);
        final CardView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String index = name.getText().toString();
                UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {
                        UserReaderDB.UserEntry.COLUMN_NAME_INDEX
                };

                String selection = UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " = ?";
                String[] selectionArgs = {index};
                Cursor cursor = db.query(
                        UserReaderDB.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                List<String> indexes = new ArrayList<String>();
                while (cursor.moveToNext()) {
                    String indexNo = cursor.getString(cursor.getColumnIndexOrThrow(
                            UserReaderDB.UserEntry.COLUMN_NAME_INDEX
                    ));
                    indexes.add(indexNo);
                }
                if (indexes.size()==0) {
                    final String SQL_MY_RESULTS =
                            "CREATE TABLE " + "me" + index + " (" +
                                    UserReaderDB.UserEntry._ID + " INTEGER PRIMARY KEY," +
                                    UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " INTEGER," +
                                    UserReaderDB.UserEntry.COLUMN_NAME_CREDITS + " REAL," +
                                    UserReaderDB.UserEntry.COLUMN_NAME_GPA + " REAL)";

                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(UserReaderDB.UserEntry.COLUMN_NAME_INDEX, index);
                    long newRowId = database.insert(UserReaderDB.UserEntry.TABLE_NAME,
                            null, values);
                    database.execSQL(SQL_MY_RESULTS);
                }
                else {
                    System.out.println(indexes.size());
                }

                Intent intent = new Intent(v.getContext(), DisplayDeptActivity.class);
                System.out.println("Main*************" + index);
                intent.putExtra("Index", index);
                startActivity(intent);

            }
        });
    }


}
