package com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment;

import android.content.Context;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.ScreenSlidePagerActivity;
import javax.inject.Inject;

import static com.tigaomobile.lockinapp.lockscreen.presentation.view.MApplication.getConfig;

/**
 * Created by adi on 29/03/2018.
 * Description:
 */

public abstract class BaseAppFragment extends BaseFragment {
    private ScreenSlidePagerActivity baseProject;
    @Inject protected Config config;

    protected ScreenSlidePagerActivity getBaseProject() {
        if (baseProject == null) baseProject = getBaseProject();
        return baseProject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScreenSlidePagerActivity) {
            baseProject = (ScreenSlidePagerActivity) context;
        }
    }
}
