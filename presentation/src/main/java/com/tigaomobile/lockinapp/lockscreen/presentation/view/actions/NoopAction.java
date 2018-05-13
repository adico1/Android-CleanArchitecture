package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.Context;
import android.content.Intent;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 */

public class NoopAction extends Action {
    public NoopAction(Context context) {
        super(context);
    }

    private void minimizeApp(Context context) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
    }

    @Override
    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;

        activity.unlockDevice();
        minimizeApp(activity);
    }
}
