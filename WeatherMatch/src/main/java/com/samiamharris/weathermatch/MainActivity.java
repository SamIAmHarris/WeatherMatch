package com.samiamharris.weathermatch;

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onDaySelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.layout.details);


        if(detailsFragment == null){

            DetailsFragment onePaneFragment = new DetailsFragment();

            Bundle args = new Bundle();
            args.putInt(DetailsFragment.ARG_POSITION, position);
            onePaneFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, onePaneFragment)
                    .addToBackStack(null)
                    .commit();


        }else{

            detailsFragment.updateDetailsView(position);
        }


    }

}

