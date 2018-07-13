package com.example.ming.newcards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataObject {
    private String mText1;
    private String mText2;
    private Date date;

    private String main;
    private String description;
    private String icon;

    private double day;
    private double min;
    private double max;

    DataObject(Date date, double day, double min, double max, String main, String description, String icon)
    {
        this.date = date;
        this.day = (day - 273.15) * 9.0/5 + 32;
        this.min = (min - 273.15) * 9.0/5 + 32;
        this.max = (max - 273.15) * 9.0/5 + 32;
        this.description = description;

        //this.fahrenheit = (this.kelvin - 273.15) * 9.0/5 + 32;
        //this.celcius = (this.kelvin - 273.15);
    }

    public String getTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE h:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getDay()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MM-dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }



    public String getWeatherDescription()
    {
        return this.description;
    }

    public String getDayTemp()
    {
        return Double.toString(this.day);
    }

    public String getMaxTemp()
    {
        return Double.toString(this.max);
    }

    public String getMinTemp()
    {
        return Double.toString(this.min);
    }
}