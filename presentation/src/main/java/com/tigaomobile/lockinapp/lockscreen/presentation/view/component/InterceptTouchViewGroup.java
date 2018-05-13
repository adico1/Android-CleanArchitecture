package com.tigaomobile.lockinapp.lockscreen.presentation.view.component;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by adi on 08/02/2018.
 * Description:
 *
 *
 *
 */

public class InterceptTouchViewGroup extends ViewGroup {

    public InterceptTouchViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Log.v("customViewGroup", "**********Intercepted");
        return true;
    }
}
