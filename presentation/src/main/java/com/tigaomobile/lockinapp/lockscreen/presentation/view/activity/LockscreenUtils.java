package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.app.Activity;

public class LockscreenUtils {

    // Member variables
    private OnLockStatusChangedListener mLockStatusChangedListener;

    // Interface to communicate with owner activity
    public interface OnLockStatusChangedListener
    {
        void onLockStatusChanged(boolean isLocked);
    }

    /***
     * Init the LockscreenUtil class & reset the OverlayDialog
     * Step 5 in the create flow
     */
    public LockscreenUtils() {
        // Log.i("LockscreenUtils", "LockscreenUtils");
    }

    /***
     * Display overlay dialog with a view to prevent home button click
     * Step 9 in create app flow
     */
    public void lock(Activity activity) {
            mLockStatusChangedListener = (OnLockStatusChangedListener) activity;
    }


    // Unlock the home button and give callback to unlock the screen
    public void unlock() {
        // Log.i("LockscreenUtils", "unlock");
            if(mLockStatusChangedListener!=null)
            {
                mLockStatusChangedListener.onLockStatusChanged(false);
            }

    }
}