package com.bnr.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "TimePickerFragment";
    public static final String EXTRA_TIME = "com.bnr.android.criminalintent.time";
    private Date mDate;

    public static TimePickerFragment newInstance( Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);

        // Create a Calender to get the year, month and day.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( mDate);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get( Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, min,
                DateFormat.is24HourFormat(getActivity()));

    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "TimePicker: Hour = " + hourOfDay + ",  Minute = " + minute);

        if (getTargetFragment() == null) {
            return;
        }

        Calendar local = new GregorianCalendar();
        local.setTime(this.mDate);
        local.set( Calendar.HOUR, hourOfDay);
        local.set( Calendar.MINUTE, minute);
        mDate = local.getTime();

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);
        Log.d(TAG, "sendResult: " + mDate.toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }

}