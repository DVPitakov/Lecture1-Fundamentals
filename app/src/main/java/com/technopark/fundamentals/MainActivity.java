package com.technopark.fundamentals;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    ScreenStateReceiver screenStateReceiver;
    BoundService service;
    boolean bound;

    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            MainActivity.this.service = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        Button startActivity = (Button) findViewById(R.id.btn_start_activity);
        startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity();
            }
        });

        Button startService = (Button) findViewById(R.id.btn_start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTestService();
            }
        });

        Button stopService = (Button) findViewById(R.id.btn_stop_service);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTestService();
            }
        });

        Button bindService = (Button) findViewById(R.id.btn_bind_service);
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindTestService();
            }
        });

        Button unbindService = (Button) findViewById(R.id.btn_unbind_service);
        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindTestService();
            }
        });

        findViewById(R.id.btn_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumber();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenStateReceiver = new ScreenStateReceiver(), filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        if (screenStateReceiver != null) {
            unregisterReceiver(screenStateReceiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    private void startSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void startTestService() {
        Intent intent = new Intent(this, StartedService.class);
        intent.putExtra(StartedService.EXTRA_ARGUMENT, "Hello from Main Activity");
        startService(intent);
    }

    private void stopTestService() {
        Intent intent = new Intent(this, StartedService.class);
        stopService(intent);
    }

    private void bindTestService() {
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void unbindTestService() {
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    private void generateRandomNumber() {
        if (bound) {
            int num = service.getRandomNumber();
            Toast.makeText(this, "Number is " + num, Toast.LENGTH_SHORT).show();
        }
    }
}
