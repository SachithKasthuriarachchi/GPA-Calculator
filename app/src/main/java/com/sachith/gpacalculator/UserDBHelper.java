package com.sachith.gpacalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserReaderDB.UserEntry.TABLE_NAME + " (" +
                    UserReaderDB.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " TEXT," +
                    UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " INTEGER," +
                    UserReaderDB.UserEntry.COLUMN_NAME_CREDITS + " REAL," +
                    UserReaderDB.UserEntry.COLUMN_NAME_GPA + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserReaderDB.UserEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserReader.db";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
