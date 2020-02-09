package com.sachith.gpacalculator;

import android.provider.BaseColumns;

public final class UserReaderDB {

    private UserReaderDB() {
    }

    public static class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_INDEX = "indexno";
        public static final String COLUMN_NAME_SEMESTER = "semester";
        public static final String COLUMN_NAME_CREDITS = "credits";
        public static final String COLUMN_NAME_GPA = "gpa";
    }
}
