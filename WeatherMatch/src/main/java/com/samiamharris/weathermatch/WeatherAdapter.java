package com.samiamharris.weathermatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by samharris on 11/25/13.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {

    Context mContext;
    int mLayoutResourceId;
    WeatherData[] mData;


    public WeatherAdapter (Context context, int layoutResourceId, WeatherData[] data) {
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }

    public void setData(WeatherData[] updatedData) {

        mData = updatedData;
        this.notifyDataSetChanged();
    }

    @Override
    public WeatherData getItem(int position) {
        return super.getItem(position);
    }

    public int getCount() {
        return mData.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        PlaceHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new PlaceHolder();

            holder.dayView = (TextView) row.findViewById(R.id.day);
            holder.dateView = (TextView) row.findViewById(R.id.date);
            holder.highTempView = (TextView) row.findViewById(R.id.hightemp);
            holder.lowTempView = (TextView) row.findViewById(R.id.lowtemp);
            holder.imageView = (ImageView) row.findViewById(R.id.emoji);


            row.setTag(holder);
        }else {
            holder = (PlaceHolder) row.getTag();
        }

        //get the data from the array
        WeatherData data = mData[position];

        holder.dayView.setText(data.simpleDay(data.getmDay()));
        holder.dateView.setText(data.simpleDate(data.getmDate()));
        holder.highTempView.setText((String.valueOf(data.getmHighTemp()))+ "°");
        holder.lowTempView.setText((String.valueOf(data.getmLowTemp()))+ "°");

        int resId = mContext.getResources().getIdentifier(data.getEmoji(data.getmIcon()), "drawable", mContext.getPackageName());
        holder.imageView.setImageResource(resId);

        return row;


    }

    static class PlaceHolder {

        TextView dayView;
        TextView dateView;
        TextView highTempView;
        TextView lowTempView;
        ImageView imageView;
    }
}

