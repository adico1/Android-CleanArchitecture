package com.tigaomobile.lockinapp.lockscreen.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;

/**
 * Created by adi on 12/02/2018.
 */

public class SwipeToUnlock extends LinearLayout {
    private final LayoutInflater mInflater;
    public SwipeToUnlock(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();

    }
    public SwipeToUnlock(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public SwipeToUnlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }
    private void init()
    {
        View v = mInflater.inflate(R.layout.swipe_to_unlock, this, true);
//        TextView tv = (TextView) v.findViewById(R.id.textView1);
//        tv.setText(" Custom RelativeLayout");
    }
}
