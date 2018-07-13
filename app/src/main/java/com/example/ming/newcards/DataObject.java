package com.example.ming.newcards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataObject {
    private String mText1;
    private String mText2;
    private Date date;
    private String weatherDescription;

    private double kelvin;
    private double celcius;
    private double fahrenheit;

    DataObject(Date date, double kelvin, String weatherDescription)
    {
        this.date = date;
        this.kelvin = kelvin;
        this.weatherDescription = weatherDescription;

        this.fahrenheit = (this.kelvin - 273.15) * 9.0/5 + 32;
        this.celcius = (this.kelvin - 273.15);
    }

    public String getTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE h:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getTemp(int tempFormat) {
        String temp = "";

        if (tempFormat == 1)
            temp = "K: " + Double.toString(kelvin);
        else if (tempFormat == 2)
            temp = "C: " + Double.toString(celcius);
        else if (tempFormat == 3)
            temp = "F: " + Double.toString(fahrenheit);

        return temp;
    }

    public String getWeatherDescription()
    {
        return this.weatherDescription;
    }
}