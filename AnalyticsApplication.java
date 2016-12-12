package com.lindsaykroeger.mygame;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Lindsay on 12/11/2016.
 */

public class AnalyticsApplication extends Application {

    private Tracker mTracker;
    private static final String trackerID = "UA-88820263-2";


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(trackerID);
        }
        return mTracker;
    }
}
