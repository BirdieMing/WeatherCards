package com.weather.ming.newcards;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CardListFragment extends Fragment {

    private ArrayList<ForcastObject> listObject;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String LOG_TAG = "CardListFragment";
    String mode = Constants.Days;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listObject = new ArrayList<ForcastObject>();//= getDataSet("Malden,us", "Day");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.card_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(listObject, getActivity(), getContext(), mode);
        mRecyclerView.setAdapter(mAdapter);

        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

                Context context = v.getContext();
                Intent intent = new Intent(context, ForcastDetailActivity.class);
                ForcastObject f = listObject.get(position);
                intent.putExtra(ItemDetailFragment.ARG_MODE, f.GetMode());

                if (f.GetMode().equals(Constants.Days)) {
                    intent.putExtra(ItemDetailFragment.ARG_DAY, f.GetDay());
                    intent.putExtra(ItemDetailFragment.ARG_NIGHT, f.GetNight());
                } else {
                    intent.putExtra(ItemDetailFragment.ARG_TEMP, f.GetTemp());
                }

                intent.putExtra(ItemDetailFragment.ARG_ICON, f.GetIcon());
                intent.putExtra(ItemDetailFragment.ARG_TIME, f.GetTime());
                intent.putExtra(ItemDetailFragment.ARG_DESCRIPTION, f.GetDescription());
                intent.putExtra(ItemDetailFragment.ARG_MIN, f.GetMin());
                intent.putExtra(ItemDetailFragment.ARG_MAX, f.GetMax());
                intent.putExtra(ItemDetailFragment.ARG_SPEED, f.GetSpeed());
                intent.putExtra(ItemDetailFragment.ARG_DIRECTION, f.GetWindDirection());
                intent.putExtra(ItemDetailFragment.ARG_HUMIDITY, f.GetHumidity());
                context.startActivity(intent);
            }
        });

        return rootView;
    }

    public void Refresh(String location, String mode)
    {
        this.mode = mode;
        listObject.clear();
        mAdapter.SetMode(mode);
        ArrayList<ForcastObject> newList = getDataSet(location, mode);
        listObject.addAll(newList);

        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<ForcastObject> getDataSet(String location, String mode) {
        ArrayList results = new ArrayList<ForcastObject>();
        GetServiceData data = new GetServiceData();
        ArrayList<Forcast> fList = new ArrayList<Forcast>();

        try {
            fList = data.GetForcast(location, mode);
        } catch (Exception e) {
            Log.d(Constants.ErrorTag, e.getMessage());
        }

        for (int index = 0; index < fList.size(); index++) {
            Forcast f = fList.get(index);
            ForcastObject d = new ForcastObject(f.time, f.day, f.night, f.temp, f.min, f.max, f.windDirection, f.windSpeed, f.humidity, f.weatherMain, f.weatherDescription, f.weatherIcon, mode);
            //Log.d("Mode", d.GetMode());
            results.add(index, d);
        }
        return results;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    public void Clear()
    {
        listObject.clear();
        mAdapter.notifyDataSetChanged();
    }
}