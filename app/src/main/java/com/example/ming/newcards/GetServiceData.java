package com.example.ming.newcards;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ming on 2/21/2016.
 */
public class GetServiceData {

    private String weather_api_key = "b9ab3488c011ac84e109bf20d600b6d7";
    private String mbta = "http://realtime.mbta.com/developer/api/v2/stopsbylocation?api_key=wX9NwuHnZU2ToO7GmGR9uw&lat=42.346961&lon=-71.076640&format=json";

    public GetServiceData()
    {

    }

    public int DebugMethod()
    {
        int debug = 34;
        return debug;
    }

    public ArrayList<Forcast> GetForcast() throws  JSONException {

        ArrayList<Forcast> fList = new ArrayList<Forcast>();
        String weather = "http://api.openweathermap.org/data/2.5/forecast?q=Malden,us&mode=json&appid=44db6a862fba0b067b1930da0d769e98";
        JSONObject forcast = getJSON(weather);
        JSONArray forcastArray = forcast.getJSONArray("list");
        for (int i = 0; i < forcastArray.length(); i++)
        {
            JSONObject dayForcast = forcastArray.getJSONObject(i);
            int dt = dayForcast.getInt("dt");
            Forcast f = new Forcast();

            Date date = new Date(dt*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("UTC-5")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            System.out.println(formattedDate);

            f.time = date;

            String weatherDescription = forcastArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
            Double temp = dayForcast.getJSONObject("main").getDouble("temp");
            f.clouds = weatherDescription;
            f.temperature = temp;
            fList.add(f);
        }

        return fList;
    }

    public JSONObject getJSON(String url) {
        JSONObject jsonObject = null;
        try {
            String data = getJSON(url, 10000);
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
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
