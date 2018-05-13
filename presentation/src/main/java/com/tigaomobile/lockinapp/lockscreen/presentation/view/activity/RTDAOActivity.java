package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.tigaomobile.lockinapp.device.RTDAO;
import java.lang.ref.WeakReference;

public abstract class RTDAOActivity extends BaseActivity {
    private final String TAG = "RTDAOActivity";
    protected WeakReference<RTDAO> mService = null;
    private boolean mBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            // Log.i(TAG, "onServiceConnected");

            RTDAO.LocalBinder binder = (RTDAO.LocalBinder) service;
            mService = new WeakReference<>(binder.getService());

            if(mService.get() != null){
                // Log.i(TAG, "Service is bonded successfully!");

                //do whatever you want to do after successful binding
                mBound = true;
                onServiceBounded();
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Log.i(TAG, "onServiceDisconnected");

            mService.clear();
            mBound = false;
        }
    };

    protected void onServiceBounded() {
        // Log.i(TAG, "Service is bonded successfully!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onStart");

        // Bind to LocalService
        Intent intent = new Intent(this, RTDAO.class);
        startService(intent);
        mBound = bindService(intent, mConnection, RTDAOActivity.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onStop");
        super.onPause();
        // Unbind from the service
        if (mBound || mConnection != null) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
