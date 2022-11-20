package com.example.trips.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Trip.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExpenseEntry.SQL_CREATE_TABLE);
        db.execSQL(TripEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TripEntry.SQL_DELETE_TABLE);
        db.execSQL(ExpenseEntry.SQL_DELETE_TABLE);

        onCreate(db);
    }
}
