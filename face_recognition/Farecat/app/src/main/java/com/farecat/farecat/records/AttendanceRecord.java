package com.farecat.farecat.records;

import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendanceRecord {

    public static final String DATEFORMAT = "yyyy-MM-dd";

    private int _id;
    private String _studentName;
    private String _date;

    public AttendanceRecord(String _studentName) {
        this._studentName = _studentName;
        this._date = dateString(null);
    }

    public static String dateString(Date date) {
        DateFormat df = new SimpleDateFormat(DATEFORMAT);
        if(date == null) {
            date = Calendar.getInstance().getTime();
        }
        return df.format(date);
    }

    public String get_date() {
        return _date;
    }

    public String get_studentName() {
        return _studentName;
    }
}
