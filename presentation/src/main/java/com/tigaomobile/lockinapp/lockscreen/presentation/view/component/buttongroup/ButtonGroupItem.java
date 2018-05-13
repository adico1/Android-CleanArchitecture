package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

import android.content.Context;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.ActionFactory;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.IAction;

/**
 * Created by adi on 10/04/2018.
 * Description:
 */

public class ButtonGroupItem {
    private int _x, _y;
    private String _icon;
    private String _actionName;
    private String _url;
    private IAction _action;
    private int _interfaceId;

    public ButtonGroupItem(Context context, int x, int y, String icon, String actionName, String url) {
        _x = x;
        _y = y;
        _icon = icon;
        _actionName = actionName;
        _url = url;
        _action = new ActionFactory(context).create(_actionName, _url);
    }


    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public String get_icon() {
        return _icon;
    }

    public void set_icon(String _icon) {
        this._icon = _icon;
    }

    public IAction get_action() {
        return _action;
    }

    public String get_actionName() {
        return _actionName;
    }

    public void set_actionName(String _actionName) {
        this._actionName = _actionName;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public int get_interfaceId() {
        return _interfaceId;
    }

    public void set_interfaceId(int _interfaceId) {
        this._interfaceId = _interfaceId;
    }
}
