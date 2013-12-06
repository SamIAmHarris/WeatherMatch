package com.samiamharris.weathermatch;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by samharris on 12/6/13.
 */
public class WaitService extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public WaitService() {
        super("WaitService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("this guy", "is about to make us wait");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */



    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        long endTime = System.currentTimeMillis() + 5*1000;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        }
    }

}
