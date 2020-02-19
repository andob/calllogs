package com.wickerlabs.logmanager;

import android.content.Context;

import com.wickerlabs.logmanager.interfaces.CallLogObject;

import java.text.DecimalFormat;

import static android.provider.CallLog.Calls.MISSED_TYPE;

public class LogObject implements CallLogObject {
    private String number;
    private long date;
    private int duration, type;
    private String coolDuration;

    public LogObject() {
    }

    public LogObject(String number, long date, int duration, int type)
    {
        this.number=number;
        this.date=date;
        this.duration=duration;
        this.type=type;
    }

    public LogObject(LogObject another)
    {
        this.number=another.number;
        this.date=another.date;
        this.duration=another.duration;
        this.type=another.type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String getCoolDuration()
    {
        if (this.type==MISSED_TYPE)
            return "";
        if (this.duration<=0)
            return "";

        if (coolDuration==null)
            coolDuration=formatDuration(duration);
        return coolDuration;
    }

    private static String formatDuration(float value) {

        String duration = "";
        String result = "";

        if (value >= 0 && value < 3600) {

            result = String.valueOf(value / 60);
            String decimal = result.substring(0, result.lastIndexOf("."));
            String point = "0" + result.substring(result.lastIndexOf("."));

            int minutes = Integer.parseInt(decimal);
            float seconds = Float.parseFloat(point) * 60;

            DecimalFormat formatter = new DecimalFormat("#");
            duration = (minutes + " "+(minutes==1?"min":"mins")+" " + formatter.format(seconds) + " "+(seconds==1?"sec":"secs")).trim();

        } else if (value >= 3600) {

            result = String.valueOf(value / 3600);
            String decimal = result.substring(0, result.lastIndexOf("."));
            String point = "0" + result.substring(result.lastIndexOf("."));

            int hours = Integer.parseInt(decimal);
            float minutes = Float.parseFloat(point) * 60;

            DecimalFormat formatter = new DecimalFormat("#");
            duration = (hours + " "+(hours==1?"hr":"hrs")+" " + formatter.format(minutes) + " "+(minutes==1?"min":"mins")).trim();

        }

        return duration.replace("0 mins", "")
                .replace("0 secs", "")
                .replace("0 hrs", "")
                .trim();
    }
}
