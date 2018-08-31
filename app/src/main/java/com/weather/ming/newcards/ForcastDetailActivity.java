package com.weather.ming.newcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ForcastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_MODE, getIntent().getStringExtra(ItemDetailFragment.ARG_MODE));
            arguments.putDouble(ItemDetailFragment.ARG_HUMIDITY, getIntent().getDoubleExtra(ItemDetailFragment.ARG_HUMIDITY, 0));
            arguments.putString(ItemDetailFragment.ARG_ICON, getIntent().getStringExtra(ItemDetailFragment.ARG_ICON));
            arguments.putString(ItemDetailFragment.ARG_DESCRIPTION, getIntent().getStringExtra(ItemDetailFragment.ARG_DESCRIPTION));
            arguments.putString(ItemDetailFragment.ARG_DIRECTION, getIntent().getStringExtra(ItemDetailFragment.ARG_DIRECTION));
            arguments.putDouble(ItemDetailFragment.ARG_SPEED, getIntent().getDoubleExtra(ItemDetailFragment.ARG_SPEED, 0));
            arguments.putString(ItemDetailFragment.ARG_TIME, getIntent().getStringExtra(ItemDetailFragment.ARG_TIME));
            arguments.putDouble(ItemDetailFragment.ARG_MIN, getIntent().getDoubleExtra(ItemDetailFragment.ARG_MIN, 0));
            arguments.putDouble(ItemDetailFragment.ARG_MAX, getIntent().getDoubleExtra(ItemDetailFragment.ARG_MAX, 0));

            if (getIntent().getStringExtra(ItemDetailFragment.ARG_MODE).equals(Constants.Days))
            {
                arguments.putDouble(ItemDetailFragment.ARG_DAY, getIntent().getDoubleExtra(ItemDetailFragment.ARG_DAY, 0));
                arguments.putDouble(ItemDetailFragment.ARG_NIGHT, getIntent().getDoubleExtra(ItemDetailFragment.ARG_NIGHT, 0));
                Log.d("Day: ", Double.toString(getIntent().getDoubleExtra(ItemDetailFragment.ARG_DAY, 0)));
                Log.d("Night: ", Double.toString(getIntent().getDoubleExtra(ItemDetailFragment.ARG_NIGHT, 0)));
                Log.d("Humidity: ", Double.toString(getIntent().getDoubleExtra(ItemDetailFragment.ARG_HUMIDITY, 0)));
            }
            else
            {
                arguments.putDouble(ItemDetailFragment.ARG_TEMP, getIntent().getDoubleExtra(ItemDetailFragment.ARG_TEMP, 0));
            }
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            //navigateUpTo(new Intent(this, CardViewActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
