package com.samiamharris.weathermatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WeatherActivity extends ActionBarActivity implements WeatherMatch.OnDaySelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            WeatherMatch weatherMatchFragment = new WeatherMatch();
            weatherMatchFragment.setArguments(getIntent().getExtras());


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, weatherMatchFragment)
                    .addToBackStack(null)
                    .commit();

        }




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
        Intent j = new Intent(this, WeatherUpdateService.class);
        startService(j);
    }
}

