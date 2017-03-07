package com.farecat.farecat.records;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "farecat.db";
    public static final String TABLE_ATTENDANCE_RECORDS = "attendance_records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STUDENTNAME = "studentName";
    public static final String COLUMN_DATE = "attendanceDate";

    public static final String TABLE_ENROLLED_STUDENTS = "enrolled_students";

    private static final String TAG = "dbTag";

    public DBManager(Context ctx, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(ctx, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String attRecQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ATTENDANCE_RECORDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_STUDENTNAME + " TEXT, " +
                COLUMN_DATE + " TEXT" +
                ");";
        db.execSQL(attRecQuery);
        String esQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ENROLLED_STUDENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_STUDENTNAME + " TEXT" +
                ");";
        db.execSQL(esQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLED_STUDENTS);
        onCreate(db);
    }

    public void addAttRecord(AttendanceRecord ar) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENTNAME, ar.get_studentName());
        cv.put(COLUMN_DATE, ar.get_date());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ATTENDANCE_RECORDS, null, cv);
        db.close();
    }

    public void addStudent(AttendanceRecord ar) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENTNAME, ar.get_studentName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ENROLLED_STUDENTS, null, cv);
        db.close();
    }

    /*public void getStudents() {
        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_STUDENTNAME +
                " FROM " + TABLE_ENROLLED_STUDENTS +
                ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            String studentName = c.getString(c.getColumnIndex(COLUMN_STUDENTNAME));
            if(studentName != null) {
                dbString += studentName + "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        Log.i(TAG, dbString);
    }*/

    public String getAttRecords(String date) {
        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        String getQuery = "SELECT DISTINCT " + COLUMN_STUDENTNAME +
                " FROM " + TABLE_ATTENDANCE_RECORDS +
                " WHERE " + COLUMN_DATE + " = '" + date + "';";
        Cursor c = db.rawQuery(getQuery, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            String studentName = c.getString(c.getColumnIndex(COLUMN_STUDENTNAME));
            if(studentName != null) {
                dbString += studentName + "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }

    public boolean isEnrolled(String subject) {
        boolean isEnrolled = false;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ENROLLED_STUDENTS +
                " WHERE " + COLUMN_STUDENTNAME + " = '" + subject + "';";
        Cursor c = db.rawQuery(query, null);
        Log.i(TAG, "count : " + c.getCount());
        if(c.getCount() > 0) {
            isEnrolled = true;
        }
        c.close();
        db.close();
        return isEnrolled;
    }
}
