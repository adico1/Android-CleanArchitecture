package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 */

public class FacebookAction extends Action {
    public FacebookAction(Context context) {
        super(context);
    }

    @Override
    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;
        PackageManager pm = context.getPackageManager();

        String url = "";

        try
        {
            // Raise exception if whatsapp doesn't exist
            PackageInfo info = pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
            url = "fb://root";
        }
        catch (PackageManager.NameNotFoundException e)
        {
            url = "http://www.facebook.com";
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
        activity.unlockDevice();
    }
}
