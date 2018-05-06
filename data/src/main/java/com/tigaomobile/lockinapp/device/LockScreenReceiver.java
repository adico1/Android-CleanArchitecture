package com.tigaomobile.lockinapp.device;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;

/**
 * Created by adi on 28/04/2018.
 * Description:
 */

public class LockScreenReceiver extends BroadcastReceiver
{
    private final static String TAG = LockScreenReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "onReceiver");

        if (intent != null && intent.getAction() != null)
        {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            {
                // Screen is on but not unlocked (if any locking mechanism present)
                Log.i(TAG, "ACTION_SCREEN_ON");
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                // Screen is locked
                Log.i(TAG, "ACTION_SCREEN_OFF");

                if(isAppForeground(context)) {
                    Log.i(TAG, "App In Foreground, skipping reload");
                    return;
                }

                Log.i(TAG, "App not in Foreground, reloading");
                AppIntents.startLockScreenActivity(context);
            }
            else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT))
            {
                // Screen is unlocked
                Log.i(TAG, "ACTION_USER_PRESENT");
            }
        }
    }

    public boolean isAppForeground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
    }
}
