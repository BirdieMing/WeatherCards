package com.example.ming.newcards;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

//TODO enter place. Dynamically load images. Half day skip.
//TODO notification for rain or snow
//Textbox for city
//Language
//10 days forcast

public class CardListFragment extends Fragment {

    private ArrayList<DataObject> listObject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String LOG_TAG = "CardListFragment";
    String mode = Constants.Days;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listObject = getDataSet("Malden,us", "Day");
        super.onCreate(savedInstanceState);
    }

    @TargetApi(23)
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




        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void Refresh(String location, String mode)
    {
        this.mode = mode;
        listObject.clear();
        listObject.addAll(getDataSet(location, mode));
        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<DataObject> getDataSet(String location, String mode) {
        ArrayList results = new ArrayList<DataObject>();
        GetServiceData data = new GetServiceData();
        ArrayList<Forcast> fList = new ArrayList<Forcast>();

        try {
            fList = data.GetForcast(location, mode);
        } catch (Exception e) {
            Log.d(Constants.ErrorTag, e.getMessage());
        }

        for (int index = 0; index < fList.size(); index++) {
            Forcast f = fList.get(index);
            DataObject d = new DataObject(f.time, f.day, f.min, f.max, f.weatherMain, f.weatherDescription, f.weatherIcon);
            results.add(index, d);
        }
        return results;
    }

    @Override
    public void onResume()
    {
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
        super.onResume();
    }

    public void Clear()
    {
        listObject.clear();
        mAdapter.notifyDataSetChanged();
    }
}