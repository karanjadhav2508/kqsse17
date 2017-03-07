package com.farecat.farecat;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.farecat.farecat.helpers.AlertDialogHelper;
import com.farecat.farecat.records.AttendanceRecord;
import com.farecat.farecat.records.DBManager;

import java.util.Calendar;

public class ViewAttendanceActivity extends AppCompatActivity {

    private DBManager dbManager;
    private DatePicker dp;
    private AlertDialog alertDialog;

    private static final String NO_RECORDS = "No attendance records present.";
    static final String TAG = "ViewAttendanceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        dbManager = new DBManager(this, null, null, 1);
        dp = (DatePicker) findViewById(R.id.attendanceDatePicker);
        alertDialog = AlertDialogHelper.buildAlertDialog(ViewAttendanceActivity.this, "Attendance Records", "OK");
    }

    public void viewAttendance(View v) {
        String attDate = datePickerToDate();
        Log.i(TAG, attDate);
        String attrecords = dbManager.getAttRecords(attDate);
        if(attrecords.matches("")) {
            alertDialog.setMessage(NO_RECORDS);
        } else {
            alertDialog.setMessage(attrecords);
        }
        alertDialog.show();
    }

    private String datePickerToDate() {
        Calendar c = Calendar.getInstance();
        c.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
        return AttendanceRecord.dateString(c.getTime());
    }

}