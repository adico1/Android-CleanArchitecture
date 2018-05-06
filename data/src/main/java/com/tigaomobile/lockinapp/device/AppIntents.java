package com.tigaomobile.lockinapp.device;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

/**
 * Created by adi on 05/05/2018.
 * Description:
 */
public class AppIntents {
  public static void startBootUpService(Context context) {
    Intent serviceIntent = new Intent(context, BootService.class);
    context.startService(serviceIntent);
  }

  public static void delayedStartBootupService(Context context) {
    Intent restartServiceIntent = new Intent(context.getApplicationContext(),
        context.getClass());
    restartServiceIntent.setPackage(context.getPackageName());

    PendingIntent restartServicePendingIntent = PendingIntent.getService(
        context.getApplicationContext(), 1, restartServiceIntent,
        PendingIntent.FLAG_ONE_SHOT);
    AlarmManager alarmService = (AlarmManager) context.getApplicationContext()
        .getSystemService(Context.ALARM_SERVICE);
    alarmService.set(AlarmManager.ELAPSED_REALTIME,
        SystemClock.elapsedRealtime() + 1000,
        restartServicePendingIntent);
  }

  public static void startLockScreenActivity(Context context) {
    restartService(context);

    //Context app = context.getApplicationContext();
    //
    //Intent i = app.getPackageManager()
    //    .getLaunchIntentForPackage(app.getPackageName() );
    //
    //i.addFlags(
    //    Intent.FLAG_ACTIVITY_CLEAR_TOP |
    //    Intent.FLAG_ACTIVITY_NEW_TASK |
    //    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
    //    Intent.FLAG_ACTIVITY_CLEAR_TASK |
    //    Intent.FLAG_ACTIVITY_NO_HISTORY );
    //
    //app.startActivity(i);
  }

  public static void restartService(Context context) {
    Context app = context.getApplicationContext();

    PackageManager packageManager = app.getPackageManager();
    Intent intent = packageManager.getLaunchIntentForPackage( app.getPackageName());
    ComponentName componentName = intent.getComponent();
    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
    mainIntent.setFlags(
        Intent.FLAG_ACTIVITY_CLEAR_TOP |
        Intent.FLAG_ACTIVITY_NEW_TASK |
        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
        Intent.FLAG_ACTIVITY_CLEAR_TASK |
        Intent.FLAG_ACTIVITY_NO_HISTORY );
    app.startActivity(mainIntent);
  }
}
