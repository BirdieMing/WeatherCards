package com.example.ming.newcards;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO enter place. Dynamically load images. Half day skip.
/**
 * Created by Ming on 2/15/2016.
 */
public class CardListFragment extends Fragment {

    private ArrayList<DataObject> listObject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String LOG_TAG = "CardListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listObject = getDataSet();
        Log.d("list created", "list created 2");
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
        mAdapter = new MyRecyclerViewAdapter(listObject);
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void AddString(String text1, String text2)
    {
        DataObject o = new DataObject(text1, text2);
        listObject.add(o);
        Log.d("Tag", Integer.toString(listObject.size()));
        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        GetServiceData data = new GetServiceData();
        ArrayList<Forcast> fList = new ArrayList<Forcast>();

        try {
            fList = data.GetForcast();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int index = 0; index < fList.size(); index++) {
            Forcast f = fList.get(index);
            Date d = f.time;
            String description = f.clouds;
            Double temp = f.temperature;

            DataObject obj = new DataObject("Time " + d.toString(),
                    "Temperature: " + temp.toString() + " " + description);

            results.add(index, obj);
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