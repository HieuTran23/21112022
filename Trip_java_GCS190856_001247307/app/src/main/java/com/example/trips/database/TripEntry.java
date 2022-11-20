package com.example.trips.database;

public class TripEntry {
    public static final String TABLE_NAME = "trip";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_DATE_OF_TRIP = "date_of_trip";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_RISK_ASSESSMENT = "risk_assessment";
    public static final String COL_PEOPLE_NUMBER = "peopleNumber";
    public static final String COL_TYPE = "type";
    
    private TripEntry(){}

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME + " TEXT NOT NULL," +
                    COL_DESTINATION + " TEXT NOT NULL," +
                    COL_DATE_OF_TRIP + " TEXT NOT NULL," +
                    COL_DESCRIPTION + " TEXT NOT NULL," +
                    COL_PEOPLE_NUMBER + " TEXT," +
                    COL_TYPE + " TEXT," +
                    COL_RISK_ASSESSMENT + " INTEGER NOT NULL)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
