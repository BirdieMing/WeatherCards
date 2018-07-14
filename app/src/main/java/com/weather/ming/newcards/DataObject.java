package com.weather.ming.newcards;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private String mode;

    DataObject(Date date, double day, double min, double max, String main, String description, String icon, String mode)
    {
        this.date = date;

        this.day = (int) (day * 9.0/5 + 32);
        this.min = (int) (min * 9.0/5 + 32);
        this.max = (int) (max * 9.0/5 + 32);
        this.description = description;
        this.icon = icon;
        this.main = main;
        this.mode = mode;
        //this.fahrenheit = (this.kelvin - 273.15) * 9.0/5 + 32;
        //this.celcius = (this.kelvin - 273.15);
    }

    public String getTime()
    {
        if (this.mode == Constants.Hours) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE h:mm a"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            return formattedDate;
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE M-dd"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            return formattedDate;
        }
    }

    public String getDescription()
    {
        return this.description + "\n" + "Day: " + getDayTemp() + " Max: " + getMaxTemp() + " Min: " + getMinTemp();
    }

    public String getDayTemp()
    {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String str = nf.format(this.day);
        return str;
    }

    public String getMaxTemp()
    {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String str = nf.format(this.max);
        return str;
    }

    public String getMinTemp()
    {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String str = nf.format(this.min);
        return str;
    }

    public String getIcon()
    {
        return this.icon;
    }

}