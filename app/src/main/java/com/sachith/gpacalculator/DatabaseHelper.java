package com.sachith.gpacalculator;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
    }
}
