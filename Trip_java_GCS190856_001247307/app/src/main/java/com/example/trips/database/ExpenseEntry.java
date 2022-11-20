package com.example.trips.database;

public class ExpenseEntry {
    public static final String TABLE_NAME = "expense";
    public static final String COL_ID = "id";
    public static final String COL_TYPE = "type";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_COMMENT = "comment";
    public static final String COL_TRIP_ID = "trip_id";

    private ExpenseEntry(){}

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_TYPE + " TEXT NOT NULL," +
                    COL_AMOUNT + " TEXT NOT NULL," +
                    COL_DATE + " TEXT NOT NULL," +
                    COL_TIME + " TEXT NOT NULL," +
                    COL_COMMENT + " TEXT," +
                    COL_TRIP_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + COL_TRIP_ID + ") " +
                    "REFERENCES " + TripEntry.TABLE_NAME + "(" + TripEntry.COL_ID + "))";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
