package com.example.trips.models;

import java.io.Serializable;

public class Trip implements Serializable {
    private long _id;
    private String _name;
    private String _destination;
    private String _dateOfTrip;
    private String _description;
    private String _type;
    private String _peopleNumber;
    private int _riskAssessment;

    public Trip() {
        _id = -1;
        _name = null;
        _destination = null;
        _dateOfTrip = null;
        _description = null;
        _type = null;
        _peopleNumber = null;
        _riskAssessment = -1;
    }

    public Trip(long id, String name, String destination, String dateOfTrip, String description, int riskAssessment, String type, String peopleNumber) {
        _id = id;
        _name = name;
        _destination = destination;
        _dateOfTrip = dateOfTrip;
        _description = description;
        _riskAssessment = riskAssessment;
        _type = type;
        _peopleNumber = peopleNumber;
    }

    public long getId() { return _id; }
    public void setId(long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public String getDateOfTrip() {
        return _dateOfTrip;
    }
    public void setDateOfTrip(String dateOfTrip) {
        _dateOfTrip = dateOfTrip;
    }

    public String getDestination() {
        return _destination;
    }
    public void setDestination(String destination) {
        _destination = destination;
    }

    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }

    public int getRiskAssessment() { return _riskAssessment;}
    public void setRiskAssessment(int riskAssessment) {_riskAssessment = riskAssessment;}

    public String getType() {return _type; }
    public void setType(String type) {_type = type;}

    public String getPeopleNumber() {return _peopleNumber;}
    public void setPeopleNumber(String peopleNumber) { _peopleNumber = peopleNumber;}

    public boolean isEmpty() {
        if (-1 == _id && null == _name && null == _dateOfTrip && null == _destination && null == _description)
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "[" + _dateOfTrip + "] " + _name;
    }
}
