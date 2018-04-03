package com.example.u3182551.itemrecognition;

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



public class IRListDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "IRListDbHelper";
    private static final String TABLE_NAME = "IRDatabase";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_RESOURCE = "image_resource";

    public IRListDbHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TITLE + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void updateName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_TITLE +
                " = '" + newName + "' WHERE " + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_TITLE + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_TITLE + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }




}






/*
public class IRListDbHelper extends SQLiteOpenHelper {
    public IRListDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_RESOURCE = "image_resource";

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_TITLE + " text, " +
                COLUMN_DESCRIPTION + "text, " +
                COLUMN_IMAGE_RESOURCE + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<ItemsRecognized> getAllEvents() {
        ArrayList<ItemsRecognized> eventList = new ArrayList<ItemsRecognized>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            ItemsRecognized event = new ItemsRecognized(
                    res.getString(res.getColumnIndex(COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(COLUMN_DESCRIPTION),
                    res.getInt(res.getColumnIndex(COLUMN_IMAGE_RESOURCE));
            eventList.add(event);
            res.moveToNext();
        }
        return eventList;
    }

    public boolean insertEvent(ItemsRecognized event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, event.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, event.getDescription());
        contentValues.put(COLUMN_IMAGE_RESOURCE, event.getImageResource());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteEvent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[]{id});
    }

    public boolean updateEvent(String id, ItemsRecognized event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, event.getTitle());
        contentValues.put(COLUMN_IMAGE_RESOURCE, event.getImageResource());
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{id});
        return true;
    }


}*/
