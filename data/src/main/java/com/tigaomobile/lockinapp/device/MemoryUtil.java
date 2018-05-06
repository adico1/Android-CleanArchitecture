package com.tigaomobile.lockinapp.device;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;
import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by adi on 29/04/2018.
 * Description:
 */

public class MemoryUtil {
    private static final String TAG = MemoryUtil.class.getSimpleName();

    private Context _context = null;

    public MemoryUtil(Context context) {
        _context = context;
    }

    // Get a MemoryInfo object for the device's current memory status.
    public ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) _context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public void logMemInfo(ActivityManager.MemoryInfo memInfo) {
        Log.i(TAG, "memInfo.totalMem: " + memInfo.totalMem);
        Log.i(TAG, "memInfo.availMem: " + memInfo.availMem);
        Log.i(TAG, "memInfo.usedMem: " + (memInfo.totalMem - memInfo.availMem));
        Log.i(TAG, "memInfo.threshold: " + memInfo.threshold);
        Log.i(TAG, "memInfo.lowMemory: " + memInfo.lowMemory);
    }

    public void trimMemoryHandler(int level) {
        switch(level) {
            case TRIM_MEMORY_RUNNING_MODERATE:
                Log.i(TAG, "TRIM_MEMORY_RUNNING_MODERATE");
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                Log.i(TAG, "TRIM_MEMORY_RUNNING_LOW");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                Log.i(TAG, "TRIM_MEMORY_RUNNING_CRITICAL");
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                Log.i(TAG, "TRIM_MEMORY_UI_HIDDEN");
                break;
            case TRIM_MEMORY_BACKGROUND:
                Log.i(TAG, "TRIM_MEMORY_BACKGROUND");
                break;
            case TRIM_MEMORY_MODERATE:
                Log.i(TAG, "TRIM_MEMORY_MODERATE");
                break;
            case TRIM_MEMORY_COMPLETE:
                Log.i(TAG, "TRIM_MEMORY_COMPLETE");
                break;

        }
    }
}
