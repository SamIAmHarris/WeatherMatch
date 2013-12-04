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
    final static String ARG_POSITION = "position";
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

        View rootView = inflater.inflate(R.layout.details, container, false);

        return rootView;

        //making sure that there is not a pre-existing state. Like the screen was just flipped sideways
        if(savedInstanceState !=null) {
            //setting the current position to that saved instance state. so that if there was a previous state
            //we will know what its position was and that will be passed further down in the code
            mCurrentPosition = savedInstanceState.getInt((ARG_POSITION));
        }
        //inflating the view for this fragment. the layout article_fragment
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
            updateDetailsView(args.getInt(ARG_POSITION));
        } else if(mCurrentPosition != -1){
            updateDetailsView(mCurrentPosition);
        }
    }

    public void updateDetailsView(int position) {
        //getting called by the onStart method
        //returns the fragments root view
        View v = getView();

        //typecast the view v to a text view type
        TextView articleView = (TextView) v;
        //actually bring in the text we have for the article. filling with the data
        articleView.setText(Ipsum.Articles[position]);
        //setting the current position to what was passed in. so the current position reflects the current view
        mCurrentPosition = position;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        //making this bundle object the current position when the state was saved. So that
        //when the view gets created after being destroyed it will have a saved instance state
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
