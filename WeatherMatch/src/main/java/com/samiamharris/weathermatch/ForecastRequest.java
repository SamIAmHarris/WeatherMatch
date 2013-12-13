package com.samiamharris.weathermatch;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samharris on 12/10/13.
 */
public class ForecastRequest {

    DataManager mDataManager;

    public ForecastRequest(DataManager dataManager) {

        mDataManager = dataManager;

    }

    public void getForecastRequest() {


    }

    //mDataManager.onReceiveForecast();

}
