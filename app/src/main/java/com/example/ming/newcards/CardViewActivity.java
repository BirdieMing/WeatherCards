package com.example.ming.newcards;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CardViewActivity extends AppCompatActivity {

    //TODO: Add place search text box
    //Debug: Load picture button
    //Manage picture screen?

    private static String LOG_TAG = "CardViewActivity";
    private static String List_TAG = "ListTag";
    private static String Entry_TAG = "EntryTag";
    private static String CardFragmentStackTag = "CardFragmentStackTag";
    CardEntryFragment fragment = new CardEntryFragment();
    CardListFragment listFragment = new CardListFragment();
    private int interval = 7;
    private String mode = Constants.Days;
    private AdView mAdView;
    private String locationKey = "com.example.ming.newcards.location";

    @Override
    public void onResume()
    {
        super.onResume();
        AutoCompleteTextView location_txt = (AutoCompleteTextView) findViewById(R.id.location_textbox);
        String location = location_txt.getText().toString();
        if (location != "")
            Refresh();
    }

    @Override
    public void onStop() {
        super.onStop();

        AutoCompleteTextView location_txt = (AutoCompleteTextView) findViewById(R.id.location_textbox);
        String location = location_txt.getText().toString();

        if (location =="")
            return;

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.ming.newcards", Context.MODE_PRIVATE);

        prefs.edit().putString(locationKey, location).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.ming.newcards", Context.MODE_PRIVATE);

        String location = prefs.getString(locationKey, "");

        setContentView(R.layout.activity_card_view);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            CardListFragment listFragment = new CardListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            listFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            //getSupportFragmentManager().beginTransaction()
            //      .add(R.id.fragment_container, listFragment).commit();
            AddListFragment();

        }
        ArrayList<String> cities = LoadCities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cities);

        AutoCompleteTextView location_txt = (AutoCompleteTextView) findViewById(R.id.location_textbox);
        location_txt.setOnEditorActionListener(new TextView.OnEditorActionListener()
                                               {
                                                   @Override
                                                   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                       String location = v.getText().toString();
                                                       Refresh();
                                                       return true;
                                                   }
                                               }
        );

        location_txt.setAdapter(adapter);
        location_txt.setText(location);
        location_txt.clearFocus();
        //Clear focus

        if (location != "")
            Refresh();

        MobileAds.initialize(this,
                "ca-app-pub-3664977011511772~8336923700");

        //mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
    }

    public void GoClick(View view) {
        Refresh();
    }

    private void AddListFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, listFragment, List_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 1, 0, "Refresh");
        SubMenu sMenu0 = menu.addSubMenu(0, 2, 0, "Mode");
        sMenu0.add(0, 3, 0, "Day");
        sMenu0.add(0, 4, 0, "Hourly");

        //SubMenu sMenu1 = menu.addSubMenu(0, 8, 0, "Customize picture for weather");
        ///sMenu1.add(0, 9, 0, "clear sky");

        getMenuInflater().inflate(R.menu.menu_card_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId())
        {
            case 1:
            case 3:
                this.mode = Constants.Days;
                Refresh();
                return true;
            case 4:
                this.mode = Constants.Hours;
                Refresh();
                return true;
            default:
                Log.d("Menu Selection", "missing case " + item.getItemId());

        }

        return super.onOptionsItemSelected(item);
    }

    public void Refresh() {

        try {
            AutoCompleteTextView textView = (AutoCompleteTextView)
                    findViewById(R.id.location_textbox);

            String location = (String) textView.getText().toString();

            if (location == "")
                return;

            listFragment.Refresh(location, mode);
        }
        catch (Exception e)
        {
            Log.d(Constants.ErrorTag, e.getMessage());
        }
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            Log.d(Constants.ErrorTag, ex.getMessage());
            return null;
        }
        return json;
    }

    public ArrayList<String> LoadCities()
    {
        ArrayList<String> cities = new ArrayList<String>();
        try {
            JSONObject jObject = new JSONObject(loadJSONFromAsset("US_States_and_Cities.json"));

            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( jObject.get(key) instanceof JSONArray ) {
                    JSONArray jArray = (JSONArray) jObject.get(key);

                    for(int i = 0; i < jArray.length(); i++) {
                        cities.add((String) jArray.get(i));
                    }

                }
            }
        } catch (Exception e) {
            Log.d(Constants.ErrorTag, e.getMessage());
        }

        return cities;
    }
}
