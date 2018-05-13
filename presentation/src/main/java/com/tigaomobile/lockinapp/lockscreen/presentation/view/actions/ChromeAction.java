package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 * Description:
 *
 *
 *
 */

public class ChromeAction extends Action {
    public ChromeAction(Context context) {
        super(context);
    }

    @Override
    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("about:blank"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setPackage("com.android.chrome");
        try {
            activity.startActivity(i);
        } catch(ActivityNotFoundException ex) {
            i.setPackage(null);
            activity.startActivity(i);
        }

        activity.unlockDevice();
    }
}
