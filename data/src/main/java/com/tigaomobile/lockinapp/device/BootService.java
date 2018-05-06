package com.tigaomobile.lockinapp.device;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by adi on 05/05/2018.
 * Description:
 */
public class BootService extends Service {
  private static final String TAG = BootService.class.getSimpleName();

  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind");
    return null;
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy");
    Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();

    AppIntents.delayedStartBootupService(this);

    // TODO: does including destroy changes the flow?
    //super.onDestroy();
  }

  @Override
  public void onStart(Intent intent, int startid)
  {
    Log.d(TAG, "onStart");

    // TODO: does including super.onstart changes the flow?
    //super.onStart(intent, startid);

  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    Log.d(TAG, "TASK REMOVED");


    AppIntents.delayedStartBootupService(this);

    super.onTaskRemoved(rootIntent);

  }
}
