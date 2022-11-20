package com.example.trips.models;

import java.io.Serializable;

public class Expense implements Serializable {
    private long _id;
    private String _type;
    private int _amount;
    private String _date;
    private String _time;
    private String _comment;
    private long _tripId;

    public Expense() {
        _id = -1;
        _type = null;
        _amount = 0;
        _date = null;
        _time = null;
        _comment = null;
        _tripId = -1;
    }

    public Expense(long id, String type, int amount, String date, String time, String comment, long tripId) {
        _id = id;
        _type = type;
        _amount = amount;
        _date = date;
        _time = time;
        _comment = comment;
        _tripId = tripId;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public int getAmount() {return _amount; }
    public void setAmount(int amount) {_amount = amount; }

    public String getDate() {return _date; }
    public void setDate(String date) {_date = date; }

    public String getTime() {return _time;}
    public void setTime(String time) { _time = time; }

    public String getComment() { return _comment; }
    public void setComment(String comment) { _comment = comment; }

    public long getTripId() { return _tripId;}
    public void setTripId(long tripId) { _tripId = tripId;}

    public boolean isEmpty() {
        if (-1 == _id && null == _type && null == _date && null == _time && null == _type && null == _comment && -1 == _tripId)
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "[" + _type + "][" + _date + "] " + _amount;
    }
}
