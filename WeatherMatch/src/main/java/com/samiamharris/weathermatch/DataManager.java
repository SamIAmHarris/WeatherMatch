package com.samiamharris.weathermatch;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by samharris on 12/10/13.
 */
public class DataManager {

    ForecastRequest mForecastRequest;

    WeatherUpdateService mWeatherUpdateService;

    public DataManager(WeatherUpdateService weatherUpdateService) {

        mWeatherUpdateService = weatherUpdateService;

    }

    public void PerformRequest() {
        mForecastRequest = new ForecastRequest(this);

    }

    public void OnReceiveForecast() {


    }
    //Manage Date - Location Manager nested class
    //Request Management
    //Parse JSON Data
    //notification Sending
    //Receive Data


}
