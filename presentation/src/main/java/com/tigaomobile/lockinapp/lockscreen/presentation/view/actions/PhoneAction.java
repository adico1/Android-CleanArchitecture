package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.Context;
import android.content.Intent;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 */

public class PhoneAction extends Action {
    public PhoneAction(Context context) {
        super(context);
    }

    @Override
    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.unlockDevice();
    }
}
