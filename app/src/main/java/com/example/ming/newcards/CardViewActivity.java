package com.example.ming.newcards;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class CardViewActivity extends AppCompatActivity {

    private static String LOG_TAG = "CardViewActivity";
    private static String List_TAG = "ListTag";
    private static String Entry_TAG = "EntryTag";
    private static String CardFragmentStackTag = "CardFragmentStackTag";
    CardEntryFragment fragment = new CardEntryFragment();
    CardListFragment listFragment = new CardListFragment();

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                ReplaceCardFragment();
            }
        });
    }

    private void ReplaceCardFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragment = new CardEntryFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment, Entry_TAG);
        fragmentTransaction.addToBackStack(CardFragmentStackTag);
        fragmentTransaction.commit();
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
        getMenuInflater().inflate(R.menu.menu_card_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            CardListFragment fragment = (CardListFragment) getFragmentManager().findFragmentByTag(List_TAG);
            fragment.Clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddNewCard(String title, String moreText)
    {
        //CardListFragment fragment = (CardListFragment) getFragmentManager().findFragmentByTag(List_TAG);
        listFragment.AddString(title, moreText);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, listFragment);
        fragmentTransaction.commit();
        //Reload fragment, state diagram
        //this.getFragmentManager().popBackStack(CardFragmentStackTag, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
