package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import com.crashlytics.android.Crashlytics;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.HasComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.components.DaggerThemeComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.components.ThemeComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ThemeModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.presenter.ScreenSlidePagerActivityPresenter;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.ScreenSliderPagerView;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.IAction;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

/**
 * Created by adi on 22/03/2018.
 * Description:
 *
 *
 *
 */

public class ScreenSlidePagerActivity
    extends BaseActivity
    implements ScreenSliderPagerView,
               HasComponent<ThemeComponent>,
               LockscreenUtils.OnLockStatusChangedListener {

    private final String TAG = ScreenSlidePagerActivity.class.getSimpleName();

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, ScreenSlidePagerActivity.class);
        return callingIntent;
    }

    private ThemeComponent themeComponent;

    private FullscreenBehaviour fullscreenActivity;

    @Inject ScreenSlidePagerActivityPresenter screenSlidePagerActivityPresenter;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    @BindView(R.id.pager) ViewPager mPager;


    @Override protected void onResume() {
        super.onResume();
        fullscreenActivity.goFullScreen(mPager);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy - Stopping Service");

        // Stopping the long-live service to force it to restart (defined as STICKY)
        // Stopping the service before super.onDestroy intentionally
        super.onDestroy();

        // remove layers covering the phone panels allowing clicking home, recent, back & the other panels
        screenSlidePagerActivityPresenter.unlockUI();
        screenSlidePagerActivityPresenter.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //// Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        //if (!isTaskRoot()) {
        //    // Android launched another instance of the root activity into an existing task
        //    //  so just quietly finish and go away, dropping the user back into the activity
        //    //  at the top of the stack (ie: the last state of this task)
        //    // Don't need to finish it again since it's finished in super.onCreate .
        //    return;
        //}

        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setupWindowLayout();

        this.initializeInjector();
        this.getComponent(ThemeComponent.class).inject(this);

        setContentView(R.layout.activity_screen_slide);
        ButterKnife.bind(this);

        fullscreenActivity = new FullscreenBehaviour();
        fullscreenActivity.onInit(this, mPager);

        this.screenSlidePagerActivityPresenter.setView(this);
        if (savedInstanceState == null) {

            this.loadTheme();
            //lockDevice(this);
        }
    }

    private void initializeInjector() {
        this.themeComponent = DaggerThemeComponent.builder()
            .applicationComponent(getApplicationComponent())
            .activityModule(getActivityModule())
            .build();
    }

    //public ThemeComponent getComponent() {
    //    return this.themeComponent;
    //}

    /**
     * Loads all users.
     */
    private void loadTheme() {
        this.screenSlidePagerActivityPresenter.initialize(mPager);
    }

    public void setupWindowLayout() {
        // Remove title bar from the window
        // TODO: check if still necessary using fullscreen activity
        // run before super because it's part of the window configuration
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Define the current window to be on top of all other layer
        Window win = getWindow();

        win.setType(
            WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        win.addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                //| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON <- remove because suspiciously never let the app go to sleep
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        try {
            // set an exit transition
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // inside your activity (if you did not enable transitions in your theme)
                win.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
                win.setExitTransition(new Explode());
            }
        } catch(Exception ex) {
            Crashlytics.logException(ex);
            ex.printStackTrace();
        }
    }

    @OnTouch(R.id.pager)
    boolean onTouch(View v, MotionEvent event) {
        return this.screenSlidePagerActivityPresenter.pagerOnTouch(v, event);
    }

    @Override
    public void onBackPressed() {
        if(screenSlidePagerActivityPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override public void renderTheme(ThemeModel themeModel) {

    }

    @Override public void viewTheme(ThemeModel themeModel) {

    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void showError(String message) {

    }

    @Override public Context context() {
        return this;
    }

    @Override public ThemeComponent getComponent() {
        return this.themeComponent;
    }

    public void lockDevice(Context context) {
        screenSlidePagerActivityPresenter.lockDevice(context);
    }

    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        return screenSlidePagerActivityPresenter.onKeyDown(keyCode, event);
    }

    // handle the key press events here itself
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Log.i("FullscreenBehaviour", "onKeyDown");
        return screenSlidePagerActivityPresenter.dispatchKeyEvent(event);
    }

    // Simply unlock device when home button is successfully unlocked
    @Override
    public void onLockStatusChanged(boolean isLocked) {
        screenSlidePagerActivityPresenter.onLockStatusChanged(isLocked);
    }

    void unlockUI() {
        screenSlidePagerActivityPresenter.unlockUI();
    }



    protected void unlockApp() {
        screenSlidePagerActivityPresenter.unlockApp();
    }

    // open the unlock challenge page if the user is app locked
    public void unlockAppAnd(IAction doNext) {
        screenSlidePagerActivityPresenter.unlockAppAnd(doNext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(resultCode == RESULT_OK){
            if(requestCode == screenSlidePagerActivityPresenter.RC_EXT_APP_LOCK && data !=null) {
                screenSlidePagerActivityPresenter.onExternalAppResult(data);
            }
        }
    }

    /***
     * App Activity unlocking the phone - Simply unlock device by finishing the activity
     * Step 2 of application unlock flow
     */
    public void hideLockEffects() {
        Log.i(TAG, "hideLockEffects");

        screenSlidePagerActivityPresenter.hideLockEffects();
    }

    /***
     * App Activity unlocking the phone - Simply unlock device by finishing the activity
     * Step 2 of application unlock flow
     */
    public void unlockDevice() {
        Log.i(TAG, "unlockDevice");

        screenSlidePagerActivityPresenter.unlockDevice();
    }
}
