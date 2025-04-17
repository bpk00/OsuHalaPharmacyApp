package com.s22010075.osuhalapharmacyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{
    // define database related variables ( db, tables, columns)
    public static final String DATABASE_NAME = "myWishlistDB.db";
    public static final String TABLE_NAME = "myWishlistDB_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "TYPE";
    public static  final String COL_3 = "NAME";
    public static  final String COL_4 = "SPECIAL_NOTES";

    // constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // methods
    // create table and queries
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table SQL queries
        db.execSQL(" CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TYPE TEXT, NAME TEXT, SPECIAL_NOTES TEXT )");
    }

    // drop table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // insert data operation
    public boolean insertData(String type, String name, String specialNotes) {
        // define database object
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, specialNotes);

        // initialize table data
        long results = db.insert(TABLE_NAME, null, contentValues);
        // check table data
        if (results == -1)
            return false;
        else
            return true;
    }

    // view data operation
    public Cursor viewAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return results;
    }

    // update data operation
    public boolean updateData (String id, String type, String name, String specialNotes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, specialNotes);
        db.update (TABLE_NAME, contentValues, " id = ? ", new String[] {id});
        return true;
    }

    // delete data operation
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, " ID = ? ", new String[] {id});
    }
}