/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tigaomobile.lockinapp.lockscreen.presentation.presenter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.tigaomobile.lockinapp.lockscreen.domain.Theme;
import com.tigaomobile.lockinapp.lockscreen.domain.exception.DefaultErrorBundle;
import com.tigaomobile.lockinapp.lockscreen.domain.exception.ErrorBundle;
import com.tigaomobile.lockinapp.lockscreen.domain.interactor.DefaultObserver;
import com.tigaomobile.lockinapp.lockscreen.domain.interactor.GetTheme;
import com.tigaomobile.lockinapp.lockscreen.domain.interactor.GetUserDetails;
import com.tigaomobile.lockinapp.lockscreen.presentation.device.TelephonyManager;
import com.tigaomobile.lockinapp.lockscreen.presentation.exception.ErrorMessageFactory;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.PerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.mapper.ThemeModelDataMapper;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ThemeModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.UserModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.ScreenSliderPagerView;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.ActionFactory;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.IAction;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.UnlockChallengeActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.adapter.ScreenSlidePagerAdapter;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.adapter.UsersAdapter;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.transformers.ZoomOutPageTransformer;
import javax.inject.Inject;

import static com.tigaomobile.lockinapp.lockscreen.presentation.view.MApplication.getConfig;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ScreenSlidePagerActivityPresenter implements Presenter {
  public final int RC_EXT_APP_LOCK = 34563;
  private IAction doNextAfterAppUnlock = null;

  private final TelephonyManager telephonyManager;
  private ScreenSliderPagerView screenSliderPagerView;

  private final GetTheme getThemeUseCase;
  private final ThemeModelDataMapper themeModelDataMapper;
  private ScreenSlidePagerAdapter screenSlidePagerAdapter;
  private ViewPager mPager;
  private ScreenSlidePagerActivity mActivity;
  private Config config;

  @Inject
  public ScreenSlidePagerActivityPresenter(GetTheme getThemeUseCase,
      ThemeModelDataMapper themeModelDataMapper,
      Config config,
      TelephonyManager telephonyManager) {
    this.getThemeUseCase = getThemeUseCase;
    this.themeModelDataMapper = themeModelDataMapper;
    this.config = config;
    this.telephonyManager = telephonyManager;
  }

  public void setView(@NonNull ScreenSliderPagerView view) {
    this.screenSliderPagerView = view;
  }

  @Override public void init() {
    lockDevice(mActivity);
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getThemeUseCase.dispose();
    this.screenSliderPagerView = null;
    doNextAfterAppUnlock = null;
    telephonyManager.destroy();

  }

  /**
   * Adds a {@link android.support.v4.app.Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  public void addFragment(int containerViewId, android.support.v4.app.Fragment fragment) {
    final FragmentTransaction fragmentTransaction =
        mActivity.getSupportFragmentManager().beginTransaction();

    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Initializes the presenter by start retrieving the user list.
   */
  public void initialize(final ViewPager mPager) {
    // Instantiate a ViewPager and a PagerAdapter.
        /*
      The pager adapter, which provides the pages to the view pager widget.
     */
    this.mPager = mPager;
    mActivity = (ScreenSlidePagerActivity)screenSliderPagerView.context();
    this.screenSlidePagerAdapter = new ScreenSlidePagerAdapter(mActivity.getSupportFragmentManager(), mPager, this.config);
    mPager.setAdapter(this.screenSlidePagerAdapter);
    mPager.setPageTransformer(true, new ZoomOutPageTransformer());

    this.loadTheme();
  }

  public boolean pagerOnTouch(View v, MotionEvent event) {

    if (mPager.getCurrentItem() == 0 && calcDirection(v, event).equals("right")) {
      mActivity.unlockDevice();
      return false;
    }

    return false;
  }

  /**
   * Loads all users.
   */
  private void loadTheme() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getTheme();
  }

  public void onUserClicked(ThemeModel themeModel) {
    this.screenSliderPagerView.viewTheme(themeModel);
  }

  private void showViewLoading() {
    this.screenSliderPagerView.showLoading();
  }

  private void hideViewLoading() {
    this.screenSliderPagerView.hideLoading();
  }

  private void showViewRetry() {
    this.screenSliderPagerView.showRetry();
  }

  private void hideViewRetry() {
    this.screenSliderPagerView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.screenSliderPagerView.context(),
        errorBundle.getException());
    this.screenSliderPagerView.showError(errorMessage);
  }

  private void showThemeInView(Theme theme) {
    final ThemeModel themeModel =
        this.themeModelDataMapper.transform(theme);
    this.screenSliderPagerView.renderTheme(themeModel);
  }

  private void getTheme() {
    int themeId = 0;
    this.getThemeUseCase.execute(new ThemeObserver(), GetTheme.Params.forTheme(themeId));
  }

  private final class ThemeObserver extends DefaultObserver<Theme> {

    @Override public void onComplete() {
      ScreenSlidePagerActivityPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      ScreenSlidePagerActivityPresenter.this.hideViewLoading();
      ScreenSlidePagerActivityPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      ScreenSlidePagerActivityPresenter.this.showViewRetry();
    }

    @Override public void onNext(Theme theme) {
      ScreenSlidePagerActivityPresenter.this.showThemeInView(theme);
    }
  }

  public void lockDevice(Context context) {
    try {
      lockUI();
      lockFeatures(context);
    } catch(Exception ex) {
      ex.printStackTrace();
      //Log.e(className, ex.getMessage());
      Crashlytics.logException(ex);
      unlockDevice();
    }
  }

  public void lockUI() {
  }

  public void lockFeatures(Context context) {
    ///Log.i(TAG,"lockFeatures");
    telephonyManager.attachPhoneListener();
  }

  public void onLockStatusChanged(boolean isLocked) {
    // Log.i(className,"onLockStatusChanged");
    if (!isLocked) {
      unlockDevice();
    }
  }

  // handle the key press events here itself
  public boolean dispatchKeyEvent(KeyEvent event) {
    // Log.i("FullscreenBehaviour", "onKeyDown");
    return !(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
        || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
        || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) && (event.getKeyCode() == KeyEvent.KEYCODE_HOME);

  }

  public boolean onBackPressed() {
    if (mPager.getCurrentItem() == 0) {
      // If the user is currently looking at the first step, allow the system to handle the
      // Back button. This calls finish() on this activity and pops the back stack.
      return true;
    } else {
      // Otherwise, select the previous step.
      mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }
    return false;
  }

  public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    // Log.i("FullscreenBehaviour","onKeyDown");
    if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        || (keyCode == KeyEvent.KEYCODE_POWER)
        || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
      return true;
    }
    if ((keyCode == KeyEvent.KEYCODE_HOME)) {
      // Log.i("Home Button","Clicked");
      return true;
    }
    if(keyCode== KeyEvent.KEYCODE_BACK)
    {
      mActivity.finish();
    }
    return false;

  }

  public void unlockUI() {

  }

  private void unlockFeatures() {
    telephonyManager.detachPhoneListener();
  }

  public void unlockApp() {
    unlockAppAnd(new ActionFactory(this.mActivity).create("unlock", ""));
  }

  void onAppUnlocked(IAction action) {
    action.run();
  }


  public void unlockAppAnd(IAction doNext) {
    // Log.i(className, "unlockAppAnd");

    String extAppUrl = ""; // mService.get().readLock();
    if (!extAppUrl.isEmpty()) {
      Bundle bundle = new Bundle();
      bundle.putString("url", extAppUrl);
      doNextAfterAppUnlock = doNext;
      Intent waIntent = new Intent(this.mActivity, UnlockChallengeActivity.class);
      waIntent.putExtras(bundle);

      waIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      this.mActivity.startActivityForResult(waIntent, RC_EXT_APP_LOCK);
    } else {
      onAppUnlocked(doNext);
      //unlockDevice();
    }
  }

  public void unlockDevice() {
    hideLockEffects();
    this.mActivity.finish();
  }

  public void hideLockEffects() {
    unlockFeatures();
    unlockUI();
  }

  public void onExternalAppResult(Intent data) {
    String strMessage = data.getStringExtra("result");
    if(strMessage.equals("ok")) {
      // Log.i(TAG, "onActivityResult: message >>" + strMessage);
      onAppUnlocked(doNextAfterAppUnlock);
      doNextAfterAppUnlock = null;
    }
  }

  private float x1 = 0;
  private float y1 = 0;

  private String calcDirection(View v, MotionEvent event) {

    String direction = "";

    switch(event.getAction()) {
      case(MotionEvent.ACTION_DOWN):
        x1 = event.getX();
        y1 = event.getY();
        // Log.i(TAG, "ACTION_DOWN: x1: " + x1 + ", y1: " + y1);
        break;
      case MotionEvent.ACTION_UP:
        x1=0;
        y1=0;
        // Log.i(TAG, "ACTION_UP: x1: " + x1 + ", y1: " + y1);
        break;
      case(MotionEvent.ACTION_MOVE): {
        if(x1 == 0 && y1 == 0) {
          x1 = event.getX();
          y1 = event.getY();
          // Log.i(TAG, "ACTION_DOWN: x1: " + x1 + ", y1: " + y1);
          break;
        }

        float dx = event.getX() - x1;
        float dy = event.getY() - y1;

        // Log.i(TAG, "ACTION_MOVE: x1: " + x1 + ", y1: " + y1 + "x2: " + event.getX() + ", y2: " + event.getY() + "dx: " + dx + ", dy: " + dy);
        if (Math.abs(dx) <= Math.abs(dy)) {
          //vertical scroll
          v.getParent().requestDisallowInterceptTouchEvent(true);

          if(dy >0) {
            // Log.i(TAG, "down");
            direction = "down";
          } else {
            // Log.i(TAG, "up");
            direction = "up";
          }
        } else {
          v.getParent().requestDisallowInterceptTouchEvent(false);

          if(dx >0) {
            // Log.i(TAG, "right");
            direction = "right";
          } else {
            // Log.i(TAG, "left");
            direction = "left";
          }
        }
      }
    }

    return direction;
  }
}
