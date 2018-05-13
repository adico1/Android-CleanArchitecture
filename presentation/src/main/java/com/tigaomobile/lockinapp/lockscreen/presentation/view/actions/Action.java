package com.tigaomobile.lockinapp.lockscreen.presentation.view.actions;

import android.content.Context;

/**
 * Created by adi on 08/03/2018.
 * Description:
 *
 *
 *
 */

abstract class Action implements IAction {
    final Context context;

    Action(Context context) {
        this.context = context;
    }
}
