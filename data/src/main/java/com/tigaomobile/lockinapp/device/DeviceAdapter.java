package com.tigaomobile.lockinapp.device;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
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

  private void disableDeviceKeyGuard() {
    KeyguardManager keyguardManager =
        (KeyguardManager) application.get().getSystemService(KEYGUARD_SERVICE);
    KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
    lock.disableKeyguard();
  }
}
