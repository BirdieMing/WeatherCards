package com.weather.ming.newcards;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void setUp()
    {
        String setup = "test";
    }

    public void tearDown()
    {
        Log.d("test", "tear down");
    }

    @SmallTest
    public void testDay()
    {

        GetServiceData data = new GetServiceData();

        try {
            ArrayList<Forcast> result = data.GetForcast("Malden, us", Constants.Days);
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }

    @SmallTest
    public void testHour()
    {

        GetServiceData data = new GetServiceData();

        try {
            ArrayList<Forcast> result = data.GetForcast("Malden, us", Constants.Hours);
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }

    public void testJson()
    {
        CardViewActivity a = new CardViewActivity();
        a.LoadCities();
    }

}