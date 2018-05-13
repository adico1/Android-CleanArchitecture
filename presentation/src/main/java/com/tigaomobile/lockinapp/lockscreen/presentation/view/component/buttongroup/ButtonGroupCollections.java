package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.tigaomobile.lockinapp.lockscreen.presentation.view.MApplication.getConfig;

/**
 * Created by adi on 10/04/2018.
 * Description:
 */

public class ButtonGroupCollections {
    private final String TAG = ButtonGroupCollections.class.getSimpleName();
    private ArrayList<ButtonGroupItem> orderedListOfButtons = new ArrayList<>();
    private HashMap<String, ButtonGroupItem> buttonIndex = new HashMap<>();
    private ButtonGroupItem controllerBtn;

    public ButtonGroupCollections(Context context, View view, Config config) {
        JSONArray lockViewActions = config.getLockViewActions();
        JSONObject controllerBtnInfo = config.getLockViewCtrlBtn();

        String location = config.getMenuLocation();
        String style = config.getMenuStyle();

        IButtonCollectionLayout layout = ButtonCollectionLayoutFactory.create(location, style);
        layout.arrange(view, controllerBtnInfo, lockViewActions, location);

        controllerBtn = createButton(context, controllerBtnInfo);

        ButtonGroupItem button;

        for(int idx = 0; idx < lockViewActions.length(); idx += 1) {

            try {
                JSONObject action = lockViewActions.getJSONObject(idx);
                button = createButton(context, action);;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            if(button==null) {
                Log.e(TAG, "button not found:" + idx);
                break;
            }

            orderedListOfButtons.add(button);
            buttonIndex.put(button.get_actionName(), button);
        }
    }

    private ButtonGroupItem createButton(Context context, JSONObject buttonInfo) {
        int x, y;
        String icon, actionName = "", url = "";

        try {
            x = buttonInfo.getInt("x");
            y = buttonInfo.getInt("y");
            icon = buttonInfo.getString("icon");
            if(buttonInfo.has("action")) {
                actionName = buttonInfo.getString("action");
            }

            if(buttonInfo.has("url")) {
                url = buttonInfo.getString("url");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return new ButtonGroupItem(context, x, y, icon, actionName, url);
    }

    public ArrayList<ButtonGroupItem> getOrderedListOfButtons() {
        return orderedListOfButtons;
    }

    public HashMap<String, ButtonGroupItem> getButtonIndex() {
        return buttonIndex;
    }

    public int length() {
        return orderedListOfButtons.size();
    }

    public ButtonGroupItem getControllerBtn() {
        return controllerBtn;
    }
}
