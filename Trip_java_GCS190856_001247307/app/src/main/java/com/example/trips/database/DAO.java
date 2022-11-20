package com.example.trips.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trips.models.Expense;
import com.example.trips.models.Trip;

import java.util.ArrayList;

public class DAO {
    protected DbHelper dbHelper;
    protected SQLiteDatabase dbWrite, dbRead;

    public DAO(Context context) {
        dbHelper = new DbHelper(context);

        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbRead.close();
        dbWrite.close();
    }

    public void reset() {
        dbHelper.onUpgrade(dbWrite, 0, 0);
    }

    public long insertTrip(Trip trip){
        ContentValues values  = getTripValues(trip);

        return dbWrite.insert(TripEntry.TABLE_NAME, null, values);
    }

    public long updateTrip(Trip trip) {
        ContentValues values = getTripValues(trip);

        String selection = TripEntry.COL_ID + " =?";
        String[] selectionArgs = {String.valueOf(trip.getId())};

        return dbWrite.update(TripEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteTrip(long id){
        String selection = TripEntry.COL_ID + " =?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(TripEntry.TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<Trip> getTripList(Trip trip, String orderByColumn, boolean isDesc){
        String orderBy = getOrderByString(orderByColumn, isDesc);
        String selection = null;
        String[] selectionArgs = null;

        if (trip != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if (trip.getName() != null && !trip.getName().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_NAME + " LIKE ?";
                conditionList.add("%" + trip.getName() + "%");
            }

            if (trip.getDestination() != null && !trip.getDestination().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_DESTINATION + " LIKE ?";
                conditionList.add("%" + trip.getDestination() + "%");
            }

            if (trip.getDateOfTrip() != null && !trip.getDateOfTrip().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_DATE_OF_TRIP + " = ?";
                conditionList.add(trip.getDateOfTrip());
            }

            if (trip.getDescription() != null && !trip.getDescription().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_DESCRIPTION + "LIKE ?";
                conditionList.add("%" + trip.getDestination() + "%");
            }

            if (trip.getType() != null && !trip.getType().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_TYPE + " = ?";
                conditionList.add(trip.getType());
            }

            if (trip.getPeopleNumber() != null && !trip.getPeopleNumber().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_PEOPLE_NUMBER + " = ?";
                conditionList.add(trip.getPeopleNumber());
            }

            if (trip.getRiskAssessment() != -1) {
                selection += " AND " + TripEntry.COL_RISK_ASSESSMENT + " = ?";
                conditionList.add(String.valueOf(trip.getRiskAssessment()));
            }

            if (!selection.trim().isEmpty()) {
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }



        return fetchTrip(null, selection, selectionArgs, null, null, orderBy);
    }

    public Trip getTrip(long id){
        String selection = TripEntry.COL_ID + " =?";
        String[] selectionArgs= {String.valueOf(id)};

        return fetchTrip(null, selection, selectionArgs, null, null, null).get(0);
    }

    private ContentValues getTripValues(Trip trip){
        ContentValues values = new ContentValues();

        values.put(TripEntry.COL_NAME, trip.getName());
        values.put(TripEntry.COL_DESTINATION, trip.getDestination());
        values.put(TripEntry.COL_DATE_OF_TRIP, trip.getDateOfTrip());
        values.put(TripEntry.COL_DESCRIPTION, trip.getDescription());
        values.put(TripEntry.COL_RISK_ASSESSMENT, trip.getRiskAssessment());
        values.put(TripEntry.COL_TYPE, trip.getType());
        values.put(TripEntry.COL_PEOPLE_NUMBER, trip.getPeopleNumber());

        return values;
    }

    private ArrayList<Trip> fetchTrip(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        ArrayList<Trip> list = new ArrayList<>();

        Cursor cursor = dbRead.query(TripEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()){
            Trip tripItem = new Trip();


            tripItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TripEntry.COL_ID)));
            tripItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_NAME)));
            tripItem.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESTINATION)));
            tripItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESCRIPTION)));
            tripItem.setDateOfTrip(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DATE_OF_TRIP)));
            tripItem.setRiskAssessment(cursor.getInt((cursor.getColumnIndexOrThrow(TripEntry.COL_RISK_ASSESSMENT))));
            tripItem.setType(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_TYPE)));
            tripItem.setPeopleNumber(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_PEOPLE_NUMBER)));

            list.add(tripItem);
        }
        
        return list;
    }

    private String getOrderByString(String orderByColumn, boolean isDesc) {
        if (orderByColumn == null || orderByColumn.trim().isEmpty())
            return null;

        if (isDesc)
            return orderByColumn.trim() + " DESC";

        return orderByColumn.trim();
    }

//    Expense
    public long insertExpense(Expense expense) {
        ContentValues values = getExpenseValues(expense);

        return dbWrite.insert(ExpenseEntry.TABLE_NAME, null, values);
    }

    public long deleteExpense(long id) {
        String selection = ExpenseEntry.COL_ID + " =?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    private ContentValues getExpenseValues(Expense expense) {
        ContentValues values = new ContentValues();

        values.put(ExpenseEntry.COL_TYPE, expense.getType());
        values.put(ExpenseEntry.COL_AMOUNT, expense.getAmount());
        values.put(ExpenseEntry.COL_DATE, expense.getDate());
        values.put(ExpenseEntry.COL_TIME, expense.getTime());
        values.put(ExpenseEntry.COL_COMMENT, expense.getComment());
        values.put(ExpenseEntry.COL_TRIP_ID, expense.getTripId());

        return  values;
    }

    public ArrayList<Expense> getExpenseList(Expense expense, String orderByColumn, boolean isDesc) {
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if(expense != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if(expense.getTripId() != -1){
                selection += ExpenseEntry.COL_TRIP_ID + " = ?";
                conditionList.add(String.valueOf(expense.getTripId()));
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return getExpenseFromDB(null, selection, selectionArgs, null, null, orderBy);
    }

    private ArrayList<Expense> getExpenseFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Expense> list = new ArrayList<>();

        Cursor cursor = dbRead.query(ExpenseEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Expense expense = new Expense();

            expense.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_ID)));
            expense.setType(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TYPE)));
            expense.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_AMOUNT)));
            expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_DATE)));
            expense.setTime(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TIME)));
            expense.setComment((cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_COMMENT))));

            list.add(expense);
        }

        cursor.close();

        return list;
    }
}
