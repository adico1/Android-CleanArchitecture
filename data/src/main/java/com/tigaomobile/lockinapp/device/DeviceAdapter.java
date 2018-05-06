package com.tigaomobile.lockinapp.device;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.tigaomobile.lockinapp.lockscreen.domain.device.DeviceManager;
import java.lang.ref.WeakReference;
import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by adi on 06/05/2018.
 * Description:
 */
@Singleton
public class DeviceAdapter implements DeviceManager {
  private static final String TAG = DeviceAdapter.class.getSimpleName();

  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

  private final WeakReference<Context> context;
  private final WeakReference<Context> application;
  private final WeakReference<LockScreenReceiver> lockScreenReceiver;

  @Inject
  DeviceAdapter(@NonNull Context application,
                @NonNull Context context) {

    super();
    this.context = new WeakReference<>(context);
    this.application = new WeakReference<>(application);
    this.lockScreenReceiver = new WeakReference<>(new LockScreenReceiver());
  }

  @Override public void connectDevice() {
    AppIntents.startBootUpService(context.get());
    registerOnOffReceiver();
    disableDeviceKeyGuard();
    registerRestartOnException();
  }

  @Override public void disconnectDevice() {
    unregisterOnOffReceiver();
  }

  private void registerOnOffReceiver() {
    IntentFilter lockFilter = new IntentFilter();
    lockFilter.addAction(Intent.ACTION_SCREEN_ON);
    lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
    lockFilter.addAction(Intent.ACTION_USER_PRESENT);
    application.get().registerReceiver(lockScreenReceiver.get(), lockFilter);
  }

  private void unregisterOnOffReceiver() {
    application.get().unregisterReceiver(lockScreenReceiver.get());
    lockScreenReceiver.clear();
  }

  @Override public void disableDeviceKeyGuard() {
    KeyguardManager keyguardManager =
        (KeyguardManager) application.get().getSystemService(KEYGUARD_SERVICE);
    KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
    lock.disableKeyguard();
  }

  private void registerRestartOnException() {
    uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        Log.d(TAG, "Uncaught exception start!");
        ex.printStackTrace();

        AppIntents.restartService(application.get());
        Runtime.getRuntime().exit(0);
      }
    };
  }
}
