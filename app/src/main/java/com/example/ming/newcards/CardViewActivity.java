package com.example.ming.newcards;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                findViewById(R.id.location_textbox);
//
//        textView.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    Refresh();
//                }
//                return false;
//            }
//        });

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
        location_txt.clearFocus();
        //Clear focus
    }

    private void AddListFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, listFragment, List_TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private static final String[] COUNTRIES = new String[] {
            "Malden,us"
    };

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String imageFileKey;
    private String imageFilePath;
    //Dispatch intent from child class?
}
