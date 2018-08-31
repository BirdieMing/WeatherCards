package com.weather.ming.newcards;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_MODE = "mode";
    public static final String ARG_HUMIDITY = "humidity";
    public static final String ARG_DAY = "day";
    public static final String ARG_NIGHT = "night";
    public static final String ARG_TEMP = "temp";
    public static final String ARG_MIN = "min";
    public static final String ARG_MAX = "max";
    public static final String ARG_SPEED = "speed";
    public static final String ARG_DIRECTION = "direction";
    public static final String ARG_DESCRIPTION = "description";
    public static final String ARG_TIME = "time";
    public static final String ARG_ICON = "icon";
    String mode;
    double humidity;
    double day;
    double night;
    double temp;
    double min;
    double max;
    double speed;
    String direction;
    String description;
    String time;
    String icon;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (getArguments() != null) {

            mode = getArguments().getString(ARG_MODE);
            humidity = getArguments().getDouble(ARG_HUMIDITY);
            min = getArguments().getDouble(ARG_MIN);
            max = getArguments().getDouble(ARG_MAX);
            speed = getArguments().getDouble(ARG_SPEED);
            direction = getArguments().getString(ARG_DIRECTION);
            description = getArguments().getString(ARG_DESCRIPTION);
            icon = getArguments().getString(ARG_ICON);
            time = getArguments().getString(ARG_TIME);

            if (mode.equals(Constants.Days))
            {
                day = getArguments().getDouble(ARG_DAY);
                night = getArguments().getDouble(ARG_NIGHT);
            }
            else
            {
                temp = getArguments().getDouble(ARG_TEMP);
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(time);
                appBarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);


        TextView view = ((TextView) rootView.findViewById(R.id.item_detail));
        String DEGREE  = "\u00b0";
        StringBuilder b = new StringBuilder();

        b.append("Description: " + description + System.lineSeparator());

        if (mode.equals(Constants.Days)) {
            b.append("Day: " + Double.toString(day) + DEGREE + System.lineSeparator());
            b.append("Night: " + Double.toString(night) + DEGREE + System.lineSeparator());
        }

        b.append("Min: " + Double.toString(min) + DEGREE + System.lineSeparator());
        b.append("Max: " + Double.toString(max) + DEGREE + System.lineSeparator());
        b.append("Wind Direction: " + direction + System.lineSeparator());
        b.append("Wind Speed (meter/sec): " + Double.toString(speed) + System.lineSeparator());
        b.append("Humidity: " + Double.toString(humidity) + "%" + System.lineSeparator());
        view.setText(b.toString());
        return rootView;
    }
}
