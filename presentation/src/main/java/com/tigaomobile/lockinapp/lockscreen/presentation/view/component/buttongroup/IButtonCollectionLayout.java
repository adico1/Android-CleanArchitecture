package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

import android.view.View;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adi on 11/04/2018.
 * Description:
 */

interface IButtonCollectionLayout {
    void arrange(View view, JSONObject controllerBtnInfo, JSONArray lockViewActions,
        String position);
}
