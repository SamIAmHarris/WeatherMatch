package com.samiamharris.weathermatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class WeatherActivity extends ActionBarActivity implements WeatherListFragment.OnDaySelectedListener{

    //1. create member variable
    DataListener mDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            WeatherListFragment weatherMatchFragment = new WeatherListFragment();
            weatherMatchFragment.setArguments(getIntent().getExtras());


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, weatherMatchFragment)
                    .addToBackStack(null)
                    .commit();

        }
        //2. instantiate that variable
        mDataListener = new DataListener(this);

        Intent wUSIntent = new Intent(this, WeatherUpdateService.class);
        startService(wUSIntent);
        Log.e("tres amigos", "Start Intent");

        requestUpdateFromService();
    }


    public void requestUpdateFromService() {

        Intent updateIntent = new Intent(this, WeatherUpdateService.class);
        updateIntent.putExtra("UPDATEREQUEST", true);
        startService(updateIntent);
        Log.e("tres amigos", "Update sent");
    }

    public void onDataReceive(String data){
        //do something with this data which is passed from the weather update service
        Log.e("tres amigos", "Broadcast Receiver");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onDaySelected(String dayData) {

        WeatherDetailFragment detailsFragment = (WeatherDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.layout.details);


        if(detailsFragment == null){

            WeatherDetailFragment onePaneFragment = new WeatherDetailFragment();

            Bundle args = new Bundle();
            args.putString(WeatherDetailFragment.JSON_Object, dayData);
            onePaneFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, onePaneFragment)
                    .addToBackStack(null)
                    .commit();


        }else{

            detailsFragment.updateDetailsView(dayData);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    //4. unregister the receiver to listen for data
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDataListener);
    }

    //3. register the receiver to listen for data
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mDataListener, new IntentFilter("com.samiamharris.weathermatch.CUSTOM_INTENT"));
    }
}

