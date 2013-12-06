package com.samiamharris.weathermatch;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements WeatherMatch.OnDaySelectedListener{

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

        Intent i = new Intent(this,WaitService.class);
        startService(i);
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

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.layout.details);


        if(detailsFragment == null){

            DetailsFragment onePaneFragment = new DetailsFragment();

            Bundle args = new Bundle();
            args.putString(DetailsFragment.JSON_Object, dayData);
            onePaneFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, onePaneFragment)
                    .addToBackStack(null)
                    .commit();


        }else{

            detailsFragment.updateDetailsView(dayData);
        }


    }

}

