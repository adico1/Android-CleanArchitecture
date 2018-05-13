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

public class WhatsappAction extends Action {
    public WhatsappAction(Context context) {
        super(context);
    }

    @Override
    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;
        PackageManager pm = context.getPackageManager();

        try
        {
            // Raise exception if whatsapp doesn't exist
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            waIntent.setType("text/plain");
            waIntent.setPackage("com.whatsapp");
            activity.startActivity(waIntent);
            activity.unlockDevice();
        }
        catch (PackageManager.NameNotFoundException e)
        {
            String url = "http://www.whatsapp.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
            activity.unlockDevice();
        }
    }
}
