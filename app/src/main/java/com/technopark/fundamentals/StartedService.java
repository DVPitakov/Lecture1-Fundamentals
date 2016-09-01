package com.technopark.fundamentals;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartedService extends Service {
    private final static String TAG = StartedService.class.getSimpleName();

    public final static String EXTRA_ARGUMENT = "argument";

    private Thread thread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String arg = "";
        if (intent != null) {
            arg = intent.getStringExtra(EXTRA_ARGUMENT);
        }
        Log.d(TAG, String.format("onStartCommand. Argument is '%s'", arg));

        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        Log.d(TAG, "Count " + i);
                        i++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });
            thread.start();
        }

        // TODO: попробуйте вернуть другие значения из списка ниже. Что изменилось?
//        return START_STICKY;
        return START_NOT_STICKY;
//        return START_REDELIVER_INTENT;
    }
}
