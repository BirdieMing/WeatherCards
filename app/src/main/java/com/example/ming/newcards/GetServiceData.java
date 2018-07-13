package com.example.ming.newcards;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ming on 2/21/2016.
 */
public class GetServiceData {

    private String weather_api_key = "b9ab3488c011ac84e109bf20d600b6d7";

    public GetServiceData()
    {

    }

    public ArrayList<Forcast> GetForcast(String location, String mode)  {
        if (mode == Constants.Hours)
            return GetHourlyForcast(location);
        else if (mode == Constants.Days)
            return GetDayForcast(location);
        else
            return new ArrayList<Forcast>();
    }

    private ArrayList<Forcast> GetDayForcast(String location)
    {
        ArrayList<Forcast> fList = new ArrayList<>();

        try {
            String weather = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + location + "&mode=json&units=metric&cnt=7&appid=" + weather_api_key;
            JSONObject forcast = getJSON(weather);
            JSONArray forcastArray = null;

            forcastArray = forcast.getJSONArray("list");

            for (int i = 0; i < forcastArray.length(); i++) {
                JSONObject dayForcast = forcastArray.getJSONObject(i);
                int dt = dayForcast.getInt("dt");
                Forcast f = new Forcast();
                Date date = new Date(dt * 1000L); // *1000 is to convert seconds to milliseconds
                f.time = date;

                String weatherMain = dayForcast.getJSONArray("weather").getJSONObject(0).getString("main");
                String weatherDescription = dayForcast.getJSONArray("weather").getJSONObject(0).getString("description");
                String weatherIcon = dayForcast.getJSONArray("weather").getJSONObject(0).getString("icon");
                Double day = dayForcast.getJSONObject("temp").getDouble("day");
                Double min = dayForcast.getJSONObject("temp").getDouble("min");
                Double max = dayForcast.getJSONObject("temp").getDouble("max");
                f.weatherDescription = weatherDescription;
                f.day = day;
                f.min = min;
                f.max = max;
                f.weatherMain = weatherMain;
                f.weatherDescription = weatherDescription;
                f.weatherIcon = weatherIcon;
                fList.add(f);
            }
        }
        catch (Exception e)
        {
            Log.d(Constants.ErrorTag, e.getMessage());
            //e.printStackTrace();
        }
        return fList;
    }

    private ArrayList<Forcast> GetHourlyForcast(String location)
    {
        ArrayList<Forcast> fList = new ArrayList<Forcast>();
        try {
            String weather = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&mode=json&appid=" + weather_api_key;
            JSONObject forcast = getJSON(weather);
            JSONArray forcastArray = null;

            forcastArray = forcast.getJSONArray("list");

            for (int i = 0; i < forcastArray.length(); i++) {
                JSONObject dayForcast = forcastArray.getJSONObject(i);
                int dt = dayForcast.getInt("dt");
                Forcast f = new Forcast();
                Date date = new Date(dt * 1000L); // *1000 is to convert seconds to milliseconds
                f.time = date;

                String weatherDescription = forcastArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                Double temp = dayForcast.getJSONObject("main").getDouble("temp");
                f.weatherDescription = weatherDescription;
                f.day = temp;
                fList.add(f);
            }
        }
        catch (Exception ex) {
            Log.d("Exception caught", ex.getMessage());
        }

        return fList;
    }

    public JSONObject getJSON(String url) {
        JSONObject jsonObject = null;
        try {
            String data = getJSON(url, 10000);
            jsonObject = new JSONObject(data);
        } catch (Exception e) {
            Log.d(Constants.ErrorTag, e.getMessage());
        }
        return jsonObject;
    }

    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            Log.d(new Exception().getStackTrace()[0].getMethodName(), ex.getMessage());
        } catch (IOException ex) {
            Log.d(new Exception().getStackTrace()[0].getMethodName(), ex.getMessage());
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Log.d(new Exception().getStackTrace()[0].getMethodName(), ex.getMessage());
                    //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

}
