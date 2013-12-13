package com.samiamharris.weathermatch;

/**
 * Created by samharris on 12/13/13.
 */
public class NetworkImageRequest {

    WeatherData mWeatherData;

    public NetworkImageRequest(WeatherData wD) {

        mWeatherData = wD;
        //later we will call a method with the weather data instance to return the image to the weather data
    }

    public void getNetworkImage() {

        //get image from the internet

        // mWeatherData.onReceiveNetworkImage(in here pass in the image);
    }

}
