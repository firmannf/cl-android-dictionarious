package com.firmannf.dictionarious.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by codelabs on 23/07/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_ENGLISH =
            "CREATE TABLE " + DatabaseContract.TABLE_ENGLISH_NAME + " (" +
                    DatabaseContract.DictionaryColumns.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DatabaseContract.DictionaryColumns.COLUMN_WORD + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DatabaseContract.DictionaryColumns.COLUMN_MEANING + TEXT_TYPE + NOT_NULL +
                    " )";
    private static final String SQL_CREATE_TABLE_INDONESIA =
            "CREATE TABLE " + DatabaseContract.TABLE_INDONESIA_NAME + " (" +
                    DatabaseContract.DictionaryColumns.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DatabaseContract.DictionaryColumns.COLUMN_WORD + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DatabaseContract.DictionaryColumns.COLUMN_MEANING + TEXT_TYPE + NOT_NULL +
                    " )";
    private static final String SQL_DROP_TABLE_ENGLISH =
            "DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ENGLISH_NAME;
    private static final String SQL_DROP_TABLE_INDONESIA =
            "DROP TABLE IF EXISTS " + DatabaseContract.TABLE_INDONESIA_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ENGLISH);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_ENGLISH);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_INDONESIA);
        onCreate(sqLiteDatabase);
    }
}
