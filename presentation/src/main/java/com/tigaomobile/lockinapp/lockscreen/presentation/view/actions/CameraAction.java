package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.Context;
import android.content.Intent;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;

/**
 * Created by adi on 08/03/2018.
 * Description:
 * Camera action - loads the camera intent
 */

public class CameraAction extends Action {
    //private static final int MY_CAMERA_REQUEST_CODE = 100;

    public CameraAction(Context context) {
        super(context);
    }

    public void run() {
        ScreenSlidePagerActivity activity = (ScreenSlidePagerActivity)context;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
//                        MY_CAMERA_REQUEST_CODE);
//            }
//        }
//
//        if ( Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA ) !=
//                        PackageManager.PERMISSION_GRANTED) {
//            return;
//        }

        //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.unlockDevice();
    }

}
