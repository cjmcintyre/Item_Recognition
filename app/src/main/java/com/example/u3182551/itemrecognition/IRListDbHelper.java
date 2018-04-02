package com.example.u3182551.itemrecognition;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by corey on 26/03/2018.
 */



public class IRListDbHelper extends SQLiteOpenHelper {
    public IRListDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String TABLE_NAME = "events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE_RESOURCE = "image_resource";

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_TITLE + " text, " +
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
                    res.getInt(res.getColumnIndex(COLUMN_IMAGE_RESOURCE)) );
            eventList.add(event);
            res.moveToNext();
        }
        return eventList;
    }

    public boolean insertEvent(ItemsRecognized event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, event.getTitle());
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


}