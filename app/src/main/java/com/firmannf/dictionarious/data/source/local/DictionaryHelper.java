package com.firmannf.dictionarious.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.firmannf.dictionarious.data.DictionaryModel;

import java.util.ArrayList;

/**
 * Created by codelabs on 23/07/18.
 */

public class DictionaryHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<DictionaryModel> getDataByWord(String word, String tableName) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+tableName+" WHERE WORD LIKE '"+word+"%'", null);
        cursor.moveToFirst();

        ArrayList<DictionaryModel> dictionaries = new ArrayList<>();
        DictionaryModel dictionary;
        if (cursor.getCount() > 0) {
            do {
                dictionary = new DictionaryModel();
                dictionary.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_ID)));
                dictionary.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_WORD)));
                dictionary.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_MEANING)));
                dictionaries.add(dictionary);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();

        return dictionaries;
    }

    public ArrayList<DictionaryModel> getAllData(String tableName) {
        Cursor cursor = sqLiteDatabase.query(tableName,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.DictionaryColumns.COLUMN_ID + " ASC",
                null);
        cursor.moveToFirst();

        ArrayList<DictionaryModel> dictionaries = new ArrayList<>();
        DictionaryModel dictionary;
        if (cursor.getCount() > 0) {
            do {
                dictionary = new DictionaryModel();
                dictionary.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_ID)));
                dictionary.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_WORD)));
                dictionary.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.COLUMN_MEANING)));
                dictionaries.add(dictionary);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();

        return dictionaries;
    }

    public long insert(DictionaryModel mahasiswaModel, String tableName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DictionaryColumns.COLUMN_WORD, mahasiswaModel.getWord());
        values.put(DatabaseContract.DictionaryColumns.COLUMN_MEANING, mahasiswaModel.getMeaning());
        return sqLiteDatabase.insert(tableName, null, values);
    }

    public void beginTransaction() {
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccess() {
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction() {
        sqLiteDatabase.endTransaction();
    }

    public void insertTransaction(DictionaryModel dictionary, String tableName) {
        String sql = "INSERT INTO " + tableName + " (" + DatabaseContract.DictionaryColumns.COLUMN_WORD + ", " + DatabaseContract.DictionaryColumns.COLUMN_MEANING
                + ") VALUES (?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.bindString(1, dictionary.getWord());
        statement.bindString(2, dictionary.getMeaning());
        statement.execute();
        statement.clearBindings();
    }

    public int update(DictionaryModel dictionary, String tableName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DictionaryColumns.COLUMN_WORD, dictionary.getWord());
        values.put(DatabaseContract.DictionaryColumns.COLUMN_MEANING, dictionary.getMeaning());
        return sqLiteDatabase.update(tableName,
                values,
                DatabaseContract.DictionaryColumns.COLUMN_ID + "= '" + dictionary.getId() + "'",
                null);
    }

    public int delete(int id, String tableName) {
        return sqLiteDatabase.delete(tableName,
                DatabaseContract.DictionaryColumns.COLUMN_ID + " = '" + id + "'",
                null);
    }
}
