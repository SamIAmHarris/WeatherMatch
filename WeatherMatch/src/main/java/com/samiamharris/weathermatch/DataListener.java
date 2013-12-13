package com.samiamharris.weathermatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by samharris on 12/10/13.
 */
public class DataListener extends BroadcastReceiver {

    WeatherActivity mWeatherActivity;

    public DataListener(WeatherActivity wA) {
        super();
        mWeatherActivity = wA;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        String data = extra.getString("MyData");
        mWeatherActivity.onDataReceive(data);
        Log.e("tres amigos", "Receive Broadcast");
    }
}

