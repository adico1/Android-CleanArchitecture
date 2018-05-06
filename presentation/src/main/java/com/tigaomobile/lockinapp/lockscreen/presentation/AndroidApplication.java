/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tigaomobile.lockinapp.lockscreen.presentation;

import android.app.Activity;
import android.app.Application;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.components.ApplicationComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.components.DaggerApplicationComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {
  private static final String TAG = AndroidApplication.class.getSimpleName();

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    this.initializeLeakDetection();
  }

  @Override public void onTerminate() {
    super.onTerminate();
    applicationComponent.deviceManager().disconnectDevice();
  }

  private void initializeInjector() {
    this.applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

    applicationComponent.deviceManager().connectDevice();

    ActivityLifecycleCallbacks cb = new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
      }

      @Override
      public void onActivityStarted(Activity activity) {
        Log.i(TAG, "onActivityStarted");
      }

      @Override
      public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed");
        applicationComponent.deviceManager().disableDeviceKeyGuard();
      }

      @Override
      public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityPaused");

      }

      @Override
      public void onActivityStopped(Activity activity) {
        Log.i(TAG, "onActivityStopped");
        Debug.stopMethodTracing();
      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState");

      }

      @Override
      public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "onActivityDestroyed");

      }
    };
    this.registerActivityLifecycleCallbacks(cb);
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  private void initializeLeakDetection() {
    if (BuildConfig.DEBUG) {
      LeakCanary.install(this);
    }
  }

  //private Thread.UncaughtExceptionHandler defaultUEH;
  //private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
  //
  //  @Override public void uncaughtException(Thread thread, Throwable ex) {
  //    ex.printStackTrace();
  //
  //    PackageManager packageManager = getPackageManager();
  //    Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
  //    ComponentName componentName = intent.getComponent();
  //    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
  //    startActivity(mainIntent);
  //    Runtime.getRuntime().exit(0);
  //  }
  //};
}
