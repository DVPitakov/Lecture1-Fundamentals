package com.technopark.fundamentals;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class BoundService extends Service {
    private final static String TAG = BoundService.class.getSimpleName();

    private final IBinder binder = new LocalBinder();
    private final Random random = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public int getRandomNumber() {
        return random.nextInt(100);
    }

    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }
}
