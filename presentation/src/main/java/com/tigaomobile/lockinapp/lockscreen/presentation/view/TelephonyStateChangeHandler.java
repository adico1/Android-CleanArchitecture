package com.tigaomobile.lockinapp.lockscreen.presentation.view;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 */

// Handle events of calls and unlock screen if necessary
public class TelephonyStateChangeHandler extends PhoneStateListener {
    public static final String TAG = TelephonyStateChangeHandler.class.getSimpleName();
    private final Context context;

    private boolean isUnlockedByCall = false;


    public TelephonyStateChangeHandler(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.i(TAG,"onCallStateChanged");

        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;

        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isUnlockedByCall = true;
                activity.hideLockEffects();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if(isUnlockedByCall) {
                    isUnlockedByCall = false;
                    activity.lockDevice(context);
                }
                break;
        }
    }
}
