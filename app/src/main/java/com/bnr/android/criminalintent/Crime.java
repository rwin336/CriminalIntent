package com.bnr.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOVLED = "sovled";
    private static final String JSON_DATE = "date";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if( json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved = json.getBoolean(JSON_SOVLED);
        mDate = new Date(json.getLong(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOVLED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date d) {
        Calendar local = new GregorianCalendar();
        Calendar update = new GregorianCalendar();
        local.setTime(this.mDate);
        update.setTime(d);
        local.set(Calendar.MONTH, update.get(Calendar.MONTH));
        local.set( Calendar.DAY_OF_MONTH, update.get(Calendar.DAY_OF_MONTH));
        local.set( Calendar.YEAR, update.get(Calendar.YEAR));
        mDate = local.getTime();
    }

    public void setTime(Date d) {
        Calendar local = new GregorianCalendar();
        Calendar update = new GregorianCalendar();
        local.setTime(this.mDate);
        update.setTime(d);
        local.set( Calendar.HOUR, update.get(Calendar.HOUR));
        local.set( Calendar.MINUTE, update.get(Calendar.MINUTE));
        mDate = local.getTime();
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }



}