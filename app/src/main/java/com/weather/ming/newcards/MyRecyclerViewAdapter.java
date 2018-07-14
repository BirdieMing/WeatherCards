package com.weather.ming.newcards;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    private Activity act;
    private Context con;
    private String mode;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView title;
        TextView detail;
        ImageView picture;

        public DataObjectHolder(View itemView) {
            super(itemView);
            //Pick one
            title = (TextView) itemView.findViewById(R.id.date);
            detail = (TextView) itemView.findViewById(R.id.description);
            picture = (ImageView) itemView.findViewById(R.id.picture);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset, Activity act, Context context, String mode) {
        mDataset = myDataset;
        this.act = act;
        this.con = context;
        this.mode = mode;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        String time = "";
        time = mDataset.get(position).getTime();
        String description = mDataset.get(position).getDescription();

        holder.title.setText(time);
        holder.detail.setText(description);

        String icon = mDataset.get(position).getIcon();
        int iconNum = -1;
        switch (icon)
        {
            case "01d":
                iconNum = R.drawable.ic_day;
                break;
            case "02d":
            case "03d":
            case "04d":
            case "50d": //No mist icon, so cloud would do
                iconNum = R.drawable.ic_cloudy_day_1;
                break;
            case "09d":
            case "10d":
                iconNum = R.drawable.ic_rainy_1;
                break;
            case "11d":
                iconNum = R.drawable.ic_thunder;
                break;
            case "13d":
                iconNum = R.drawable.ic_snowy_1;
                break;
            default:
                iconNum = R.drawable.ic_day;
                break;
        }
        //Push this into data object
        Drawable d = ResourcesCompat.getDrawable(con.getResources(), iconNum, null);
        //holder.picture.setImageBitmap(BitmapFactory.decodeResource(con.getResources(), R.drawable.ic_cloudy_day_1));
        holder.picture.setImageDrawable(d);
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
