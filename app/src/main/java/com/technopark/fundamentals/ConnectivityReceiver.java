package com.technopark.fundamentals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {
    private final static String TAG = ConnectivityReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = (intent != null) ? intent.getAction() : "NO ACTION";
        Log.d(TAG, String.format("onReceive. Action is '%s'", action));
    }
}
