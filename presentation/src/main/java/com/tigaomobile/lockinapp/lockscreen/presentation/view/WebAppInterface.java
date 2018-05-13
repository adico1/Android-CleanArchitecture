/*
  Created by adi on 01/02/2018.
  Description:



 */
package com.tigaomobile.lockinapp.lockscreen.presentation.view;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ContentBaseActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ExternalAppActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.UnlockChallengeActivity;

public class WebAppInterface {
    private final String TAG = "WebAppInterface";
    private final Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** pass gestures from webview to android native */
    @JavascriptInterface
    public void handleGesture(String toast) {
        ((ContentBaseActivity)mContext).handleGesture(toast);
    }

    /** external app (e.g. EDu) to lockscreen communication - application unlock phone */
    @JavascriptInterface
    public void appUnlock() {
        // Log.i(TAG, "appUnlock");

        ((UnlockChallengeActivity)mContext).setAppUnlocked();
    }

    /** external app (e.g. EDU) to lockscreen communication - get user or request login */
    @JavascriptInterface
    public void getUser() {
        // Log.i(TAG, "getUser");
        ((ExternalAppActivity)mContext).requestLogin();
    }

    /** external app (e.g. EDU) to lockscreen communication - get user or request login */
    @JavascriptInterface
    public void onAppInitialized() {
        // Log.i(TAG, "onAppInitialized");

        ((ExternalAppActivity)mContext).onExternalAppInitialized();
    }

    /** external app (e.g. EDU) to lockscreen communication - get user or request login */
    @JavascriptInterface
    public void jsReady() {
        // Log.i(TAG, "jsReady");
        ((ExternalAppActivity)mContext).onJsReady();
    }

    /** external app (e.g. EDU) to lockscreen communication - get user or request login */
    @JavascriptInterface
    public void extAppErr() {
        // Log.i(TAG, "extAppErr");
        ((ExternalAppActivity)mContext).extAppErr();
    }
}
