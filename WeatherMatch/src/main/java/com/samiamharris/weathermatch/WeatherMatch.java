package com.samiamharris.weathermatch;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samharris on 11/25/13.
 */

public class WeatherMatch extends Fragment implements LocationListener{

    public String initialURL = "https://api.forecast.io/forecast/1c427d26fbc53060aef944ad30201adf/";

    ListView mListView;
    WeatherAdapter mWeatherAdapter;
    WeatherData[] weatherArray = {};
    String currentLoc;
    LocationManager mLocationManager;
    OnDaySelectedListener mCallback;



    @Override
    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        makeUseOfNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public WeatherMatch() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        makeUseOfNewLocation(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mWeatherAdapter = new WeatherAdapter(getActivity().getApplicationContext(), R.layout.row, weatherArray);

        mListView = (ListView) getView().findViewById(R.id.list_view);
        if(mListView != null) {
            mListView.setAdapter(mWeatherAdapter);
        }
    }

    public void getData() {

        String API_URL = setLatLong(initialURL, currentLoc);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //parse the data
                try {


                    JSONObject dailyJSON = response.getJSONObject("daily");
                    JSONArray dataJSON = dailyJSON.getJSONArray("data");

                    weatherArray = new WeatherData[dataJSON.length()];


                    for (int i = 0; i <dataJSON.length(); i++) {
                        JSONObject day = dataJSON.getJSONObject(i);
                        WeatherData weatherObject = new WeatherData();
                        weatherObject.mLowTemp = day.getInt("temperatureMin");
                        weatherObject.mHighTemp = day.getInt("temperatureMax");
                        weatherObject.mDate = day.getInt("time");
                        weatherObject.mDay = day.getInt("time");
                        weatherObject.mIcon = day.getString("icon");
                        weatherArray[i] = weatherObject;

                    }

                }catch(JSONException e) {

                }
                //data parsed
                mWeatherAdapter.setData(weatherArray);



                Log.e("HEREISDATA", response.toString());
            } }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void makeUseOfNewLocation(Location location) {

        if (location == null) {
            return;
        }

        mLocationManager.removeUpdates(this);

        double latDouble = location.getLatitude();
        double longDouble = location.getLongitude();

        String latString = String.valueOf(latDouble);
        String longString = String.valueOf(longDouble);

        String latLong = latString + "," + longString;
        currentLoc = latLong;

        getData();

    }

    public String setLatLong(String roughURL, String loc) {

        return roughURL + loc;

    }


    @Override
    public void onStart() {
        super.onStart();

        //making the fragment
        Fragment f = getFragmentManager().findFragmentById(R.layout.details);
        //assigning list view to v
        ListView v = mListView;
        //making sure neither are null
        if(f != null && v != null){
            //something to do with the clicking on. Can only select one, instead of multiple
            v.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //making sure the onHeadlineselected listener interface was implemented
        //attempt to run this code
        try{
            mCallback = (OnDaySelectedListener) activity;
        }
        //if this type of exception pops up
        catch (ClassCastException e){
            //tell you in the log why it failed. That this interface was not implemented
            throw new ClassCastException(activity.toString() +
                    "must implement OnHeadSelectedListener");
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {

        //setting the mcallback position once the article is selected
        mCallback.onDaySelected(position);
        //setting the item checked - meaning it has been clicked on so can't keep clicking
        l.setItemChecked(position, true);

    }

    public interface OnDaySelectedListener {
        /** Called by Weather Match Fragment when a list item is selected */
        public void onDaySelected(int position);

    }


}




