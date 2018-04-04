package com.example.u3182551.itemrecognition;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by corey on 26/03/2018.
 */
@SuppressWarnings("DefaultFileTemplate")
public class IRListDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String TAG = "IRListDbHelper";
    private static final String TABLE_NAME = "IRDatabase";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    public IRListDbHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT) ";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addData(String item, String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues.put(COLUMN_TITLE, item);
        contentValues2.put(COLUMN_DESCRIPTION, item2);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        return db.insertOrThrow(TABLE_NAME, null, contentValues);
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
    }

    public ArrayList<Images> getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Images> imageList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        @SuppressLint("Recycle") Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        while (!data.isAfterLast()) {
            Images image = new Images();
            image.setId(data.getInt(0));
            image.setInformation(data.getString(data.getColumnIndex(COLUMN_TITLE)));
            image.setTitle(data.getString(data.getColumnIndex(COLUMN_DESCRIPTION)));
            imageList.add(image);
            data.moveToNext();
        }
        return imageList;
    }


    public void updateName(String newName, String newTitle, long id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME +
                " SET " + COLUMN_TITLE + " = '" + newName + "', "
                + COLUMN_DESCRIPTION + " = '" + newTitle +
                "' WHERE " + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_TITLE + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(long id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_TITLE + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}