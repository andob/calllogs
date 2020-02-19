package com.wickerlabs.logmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.List;

public class LogsManager
{
    public List<LogObject> getLogs(Context context, long fromDate, long toDate, int offset, int limit)
    {
        List<LogObject> logs=new ArrayList<>();

        String selection=CallLog.Calls.DATE+" >= "+fromDate;
        selection+=" and ";
        selection+=CallLog.Calls.DATE+" <= "+toDate;

        String pagination=" limit "+limit+" offset "+offset;
        String ordering=CallLog.Calls.DATE+" desc";

        @SuppressLint("MissingPermission")
        Cursor cursor=context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, selection, null, ordering+pagination);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        while (cursor.moveToNext()) {
            LogObject log = new LogObject();

            log.setNumber(cursor.getString(number));
            log.setType(cursor.getInt(type));
            log.setDuration(cursor.getInt(duration));
            log.setDate(cursor.getLong(date));

            logs.add(log);
        }

        cursor.close();


        return logs;
    }

}
