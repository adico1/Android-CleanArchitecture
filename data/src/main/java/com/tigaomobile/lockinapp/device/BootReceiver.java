package com.tigaomobile.lockinapp.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by adi on 05/05/2018.
 * Description:
 */
public class BootReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
      AppIntents.startLockScreenActivity(context);
    }

    // FOR SERVICE BOOT UP UNCOMMENT THE FOLLOWING LINES AND COMMENT THE ABOVE
    //        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
    //            AppIntents.startBootUpService(context);
    //        }
  }
}
