package com.samiamharris.weathermatch;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by samharris on 12/6/13.
 */
public class WeatherUpdateService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    DataManager mDataManager;

    public WeatherUpdateService() {
        mDataManager = new DataManager(this);

    }


    //Will call this when we think the data is ready to send
    public void sendDataToActivity(String myData)
    {
        Intent intent = new Intent();
        intent.setAction("com.samiamharris.weathermatch.CUSTOM_INTENT");
        intent.putExtra("MyData", myData);
        sendBroadcast(intent);
        Log.e("tres amigos", "Send Broadcast");

    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.



                synchronized (this) {

                    Bundle extras = msg.getData();

                    if (extras != null) {
                        boolean value = extras.getBoolean("UPDATEREQUEST");

                        if (value == true) {
                            sendDataToActivity("Stuff man");
                        }


                    }
                }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job

            Notification n = new Notification(getApplicationContext());

            stopSelf(msg.arg1);
        }
    }


    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Thread.MIN_PRIORITY);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();




        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.setData(intent.getExtras());


        //Do work in here
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}

