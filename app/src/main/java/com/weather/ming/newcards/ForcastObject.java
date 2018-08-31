package com.weather.ming.newcards;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ForcastObject {

    private Date date;
    private String main;
    private String description;
    private String icon;
    private double day;
    private double night;
    private double temp;
    private double min;
    private double max;
    private double deg;
    private double speed;
    private String mode;
    private double humidity;

    public double GetHumidity()
    {
        return humidity;
    }

    public double GetSpeed()
    {
        return speed;
    }

    public String GetMode()
    {
        return mode;
    }

    public double GetTemp()
    {
        return temp;
    }

    public double GetMin()
    {
        return min;
    }

    public double GetMax()
    {
        return max;
    }

    public double GetDay()
    {
        return day;
    }

    public double GetNight()
    {
        return night;
    }

    public ForcastObject(Date date, double day, double night, double temp, double min, double max, double deg, double speed, double humidity,  String main, String description, String icon, String mode)
    {
        this.date = date;

        this.day = (int) (day * 9.0/5 + 32);
        this.night = (int) (night * 9.0/5 + 32);
        this.temp = (int) (temp * 9.0/5 + 32);
        this.min = (int) (min * 9.0/5 + 32);
        this.max = (int) (max * 9.0/5 + 32);
        this.description = description;
        this.icon = icon;
        this.main = main;
        this.mode = mode;
        this.speed = speed;
        this.deg = deg;
        this.humidity = humidity;
    }

    public String GetTime()
    {
        if (this.mode == Constants.Hours) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE M-dd h:mm a"); // the format of your date
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

    public String GetDescription()
    {
        String description = "";
        String DEGREE  = "\u00b0";
        if (this.mode.equals(Constants.Days))
            description = this.description + "\n" + "temp: " + getStringTemp(this.day) + DEGREE;
        else
            description = this.description + "\n" + "temp: " + getStringTemp(this.temp) + DEGREE;
        return description;
    }

    public String getStringTemp(double temp)
    {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String str = nf.format(temp);
        return str;
    }

    public String GetIcon()
    {
        return this.icon;
    }

    public String GetWindDirection()
    {
        double degMod = deg % 360;
        int windSec = ((int) (degMod / 22.5)) + 1;
        return GetWindDirectionBySec(windSec);
    }

    private String GetWindDirectionBySec(int windSec)
    {
        String direction = "";

        switch (windSec)
        {
            case 1:
                direction = "N";
                break;
            case 2:
                direction = "NNE";
                break;
            case 3:
                direction = "NE";
                break;
            case 4:
                direction = "ENE";
                break;
            case 5:
                direction = "E";
                break;
            case 6:
                direction = "ESE";
                break;
            case 7:
                direction = "SE";
                break;
            case 8:
                direction = "SSE";
                break;
            case 9:
                direction = "S";
                break;
            case 10:
                direction = "SSW";
                break;
            case 11:
                direction = "SW";
                break;
            case 12:
                direction = "WSW";
                break;
            case 13:
                direction = "W";
                break;
            case 14:
                direction = "WNW";
                break;
            case 15:
                direction = "NW";
                break;
            case 16:
                direction = "NNW";
                break;
            case 17:
                direction = "N";
                break;
            default:
                direction = "";
                break;


        }
        return direction;
    }

}