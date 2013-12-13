package com.samiamharris.weathermatch;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samharris on 11/25/13.
 */
public class WeatherData {

    int mLowTemp;
    int mHighTemp;
    int mDate;
    int mDay;
    String mIcon;

    NetworkImageRequest mNetworkImageRequest;

    public WeatherData(int mLowTemp, int mHighTemp, int mDate, int mDay, String mIcon) {
        this.mLowTemp = mLowTemp;
        this.mHighTemp = mHighTemp;
        this.mDate = mDate;
        this.mDay = mDay;
        this.mIcon = mIcon;

        mNetworkImageRequest = new NetworkImageRequest(this);

    }

    public void onReceiveNetworkImage () {



    }



    public int getmLowTemp() {
        return mLowTemp;
    }

    public void setmLowTemp(int mLowTemp) {
        this.mLowTemp = mLowTemp;
    }

    public int getmHighTemp() {
        return mHighTemp;
    }

    public void setmHighTemp(int mHighTemp) {
        this.mHighTemp = mHighTemp;
    }

    public int getmDate() {
        return mDate;
    }

    public void setmDate(int mDate) {
        this.mDate = mDate;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public String getmIcon() { return mIcon; }

    public void setmIcon(String mIcon) { this.mIcon = mIcon; }

    public String simpleDate(int date) {
        String formattedDate;
        formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date(getmDate() * 1000L));

        return formattedDate;
    }

    public String simpleDay(int date) {
        String formattedDay;
        formattedDay = new SimpleDateFormat("EEEE").format(getmDay() * 1000L);

        return formattedDay;

    }
    public String getEmoji(String icon) {
        if ("partly-cloudy-day".equals(icon)) {
            return "six";
        } else if ("cloudy".equals(icon)) {
            return "six";
        } else if("partly-cloudy-night".equals(icon)) {
            return "six";
        }else if("clear-day".equals(icon)) {
            return "four";
        } else if("rain".equals(icon)) {
            return "seven";
        }
        else {
            return "six";
        }
    }



}
