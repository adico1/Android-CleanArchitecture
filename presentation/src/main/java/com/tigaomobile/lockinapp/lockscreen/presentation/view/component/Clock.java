package com.tigaomobile.lockinapp.lockscreen.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by adi on 12/02/2018.
 */

public class Clock extends LinearLayout {
    private final LayoutInflater mInflater;
    public Clock(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();

    }
    public Clock(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }
    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }
    private void init()
    {
        View v = mInflater.inflate(R.layout.clock, this, true);
        TextView tv = v.findViewById(R.id.textView);
        // format Monday, 9 Oct
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM", Locale.US);
        Date now = new Date();
        tv.setText(sdf.format(now));
    }
}
