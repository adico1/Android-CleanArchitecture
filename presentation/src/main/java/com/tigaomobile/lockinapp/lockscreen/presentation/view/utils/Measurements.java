package com.tigaomobile.lockinapp.lockscreen.presentation.view.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by adi on 12/04/2018.
 * Description:
 */

public class Measurements {
    private Context _context;

    public Measurements(Context context) {
        _context = context;
    }

    public float calcPixels(int dpSize) {
        DisplayMetrics dm =  _context.getResources().getDisplayMetrics() ;
        return applyDimension(COMPLEX_UNIT_DIP, dpSize, dm);
    }
}
