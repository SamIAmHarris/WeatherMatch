package com.samiamharris.weathermatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samharris on 12/3/13.
 */
public class DetailsFragment extends Fragment {

    String Details_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=great%20dogs";
    NetworkImageView mNetworkImageView;
    ImageLoader mImageLoader;
    TextView mDetDay;
    TextView mDetHighTemp;
    TextView mDetLowTemp;
    ImageView mDetEmoji;
    JSONObject mData;

    String dataToSave;

    WeatherData mWeather = new WeatherData();

    final static String JSON_Object = "object";
    int mCurrentPosition = -1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDetDay = (TextView)view.findViewById(R.id.detday);
        mDetHighTemp = (TextView)view.findViewById(R.id.detHighTemp);
        mDetLowTemp = (TextView)view.findViewById(R.id.detLowTemp);

        mDetEmoji = (ImageView)view.findViewById(R.id.detEmoji);

        mNetworkImageView = (NetworkImageView)view.findViewById(R.id.network_image_view);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState !=null) {
            //setting the current position to that saved instance state. so that if there was a previous state
            //we will know what its position was and that will be passed further down in the code

            dataToSave = savedInstanceState.getString((JSON_Object));
         }

        View thisFragmentView = inflater.inflate(R.layout.details, container, false);

        //then returning that inflated view. These last two lines of code will run regardless of if. Method inflates the fragment
        return thisFragmentView;

    }

    @Override
    public void onStart() {
        super.onStart();
        //communicating data to the fragment. State that was saved previously
        Bundle args = getArguments();
        //if there was a previous state then set the view to what that state was
        if(args!= null) {
            //PROBLEM HERE
            updateDetailsView(args.getString(JSON_Object));
        } else if(dataToSave != null){
            updateDetailsView("");
        }
    }

    public void updateDetailsView(String dayData) {
        //getting called by the onStart method
        //returns the fragments root view
        dataToSave = dayData;
        View v = getView();

        try {
        mData = new JSONObject(dayData);


            mWeather.setmDay(mData.getInt("time"));
            mWeather.setmHighTemp(mData.getInt("temperatureMax"));
            mWeather.setmLowTemp(mData.getInt("temperatureMin"));
            mWeather.setmIcon(mData.getString("icon"));


        } catch (JSONException j) {

        }


        TextView mDetDay = (TextView) v.findViewById(R.id.detday);
        TextView mDetHighTemp = (TextView) v.findViewById(R.id.detHighTemp);
        TextView mDetLowTemp = (TextView) v.findViewById(R.id.detLowTemp);
        ImageView mDetEmoji = (ImageView) v.findViewById(R.id.detEmoji);



        mDetDay.setText(mWeather.simpleDay(mWeather.getmDay()));
        mDetHighTemp.setText((String.valueOf(mWeather.getmHighTemp()))+ "°");
        mDetLowTemp.setText((String.valueOf(mWeather.getmLowTemp()))+ "°");

        int resId = getActivity().getResources().getIdentifier(mWeather.getEmoji(mWeather.getmIcon()), "drawable", getActivity().getPackageName());
        mDetEmoji.setImageResource(resId);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(JSON_Object, dataToSave);
    }
}
