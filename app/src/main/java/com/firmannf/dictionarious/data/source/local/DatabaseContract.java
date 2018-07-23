package com.firmannf.dictionarious.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by codelabs on 23/07/18.
 */

public class DatabaseContract {

    public final static String TABLE_ENGLISH_NAME = "table_english";
    public final static String TABLE_INDONESIA_NAME = "table_indonesia";

    public static final class DictionaryColumns implements BaseColumns {
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_MEANING = "meaning";
    }
}
